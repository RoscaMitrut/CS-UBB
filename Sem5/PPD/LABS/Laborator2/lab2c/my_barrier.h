#include <thread>
#include <vector>
#include <mutex>
#include <queue>
#include <condition_variable>
#include <time.h>
#include <barrier>
#ifndef MY_BARRIER_H
#define MY_BARRIER_H

using namespace std;

class my_barrier {
private:
    mutex m;
    condition_variable cv;
    int counter;
    int waiting;
    int thread_count;
public:
    my_barrier(int count) : thread_count(count), counter(0), waiting(0) {}
    void wait() {
        unique_lock<mutex> lk(m);
        ++counter;
        ++waiting;
        cv.wait(lk, [&] {return counter >= thread_count;});
        cv.notify_one();
        --waiting;
        if (waiting == 0) {
            counter = 0;
        }
        lk.unlock();
    }
};

#endif //MY_BARRIER_H
