#include <stdbool.h> // For bool type
#include <ctype.h>   // For isdigit
#include <string.h>  // For strlen
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

// Prime number check function
bool is_prime(int num)
{
    if (num <= 1)
        return false;
    for (int i = 2; i * i <= num; i++)
    {
        if (num % i == 0)
            return false;
    }
    return true;
}

// Number check function
bool is_number(const char *str)
{
    for (int i = 0; i < strlen(str); i++)
    {
        if (!isdigit(str[i]))
            return false;
    }
    return true;
}

// Convert letters to their sum of decimal values
int convert_letters_to_sum(const char *str)
{
    int sum = 0;
    for (int i = 0; str[i]; i++)
    {
        if (isupper(str[i]) || islower(str[i]))
            sum += (int)str[i];
        else
            return -1; // Indicates error
    }
    return sum;
}

void *print_hello_world(void *threadid)
{
    long tid;
    tid = (long)threadid;
    for (int i = 0; i < 5; i++)
    {
        printf("[%ld] Hello World\n", tid);
    }
    pthread_exit(NULL);
}

pthread_mutex_t lock;

void *print_hello(void *data)
{
    pthread_mutex_lock(&lock);
    for (int i = 0; i < 5; i++)
    {
        printf("Hello ");
    }
    pthread_mutex_unlock(&lock);
    pthread_exit(NULL);
}

void *print_world(void *data)
{
    pthread_mutex_lock(&lock);
    for (int i = 0; i < 5; i++)
    {
        printf("World\n");
    }
    pthread_mutex_unlock(&lock);
    pthread_exit(NULL);
}

pthread_mutex_t lock1, lock2;

void *print_hello(void *data)
{
    for (int i = 0; i < 5; i++)
    {
        pthread_mutex_lock(&lock1);
        printf("Hello ");
        pthread_mutex_unlock(&lock2);
    }
    pthread_exit(NULL);
}

void *print_world(void *data)
{
    for (int i = 0; i < 5; i++)
    {
        pthread_mutex_lock(&lock2);
        printf("World\n");
        pthread_mutex_unlock(&lock1);
    }
    pthread_exit(NULL);
}

int main()
{
    pthread_t thread1, thread2;
    pthread_mutex_init(&lock, NULL);

    pthread_create(&thread1, NULL, print_hello, NULL);
    pthread_create(&thread2, NULL, print_world, NULL);

    pthread_join(thread1, NULL);
    pthread_join(thread2, NULL);
    pthread_mutex_destroy(&lock);

    pthread_t threads[2];
    int rc;
    long t;
    for (t = 0; t < 2; t++)
    {
        rc = pthread_create(&threads[t], NULL, print_hello_world, (void *)t);
        if (rc)
        {
            printf("ERROR; return code from pthread_create() is %d\n", rc);
            exit(-1);
        }
    }
    pthread_exit(NULL);

    pthread_t thread1, thread2;
    pthread_mutex_init(&lock1, NULL);
    pthread_mutex_init(&lock2, NULL);

    // Initially lock lock2 so that "Hello" prints first
    pthread_mutex_lock(&lock2);

    pthread_create(&thread1, NULL, print_hello, NULL);
    pthread_create(&thread2, NULL, print_world, NULL);

    return 0;
}
