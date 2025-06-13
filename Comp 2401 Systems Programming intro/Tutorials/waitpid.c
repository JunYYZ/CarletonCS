#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int main() {
  int status, child, parent, children[5], sleepTimes[5] = {1, 5, 8, 2, 4};

  printf("I am the parent (PID=%d)\n", parent = getpid());
  printf("I am spawning 5 children  ...\n");
  
  for (int i=0; i<5; i++) {
    if (getpid() == parent)
      children[i] = fork();
    // Note that for the parent process, children[i] is set to the
    // pid of the newly-created child process. However, for the child
    // process code, all the values of children[i] will be 0.
    if (children[i] == 0) {
    	printf("     I am a child (PID=%d) ... I will sleep for %d sec\n", getpid(), sleepTimes[i]);
    	sleep(sleepTimes[i]);
    	printf("     I am awake!  Process %d terminating.\n", getpid());
    	exit(0);
     }
  }

  printf("I am now waiting for child 3 to terminate ...\n");
  child = waitpid(children[2], &status, 0);
  printf("It looks like my child (PID=%d) has terminated.\n", child);
  printf("All children have terminated. Parent process %d terminating.\n", getpid());
}
