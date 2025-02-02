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
#include "Queue.h"
using namespace std;

struct Order {
    int id_operator;
    int id_order;
    int priority;
};

Queue<Order> Qp1;
Queue<Order> Qp2;
mutex dict_mutex;
map<int, vector<int>> finished_orders;
atomic<int> remaining_orders{ 0 };

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

void operatorFunction(int id_operator, int No, int To) {
    ifstream file("input.txt");
    string line;
    int orders_processed = 0;

    while (orders_processed < No && getline(file, line)) {
        int id_order, priority;
        istringstream iss(line);
        iss >> id_order >> priority;

        Order order{ id_operator, id_order, priority };

        if (priority == 1) {
            Qp1.push(order);
        }
        else {
            Qp2.push(order);
        }

        orders_processed++;
        remaining_orders++;
        this_thread::sleep_for(chrono::milliseconds(To));
    }

    file.close();
}


void packingFunction(int Tl) {
    while (true) {
        Order order;
        bool gotOrder = false;

        gotOrder = Qp2.pop(order);

        if (!gotOrder) {
            gotOrder = Qp1.pop(order);
        }

        if (!gotOrder) {
            break;
        }

        this_thread::sleep_for(chrono::milliseconds(Tl));

        lock_guard<mutex> lock(dict_mutex);
        finished_orders[order.id_operator].push_back(order.id_order);
        remaining_orders--;
    }
}

void managerFunction(int Tm) {
    this_thread::sleep_for(chrono::milliseconds(Tm));
    while (remaining_orders > 0) {
        auto now = chrono::system_clock::now();
        time_t timestamp = chrono::system_clock::to_time_t(now);

        cout << timestamp <<" \\\ " << now << "    Remaining orders: " << remaining_orders << endl;

        this_thread::sleep_for(chrono::milliseconds(Tm));
    }

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
    const int No = 10;
    const int To = 1;
    const int Tl = 100;
    const int Tm = 100;
    const int TOTAL_ORDERS = 90;

    generate_input_file(TOTAL_ORDERS);

    vector<thread> operator_threads;
    for (int i = 1; i <= NUM_OPERATORS; ++i) {
        operator_threads.emplace_back(operatorFunction, i, No, To);
    }

    vector<thread> packaging_threads;
    for (int i = 0; i < NUM_PACKAGING_TEAMS; ++i) {
        packaging_threads.emplace_back(packingFunction, Tl);
    }

    thread manager_thread(managerFunction, Tm);

    for (auto& t : operator_threads) {
        t.join();
    }

    Qp1.stop();
    Qp2.stop();

    for (auto& t : packaging_threads) {
        t.join();
    }

    manager_thread.join();

    cout << "All operations completed." << endl;
    return 0;
}