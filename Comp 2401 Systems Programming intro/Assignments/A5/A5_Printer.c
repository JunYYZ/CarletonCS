#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

// Global variables
int currentNumber = 1;
char currentLetter = 'A';

// Mutex for synchronization
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

// Condition variables
pthread_cond_t cond = PTHREAD_COND_INITIALIZER;

// Number of numbers printed in the current cycle
int numbersPrinted = 0;

// Function declarations
void* printNumbers(void* arg);
void* printLetters(void* arg);

int main() {
    pthread_t numberThread, letterThread;

    // Create threads
    pthread_create(&numberThread, NULL, &printNumbers, NULL);
    pthread_create(&letterThread, NULL, &printLetters, NULL);

    // Wait for threads to finish
    pthread_join(numberThread, NULL);
    pthread_join(letterThread, NULL);

    // Clean up
    pthread_mutex_destroy(&mutex);
    pthread_cond_destroy(&cond);

    return 0;
}

void* printNumbers(void* arg) {
    while (currentNumber <= 52) {
        pthread_mutex_lock(&mutex);
        if (currentNumber <= 9) {
            if (numbersPrinted < 2) {
                printf("%d ", currentNumber++);
                numbersPrinted++;
                if (numbersPrinted == 2) {
                    pthread_cond_signal(&cond); // Signal letter thread
                }
            } else {
                pthread_cond_wait(&cond, &mutex); // Wait for letter thread
                numbersPrinted = 0;
            }
        } else {
            printf("%d ", currentNumber++);
            if (currentNumber > 52) {
                pthread_cond_signal(&cond); // Ensure letter thread can exit if waiting
            }
        }
        pthread_mutex_unlock(&mutex);
    }
    return NULL;
}

void* printLetters(void* arg) {
    while (currentLetter <= 'Z') {
        pthread_mutex_lock(&mutex);
        if (currentNumber > 9) {
            printf("%c ", currentLetter++);
        } else {
            if (numbersPrinted == 2) {
                printf("%c ", currentLetter++);
                numbersPrinted = 0; // Reset for the next cycle
                pthread_cond_signal(&cond); // Signal number thread
            } else {
                pthread_cond_wait(&cond, &mutex); // Wait for number thread
            }
        }
        pthread_mutex_unlock(&mutex);
    }
    return NULL;
}
ULL);

    // wait threads
    pthread_join(thread_num, NULL);
    pthread_join(thread_letter, NULL);

    // clean
    pthread_mutex_destroy(&mutex_num);
    pthread_mutex_destroy(&mutex_letter);

    return 0;
}