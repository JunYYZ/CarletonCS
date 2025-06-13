#include <stdio.h>
#include <stdlib.h>

// definte doubly linked list
typedef struct Node
{
    int value;
    struct Node *next;
    struct Node *prev;
} Node;

// newnodee function
Node *newNode(int value)
{
    Node *node = (Node *)malloc(sizeof(Node));
    node->value = value;
    node->next = NULL;
    node->prev = NULL;
    return node;
}

// add node to end of list
void append(Node **head, int value)
{
    Node *node = newNode(value);
    if (*head == NULL)
    {
        *head = node;
        return;
    }
    Node *temp = *head;
    while (temp->next != NULL)
    {
        temp = temp->next;
    }
    temp->next = node;
    node->prev = temp;
}

// main find triplets function
void findTriplets(Node *head)
{
    if (!head || head->value > 0)
    {
        printf("No triplets found.\n");
        return;
    }
    for (Node *current = head; current != NULL; current = current->next)
    {
        Node *peek = current->next;
        Node *tail = head;
        while (tail->next != NULL)
        {
            tail = tail->next; // move tail to  end
        }
        while (peek != NULL && tail != NULL && peek != tail && peek->prev != tail)
        {
            int sum = current->value + peek->value + tail->value;
            if (sum == 0)
            {
                printf("[%d, %d, %d]\n", current->value, peek->value, tail->value);
                peek = peek->next; // move to the next potential peek
                tail = tail->prev; // move tail to find another triplet
            }
            else if (sum < 0)
            {
                peek = peek->next;
            }
            else
            {
                tail = tail->prev;
            }
        }
    }
}

// free
void freeList(Node *head)
{
    Node *temp;
    while (head != NULL)
    {
        temp = head;
        head = head->next;
        free(temp);
    }
}

int main(int argc, char *argv[])
{
    Node *head = NULL;

    if (argc < 4)
    {
        printf("Usage: ./programName <value1> <value2> <value3> ...\nPlease provide at least 3 values for the program to execute.\n");

        int n = 0;
        while (n < 3)
        {
            printf("\nSwitching to user input \n\nEnter the number of elements (greater than 3): ");
            scanf("%d", &n);
            if (n < 3)
            {
                printf("Invalid input. Please enter a number greater than 3.\n");
            }
        }

        printf("Enter %d values in order, use space as sepeator or click space after each number:", n);
        for (int i = 0; i < n; i++)
        {
            int value;
            scanf("%d", &value);
            append(&head, value);
        }
    }
    else
    {
        for (int i = 1; i < argc; i++)
        {
            append(&head, atoi(argv[i]));
        }
    }

    findTriplets(head);
    freeList(head);
    return 0;
}
