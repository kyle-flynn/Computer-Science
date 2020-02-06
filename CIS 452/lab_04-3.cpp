#include <iostream>
#include <unistd.h>
#include <thread>

using namespace std;

void do_greeting3(char arg);
//arguments:arg is an untyped pointer pointing to a character
// returns:a pointer to NULL
// side effects:prints a greeting

// global (shared and specific) data
int sharedData = 5;

int main()
{

    //create and start two threads executing the "do_greeting3" function
	// pass each thread a pointer to its respective argument
    thread thr1(do_greeting3, 'a');
    thread thr2(do_greeting3, 'b');
    cout << "Parent sees " << sharedData << endl;
    sharedData++;

    thr1.join();
    thr2.join();
    cout << "Parent sees " << sharedData << endl;
    return 0;
}

void do_greeting3(char arg)
{
    //print out a message
    cout << "Child receiving " << arg << " initially sees " 
        << sharedData << endl;
    sleep(1);
    sharedData++;
    cout << "Child receiving " << arg << " now sees " 
        << sharedData << endl;
}
