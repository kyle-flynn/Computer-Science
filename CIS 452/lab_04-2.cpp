// Compile with -lpthread flag
#include <thread>
#include <iostream>
#include <sys/types.h> 
#include <unistd.h> 

using namespace std;

/* Thread functions in C++ can take any number of args of any type */
void do_greeting2 (int, string); 

int main() 
{ 
    srand(getpid());

    thread t1(do_greeting2, 30, "First task");
    thread t2(do_greeting2, 50, "Second task");

    t1.join();
    t2.join();

    return 0; 
}

void do_greeting2 (int num_iter, string text) 
{ 
    int val = rand() % 2;

    cout << "From " << this_thread::get_id() << " " << text << endl;
    for (int loop = 0;  loop < num_iter;  loop++) { 
        sleep (1);
        if (!val) 
            cout << loop << " Hello ";  // Intentionally no newline
        else 
            cout << loop << " World\n"; 
    } 
}
