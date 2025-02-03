#include <iostream>
#include <fstream>
#include <thread>
#include <vector>
#include <mutex>
#include <condition_variable>
#include <chrono>
#include <map>
#include <random>
#include <atomic>
#include <sstream>

using namespace std;

// Queue header
#ifndef QUEUE_H
#define QUEUE_H
template <typename T>
class Queue {
private:
    static const int MAX = 50;
    queue<T> results;
    mutex mtx;
    condition_variable empty;
    condition_variable full;
    atomic<int> counter = 0;
    bool is_on = true;
public:
    void push(T item) {
        unique_lock<std::mutex> lock(mtx);
        while (results.size() == MAX) {
            full.wait(lock);
        }
        results.push(item);
        empty.notify_one();
    }
    bool pop(T& item) {
        unique_lock<std::mutex> lock(mtx);
        counter++;
        while (is_on && results.size() == 0) {
            empty.wait(lock);
        }
        counter--;
        if (!is_on && results.size() == 0)
            return false;
        item = results.front();
        results.pop();
        full.notify_one();
        return true;
    }
    void stop() {
        unique_lock<mutex> lock(mtx);
        is_on = false;
        lock.unlock();
        while (counter.load() != 0) {
            lock.lock();
            empty.notify_all();
            lock.unlock();
        }
    }
};
#endif //QUEUE_H

// Structure for orders
struct Order {
    int id_operator;
    int id_order;
    int priority;
};

// Global variables
Queue<Order> qp1; // Queue for priority 1 (low)
Queue<Order> qp2; // Queue for priority 2 (high)
mutex dict_mutex;
map<int, vector<int>> finished_orders;
atomic<int> remaining_orders{ 0 };

// Function to generate random input file
void generate_input_file(int num_orders) {
    ofstream file("input.txt");
    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<> priority_dist(1, 2);

    for (int i = 1; i <= num_orders; ++i) {
        file << i << " " << priority_dist(gen) << endl;
    }
    file.close();
}

// Operator (Producer) function
void operator_function(int id_operator, int No, int To) {
    ifstream file("input.txt");
    string line;
    int orders_processed = 0;

    while (orders_processed < No && getline(file, line)) {
        int id_order, priority;
        istringstream iss(line);
        iss >> id_order >> priority;

        Order order{ id_operator, id_order, priority };

        if (priority == 1) {
            qp1.push(order);
        }
        else {
            qp2.push(order);
        }

        orders_processed++;
        remaining_orders++;
        this_thread::sleep_for(chrono::milliseconds(To));
    }

    file.close();
}

// Packaging team (Consumer) function
void packaging_team_function(int Tl) {
    while (true) {
        Order order;
        bool got_order = false;

        // Try high priority queue first
        got_order = qp2.pop(order);

        // If no high priority order, try low priority queue
        if (!got_order) {
            got_order = qp1.pop(order);
        }

        if (!got_order) {
            // If both queues are stopped and empty, exit
            break;
        }

        // Simulate packaging time
        this_thread::sleep_for(chrono::milliseconds(Tl));

        // Mark order as finished
        lock_guard<mutex> lock(dict_mutex);
        finished_orders[order.id_operator].push_back(order.id_order);
        remaining_orders--;
    }
}

// Manager function to monitor queues
void manager_function(int Tm) {
    while (remaining_orders > 0) {
        auto now = chrono::system_clock::now();
        time_t timestamp = chrono::system_clock::to_time_t(now);

        cout << "Timestamp: " << ctime(&timestamp);
        cout << "Remaining orders: " << remaining_orders << endl;
        cout << "------------------------" << endl;

        this_thread::sleep_for(chrono::milliseconds(Tm));
    }

    // Write final results to output file
    ofstream outfile("output.txt");
    for (const auto& pair : finished_orders) {
        outfile << "Operator " << pair.first << " finished orders: ";
        for (int order_id : pair.second) {
            outfile << order_id << " ";
        }
        outfile << endl;
    }
    outfile.close();
}

int main() {
    const int NUM_OPERATORS = 3;
    const int NUM_PACKAGING_TEAMS = 4;
    const int No = 10;  // Orders per operator
    const int To = 15;  // Operator delay (ms)
    const int Tl = 10;  // Packaging delay (ms)
    const int Tm = 40;  // Manager monitoring interval (ms)
    const int TOTAL_ORDERS = 90;

    // Generate input file
    generate_input_file(TOTAL_ORDERS);

    // Create threads for operators
    vector<thread> operator_threads;
    for (int i = 1; i <= NUM_OPERATORS; ++i) {
        operator_threads.emplace_back(operator_function, i, No, To);
    }

    // Create threads for packaging teams
    vector<thread> packaging_threads;
    for (int i = 0; i < NUM_PACKAGING_TEAMS; ++i) {
        packaging_threads.emplace_back(packaging_team_function, Tl);
    }

    // Start manager thread
    thread manager_thread(manager_function, Tm);

    // Wait for operators to finish
    for (auto& t : operator_threads) {
        t.join();
    }

    // Stop the queues after all operators finish
    qp1.stop();
    qp2.stop();

    // Wait for packaging teams to finish
    for (auto& t : packaging_threads) {
        t.join();
    }

    // Wait for manager to finish
    manager_thread.join();

    cout << "All operations completed." << endl;
    return 0;
}