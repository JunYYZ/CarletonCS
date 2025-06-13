#include <stdio.h>
#include <cstdio>

int main()
{
    FILE *fd;
    int data;

    // Read in the file with the 12 integers
    fd = fopen("sampleFile.dat", "r+");
    printf("File values:\n");
    while ((fread(&data, sizeof(int), 1, fd)) > 0)
    {
        printf("%d, ", data);
    }
    printf("\n");

    rewind(fd);

    // Seek to the 8th integer (index 7, since we start counting from 0)
    fseek(fd, sizeof(int) * 7, SEEK_SET);
    printf("Current position: %ld\n", ftell(fd));
    fread(&data, sizeof(int), 1, fd);
    printf("Value read: %d\n", data);
    printf("New position: %ld\n", ftell(fd));

    // Move back 2 integers from the current position
    fseek(fd, (-2) * sizeof(int), SEEK_CUR);
    printf("Current position: %ld\n", ftell(fd));
    fread(&data, sizeof(int), 1, fd);
    printf("Value read: %d\n", data);
    printf("New position: %ld\n", ftell(fd));

    // Move forward 1 integer from the current position
    fseek(fd, sizeof(int), SEEK_CUR);
    printf("Current position: %ld\n", ftell(fd));
    fread(&data, sizeof(int), 1, fd);
    printf("Value read: %d\n", data);
    printf("New position: %ld\n", ftell(fd));

    // Move to the 4th integer from the beginning
    fseek(fd, sizeof(int) * 3, SEEK_SET);
    printf("Current position: %ld\n", ftell(fd));
    fread(&data, sizeof(int), 1, fd);
    printf("Value read: %d\n", data);
    printf("New position: %ld\n", ftell(fd));

    // Move forward 5 integers from the current position
    fseek(fd, sizeof(int) * 5, SEEK_CUR);
    printf("Current position: %ld\n", ftell(fd));
    fread(&data, sizeof(int), 1, fd);
    printf("Value read: %d\n", data);
    printf("New position: %ld\n", ftell(fd));

    fclose(fd);
    return 0;
}