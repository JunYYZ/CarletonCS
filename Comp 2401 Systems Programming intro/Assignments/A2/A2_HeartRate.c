#include <stdio.h>

// bit helpers from tut3
void setBit(unsigned short *number, unsigned int bitNum)
{
    *number |= 1 << bitNum;
}

void clearBit(unsigned short *number, unsigned int bitNum)
{
    *number &= ~(1 << bitNum);
}

int isBitSet(unsigned short number, unsigned int bitNum)
{
    return number & (1 << bitNum);
}

// helpers with printing binary
short getBit(unsigned short num, int bitNum)
{
    return (num >> bitNum) & 1;
}

void printBinary(unsigned short num)
{
    printf("%d ", (num >> 15) & 1); // bit 15
    for (int bit = 14; bit >= 0; --bit)
    {
        printf("%d", (num >> bit) & 1);
        if (bit == 13 || bit == 11 || bit == 3)
        {
            printf(" ");
        }
    }
    printf("\n");
}

// functions
int getHeartRate(int heartBPM, unsigned short *heartRate)
{
    if (heartBPM < 0 || heartBPM > 255)
    {
        return 1; // out of range
    }
    // set bits  to 0 since they're unused
    for (int bit = 14; bit >= 11; --bit)
    {
        clearBit(heartRate, bit);
    }

    // set bits 3-10, could have been done with for loop and be more readable
    // but I knew how to do this and wanted to show off
    *heartRate = (unsigned short)heartBPM << 3;

    return 0;
}

void prepareHeartRate(int sensorFunction, unsigned short *heartRate)
{
    if (sensorFunction == 1)
    {
        setBit(heartRate, 15); // set parity bit for illegal input
        return;
    }

    // set bits for abnormal heart rate,reverse of what we did in getHeartRate
    int bpm = (*heartRate >> 3); // no need for loop to check for bits 15-11 since they should be 0 (if they're not thats bad)
    if (bpm < 50)
    {
        setBit(heartRate, 2); // set abnormal bit
        // indicate low heart rate
        setBit(heartRate, 11);
        setBit(heartRate, 12);
    }
    else if (bpm > 165)
    {
        setBit(heartRate, 2); // set abnormal bit
        // indicate high heart rate
        clearBit(heartRate, 11);
        setBit(heartRate, 12);
    }
    else
    { // normal heart rate
        clearBit(heartRate, 11);
        setBit(heartRate, 12);
    }
}

void presentHeartRate(unsigned short heartRate)
{
    printf("\nHeart Rate Sensor Information:\n\n");

    printf("The output of the heart rate sensor is 0x%X Which is: \n\t", heartRate);
    printBinary(heartRate);

    // i feel like it makes more sense to first see if the input is valid or not
    printf("\n%s bit 15 is %i\n\n", isBitSet(heartRate, 15) ? "ILLEGAL INPUT: " : "Normal Input: ", getBit(heartRate, 15));

    // i also felt like it makes more sense to see if the person has abnormal heartrate or not
    printf("The value of bits 11 to 12 is %i, wwhich indicates %s heart rate\n", (heartRate >> 11), isBitSet(heartRate, 12) ? (isBitSet(heartRate, 11) ? "LOW" : "HIGH") : "Normal");

    printf("The value of bit 0 is %i, which indicates the device is correct reading.\n", getBit(heartRate, 0));

    printf("The value of bit 1 is %i, which indicates the sensor is not required to reset.\n", getBit(heartRate, 1));

    printf("The value of bit 2 is %i, which indicates %s.\n", getBit(heartRate, 2), isBitSet(heartRate, 2) ? "abnormal heart rate detected" : "normal heart rate");

    // &255 is used because there might be stuff before and after the bits 3 - 10
    printf("The value of bit 3 to 10 is 0x%x, which indicates the heart rate is %i bpm.\n", (heartRate >> 3) & 255, (heartRate >> 3) & 255);
}

int main()
{
    int heartBPM = 0;
    unsigned short heartRate = 0;

    printf("Enter heart rate in BPM: ");
    scanf("%d", &heartBPM);

    int sensorFunction = getHeartRate(heartBPM, &heartRate);
    prepareHeartRate(sensorFunction, &heartRate);
    presentHeartRate(heartRate);
    return 0;
}
