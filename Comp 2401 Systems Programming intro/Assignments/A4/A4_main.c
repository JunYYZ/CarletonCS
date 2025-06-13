#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

int main()
{
    printf("Parent process started...\n");
    system("clear");
    system("ls");

    pid_t pid1, pid2;

    // spawn Kid1
    pid1 = fork();
    if (pid1 == 0)
    {
        // kid1:  TripletSum
        execlp("./TripletSum", "./TripletSum", "-3", "0", "1", "2", (char *)NULL);
        exit(EXIT_SUCCESS);
    }

    // wait kid1
    waitpid(pid1, NULL, 0);

    // spawn Kid2
    pid2 = fork();
    if (pid2 == 0)
    {
        // kid2: Airplane
        execlp("./Airplane", "./Airplane", (char *)NULL);
        exit(EXIT_SUCCESS);
    }

    // wait kid2
    waitpid(pid2, NULL, 0);

    printf("All children processes have completed. Ending program\n");
    return 0;
}