#include <stdio.h>
#include <stdlib.h>

typedef struct Node
{
    int data;
    struct Node *next;
} Node;

void insertNode(Node **head, int data)
{
    Node *newNode = (Node *)malloc(sizeof(Node));
    newNode->data = data;
    newNode->next = NULL;
    if (*head == NULL)
    {
        *head = newNode;
    }
    else
    {
        Node *last = *head;
        while (last->next != NULL)
        {
            last = last->next;
        }
        last->next = newNode;
    }
}

void printList(Node *node)
{
    while (node != NULL)
    {
        printf("%d ", node->data);
        node = node->next;
    }
    printf("\n");
}

void sortList(Node **head)
{
    int swapped;
    Node *ptr1;
    Node *lptr = NULL;
    if (*head == NULL)
        return;
    do
    {
        swapped = 0;
        ptr1 = *head;
        while (ptr1->next != lptr)
        {
            if (ptr1->data > ptr1->next->data)
            {
                int temp = ptr1->data;
                ptr1->data = ptr1->next->data;
                ptr1->next->data = temp;
                swapped = 1;
            }
            ptr1 = ptr1->next;
        }
        lptr = ptr1;
    } while (swapped);
}

void freeList(Node **head)
{
    Node *current = *head;
    Node *next;
    while (current != NULL)
    {
        next = current->next;
        free(current);
        current = next;
    }
    *head = NULL;
}

void insertInOrder(Node **head, int value)
{
    Node *newNode = (Node *)malloc(sizeof(Node));
    newNode->data = value;
    newNode->next = NULL;
    if (*head == NULL || (*head)->data >= value)
    {
        newNode->next = *head;
        *head = newNode;
    }
    else
    {
        Node *current = *head;
        while (current->next != NULL && current->next->data < value)
        {
            current = current->next;
        }
        newNode->next = current->next;
        current->next = newNode;
    }
}

void reversePrint(Node *current)
{
    if (current == NULL)
        return;
    reverseAndPrint(current->next);
    printf("%d ", current->data);
}

int deleteDup(Node **head)
{
    Node *current = *head, *prev = NULL, *temp;
    int isRepeated = 0;
    while (current != NULL && current->next != NULL)
    {
        if (current->data == current->next->data)
        {
            isRepeated = 1;
            temp = current->next;
            current->next = temp->next;
            free(temp);
        }
        else
        {
            current = current->next;
        }
    }
    return isRepeated ? 1 : 0;
}

Node *merge(Node *a, Node *b)
{
    Node *result = NULL;
    if (a == NULL)
        return b;
    else if (b == NULL)
        return a;

    if (a->data <= b->data)
    {
        result = a;
        result->next = merge(a->next, b);
    }
    else
    {
        result = b;
        result->next = merge(a, b->next);
    }
    return result;
}

Node *mergedList(Node *list1, Node *list2, Node *list3)
{
    Node *temp = merge(list1, list2);
    return merge(temp, list3);
}

int main()
{
    int a[] = {12, 4, 5, 7, 22, 39, 79, 32, 4};
    int n = sizeof(a) / sizeof(a[0]);
    Node *head = NULL;

    for (int i = 0; i < n; i++)
    {
        insertNode(&head, a[i]);
    }

    sortList(&head);
    printf("Sorted List: ");
    printList(head);

    freeList(&head);
    return 0;
}