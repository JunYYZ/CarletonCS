#include <iostream>
#include <random>
#include <sstream>
#include "Campground.h"
#include "Tester.h"
#include "Category.h"
#include "Camper.h"
#include "Date.h"

using namespace std;
using namespace cat;

// unit tests for Campers and Campsites
int testPrintCampers();
int testPrintCampsites();
int testCampsiteAddCampers();

// Integration tests for Campground
int cgTestPrintCampsites();
int cgTestPrintCampers();

int main()
{
    int choice = -1;
    while (choice != 0 && cin.good())
    {
        std::cout << endl;
        std::cout << " 0. Exit" << endl;
        std::cout << " Unit tests for Campers and Campsites:" << endl;

        std::cout << " 1. [4 marks] Test print Campers" << endl;

        std::cout << " 2. [3 marks] Test print Campsites" << endl;
        std::cout << " 3. [7 marks] Test Campsite add, remove, and print Campers" << endl;
        std::cout << endl
                  << " Campground tests:" << endl;
        std::cout << " 4. [8 marks] Test add, remove, and print Campsites in Campground" << endl;
        std::cout << " 5. [9 marks] Test add, remove, and print Campers in Campground" << endl;

        std::cout << " 6. [31 marks] Test all and get mark" << endl
                  << endl;
        cin >> choice;
        std::cout << endl;

        // clear the buffer since buffers are a pain in the butt
        cin.ignore(numeric_limits<streamsize>::max(), '\n');

        switch (choice)
        {
        case 1:
            testPrintCampers();
            break;
        case 2:
            testPrintCampsites();
            break;
        case 3:
            testCampsiteAddCampers();
            break;
        case 4:
            cgTestPrintCampsites();
            break;
        case 5:
            cgTestPrintCampers();
            break;
        case 6:
            int score = 0;
            score += testPrintCampers();
            score += testPrintCampsites();
            score += testCampsiteAddCampers();
            score += cgTestPrintCampsites();
            score += cgTestPrintCampers();
            std::cout << "Your score is " << score << " out of 31" << endl
                      << endl;
            break;
        }
    }
    std::cout << "Bye!" << endl;
    return 0;
}

Tester tester;

int testPrintCampers()
{
    Camper campers[5];

    Tester tester;

    string names[5] = {"Alice", "Bob", "Charlie", "Dave", "Eve"};
    string plates[5] = {"AAA", "BBB", "CCC", "DDD", "EEE"};
    int num_people[5] = {1, 2, 3, 4, 5};
    Date check_ins[5] = {Date(2024, 1, 1), Date(2024, 2, 2), Date(2024, 3, 3), Date(2024, 4, 4), Date(2024, 5, 5)};
    Date check_outs[5] = {Date(2024, 1, 2), Date(2024, 2, 3), Date(2024, 3, 4), Date(2024, 4, 5), Date(2024, 5, 6)};

    string checkins[5];
    string checkouts[5];

    // Convert the dates to strings and make campers
    cout << "Making campers" << endl;
    for (int i = 0; i < 5; i++)
    {
        tester.initCapture();
        check_ins[i].print();
        checkins[i] = tester.getOutput();
        tester.endCapture();
        tester.initCapture();
        check_outs[i].print();
        checkouts[i] = tester.getOutput();
        tester.endCapture();
        cout << "Making camper " << i << endl;
        campers[i] = Camper(names[i], plates[i], num_people[i], check_ins[i], check_outs[i]);
    }

    cout << "Testing Print Camper class" << endl
         << endl;

    tester.initCapture();
    for (int i = 0; i < 5; i++)
    {
        campers[i].print();
    }
    tester.endCapture();

    int score = 0;
    int error = 0;
    tester.find({0, 1, 2, 3, 4}, names, error);
    if (error > 0)
    {
        cout << "Error: Some names not found" << endl;
    }
    else
    {
        cout << "All names found, 1 point earned" << endl;
        ++score;
    }

    tester.find({0, 1, 2, 3, 4}, plates, error);
    if (error > 0)
    {
        cout << "Error: Some plates not found" << endl;
    }
    else
    {
        cout << "All plates found, 1 point earned" << endl;
        ++score;
    }

    tester.find({0, 1, 2, 3, 4}, checkins, error);
    if (error > 0)
    {
        cout << "Error: Some checkins not found" << endl;
    }
    else
    {
        cout << "All checkins found, 1 point earned" << endl;
        ++score;
    }

    tester.find({0, 1, 2, 3, 4}, checkouts, error);
    if (error > 0)
    {
        cout << "Error: Some checkouts not found" << endl;
    }
    else
    {
        cout << "All checkouts found, 1 point earned" << endl;
        ++score;
    }

    cout << "Points earned: " << score << "/4" << endl
         << endl;
    return score;
}

int testPrintCampsites()
{
    Campsite campsites[5];

    int site_numbers[5] = {1, 2, 3, 4, 5};
    cat::Category categories[5] = {tent, rv_site, cabin, trailer, lodge};
    string categoriesStr[5] = {"tent", "rv_site", "cabin", "trailer", "lodge"};
    string descriptions[5] = {"Lakeside", "Mountain View", "Forest", "Desert", "Beach"};
    int maxPeople[5] = {1, 2, 3, 4, 5};
    double prices[5] = {100, 200, 300, 400, 500};
    string pricesStr[5] = {"$100.00", "$200.00", "$300.00", "$400.00", "$500.00"};

    cout << "Making campsites" << endl;
    for (int i = 0; i < 5; i++)
    {
        campsites[i] = Campsite(site_numbers[i], categories[i], descriptions[i], maxPeople[i], prices[i]);
    }

    cout << "Testing Print Campsite class" << endl
         << endl;

    tester.initCapture();
    for (int i = 0; i < 5; i++)
    {
        campsites[i].print();
    }
    tester.endCapture();

    int score = 0;
    int error = 0;
    tester.find({0, 1, 2, 3, 4}, categoriesStr, error);
    if (error > 0)
    {
        cout << "Error: Some categories not found" << endl;
    }
    else
    {
        cout << "All categories found, 1 point earned" << endl;
        ++score;
    }

    tester.find({0, 1, 2, 3, 4}, descriptions, error);
    if (error > 0)
    {
        cout << "Error: Some descriptions not found" << endl;
    }
    else
    {
        cout << "All descriptions found, 1 point earned" << endl;
        ++score;
    }

    tester.find({0, 1, 2, 3, 4}, pricesStr, error);
    if (error > 0)
    {
        cout << "Error: Some prices not found" << endl;
    }
    else
    {
        cout << "All prices found, 1 point earned" << endl;
        ++score;
    }

    cout << "Points earned: " << score << "/3" << endl
         << endl;
    return score;
}

int testCampsiteAddCampers()
{
    cout << "test Campsite add, remove, and print Campers" << endl;
    Campsite campsite(1, tent, "Lakeside", 5, 100);

    string names[5] = {"Alice", "Bob", "Charlie", "Dave", "Eve"};
    string plates[5] = {"AAA", "BBB", "CCC", "DDD", "EEE"};
    int num_people[5] = {1, 2, 3, 4, 5};
    // The third camper should not be added as it overlaps with the second
    // Order should be 3,4,1,0
    Date check_ins[5] = {Date(2024, 4, 2), Date(2024, 4, 1), Date(2024, 4, 2), Date(2024, 2, 4), Date(2024, 3, 5)};
    Date check_outs[5] = {Date(2024, 4, 3), Date(2024, 4, 2), Date(2024, 4, 4), Date(2024, 2, 5), Date(2024, 3, 6)};

    cout << "Adding campers" << endl;
    for (int i = 0; i < 5; i++)
    {
        cout << "Adding camper " << i << endl;
        campsite.addCamper(names[i], plates[i], num_people[i], check_ins[i], check_outs[i]);
    }

    cout << "Testing Print Campsite class" << endl
         << endl;

    tester.initCapture();
    campsite.printCampers();
    tester.endCapture();

    int score = 0;
    int error = 0;

    tester.find({3, 4, 1, 0}, names, error);
    if (error > 0)
    {
        cout << "Error: Some names not found" << endl;
        // return score;
    }
    else
    {
        cout << "All names found, 1 point earned" << endl;
        ++score;
    }

    tester.findInOrder({3, 4, 1, 0}, names, error);
    if (error > 0)
    {
        cout << "Error: Some names not found or not in order" << endl;
        // return score;
    }
    else
    {
        cout << "All names found and in order, 1 point earned" << endl;
        ++score;
    }

    tester.confirmAbsent({2}, names, error);
    if (error > 0)
    {
        cout << "Error: Camper 2 found but should not be" << endl;
        // return score;
    }
    else
    {
        cout << "Camper 2 not found, 1 point earned" << endl;
        ++score;
    }

    cout << "Printing camper on date: ";
    check_ins[4].print();
    tester.initCapture();
    campsite.printCamper(check_ins[4]);
    tester.endCapture();

    tester.find({4}, names, error);
    if (error > 0)
    {
        cout << "Error: " << names[4] << " not found" << endl;
    }
    else
    {
        cout << names[4] << " found, 1 point earned" << endl;
        ++score;
    }

    tester.confirmAbsent({0, 1, 3}, names, error);
    if (error > 0)
    {
        cout << "Error: Wrong campers printed" << endl;
    }
    else
    {
        cout << "No wrong campers printed, 1 point earned" << endl;
        ++score;
    }

    campsite.removeCamper(names[4]);

    cout << "Printing campers after removing " << names[4] << endl;
    tester.initCapture();
    campsite.printCampers();
    tester.endCapture();

    tester.find({3, 1, 0}, names, error);
    if (error > 0)
    {
        cout << "Error: Some names not found" << endl;
    }
    else
    {
        cout << "All names found, 1 point earned" << endl;
        ++score;
    }

    tester.confirmAbsent({4}, names, error);
    if (error > 0)
    {
        cout << "Error: Camper " << names[4] << " found but was removed" << endl;
    }
    else
    {
        cout << "Camper " << names[4] << " not found, 1 point earned" << endl;
        ++score;
    }

    cout << "Points earned: " << score << "/7" << endl
         << endl;
    return score;
}

int cgTestPrintCampsites()
{
    Campground campground;
    Campsite campsites[5];

    int site_numbers[5] = {1, 2, 3, 4, 1};
    cat::Category categories[5] = {tent, rv_site, cabin, trailer, lodge};
    string categoriesStr[5] = {"tent", "rv_site", "cabin", "trailer", "lodge"};
    string descriptions[5] = {"Lakeside", "Mountain View", "Forest", "Desert", "Beach"};
    int maxPeople[5] = {1, 2, 3, 4, 5};
    double prices[5] = {100, 200, 300, 400, 500};
    string pricesStr[5] = {"$100.00", "$200.00", "$300.00", "$400.00", "$500.00"};

    cout << "Making campsites" << endl;
    for (int i = 0; i < 5; i++)
    {
        cout << "Adding campsite " << i << endl;
        campground.addCampsite(site_numbers[i], categories[i], descriptions[i], maxPeople[i], prices[i]);
    }

    cout << "Testing Print Campsites from Campground" << endl
         << endl;

    tester.initCapture();
    campground.printCampsites();
    tester.endCapture();

    int score = 0;
    int error = 0;
    tester.find({0, 1, 2, 3}, categoriesStr, error);
    if (error > 0)
    {
        cout << "Error: Some categories not found" << endl;
    }
    else
    {
        cout << "All categories found, 1 point earned" << endl;
        ++score;
    }

    tester.find({0, 1, 2, 3}, descriptions, error);
    if (error > 0)
    {
        cout << "Error: Some descriptions not found" << endl;
    }
    else
    {
        cout << "All descriptions found, 1 point earned" << endl;
        ++score;
    }

    tester.find({0, 1, 2, 3}, pricesStr, error);
    if (error > 0)
    {
        cout << "Error: Some prices not found" << endl;
    }
    else
    {
        cout << "All prices found, 1 point earned" << endl;
        ++score;
    }

    tester.confirmAbsent({4}, categoriesStr, error);

    if (error > 0)
    {
        cout << "Error: Duplicate campsite number 1 found" << endl;
    }
    else
    {
        cout << "No duplicate campsite found, 1 point earned" << endl;
        ++score;
    }

    cout << endl
         << "Testing print campsites by category" << endl;
    tester.initCapture();
    campground.printCampsitesByCategory(tent);
    tester.endCapture();

    tester.find({0}, categoriesStr, error);
    if (error > 0)
    {
        cout << "Error: Category tent not found" << endl;
    }
    else
    {
        cout << "Category tent found, 1 point earned" << endl;
        ++score;
    }

    tester.confirmAbsent({1, 2, 3, 4}, categoriesStr, error);
    if (error > 0)
    {
        cout << "Error: incorrect Campsite categories found" << endl;
    }
    else
    {
        cout << "No incorrect categories found, 1 point earned" << endl;
        ++score;
    }

    cout << "Removing campsite 1" << endl;
    campground.removeCampsite(1);

    cout << "Printing campsites after removing campsite 1" << endl;
    tester.initCapture();
    campground.printCampsites();
    tester.endCapture();

    tester.find({1, 2, 3}, categoriesStr, error);
    if (error > 0)
    {
        cout << "Error: Some categories not found" << endl;
    }
    else
    {
        cout << "All categories found, 1 point earned" << endl;
        ++score;
    }

    tester.confirmAbsent({0, 4}, categoriesStr, error);
    if (error > 0)
    {
        cout << "Error: Campsite 0 or 4 found but should not be" << endl;
    }
    else
    {
        cout << "No extra campsite found, 1 point earned" << endl;
        ++score;
    }

    cout << "Points earned: " << score << "/8" << endl
         << endl;
    return score;
}

int cgTestPrintCampers()
{
    Campground campground;
    cout << endl
         << "campground testPrintCampers" << endl;

    campground.addCampsite(1, tent, "Lakeside", 5, 100);
    campground.addCampsite(2, rv_site, "Mountain View", 5, 200);
    campground.addCampsite(3, cabin, "Forest", 5, 300);

    string names[5] = {"Alice", "Bob", "Charlie", "Dave", "Eve"};
    string plates[5] = {"AAA", "BBB", "CCC", "DDD", "EEE"};
    int num_people[5] = {1, 2, 3, 4, 5};
    // The third camper should not be added as it overlaps with the first
    // Order should be 3,4,1,0
    Date check_ins[5] = {Date(2024, 4, 2), Date(2024, 4, 1), Date(2024, 4, 2), Date(2024, 2, 4), Date(2024, 3, 5)};
    Date check_outs[5] = {Date(2024, 4, 3), Date(2024, 4, 2), Date(2024, 4, 4), Date(2024, 2, 5), Date(2024, 3, 6)};

    cout << "Adding campers" << endl;
    for (int i = 0; i < 3; i++)
    {
        cout << "Adding camper " << i << endl;
        campground.addCamper(1, names[i], plates[i], num_people[i], check_ins[i], check_outs[i]);
    }

    campground.addCamper(2, names[2], plates[2], num_people[2], check_ins[2], check_outs[2]);
    campground.addCamper(2, names[3], plates[3], num_people[3], check_ins[3], check_outs[3]);

    // campsite does not exist
    campground.addCamper(5, names[4], plates[4], num_people[4], check_ins[4], check_outs[4]);

    cout << "Testing Print Campsite class" << endl
         << endl;

    cout << "Printing campsite 1" << endl;
    tester.initCapture();
    campground.printCampers(1);
    tester.endCapture();

    int score = 0;
    int error = 0;
    tester.findInOrder({1, 0}, names, error);
    if (error > 0)
    {
        cout << "Error: Some names not found or not in order" << endl;
    }
    else
    {
        cout << "All names found and in order, 1 point earned" << endl;
        ++score;
    }

    tester.confirmAbsent({2, 3, 4}, names, error);
    if (error > 0)
    {
        cout << "Error: Camper found but should not be" << endl;
    }
    else
    {
        cout << "No extra campers found, 1 point earned" << endl;
        ++score;
    }

    cout << endl
         << "*Printing campsite 2" << endl;
    tester.initCapture();
    campground.printCampers(2);
    tester.endCapture();

    tester.findInOrder({3, 2}, names, error);
    if (error > 0)
    {
        cout << "Error: Some names not found or not in order" << endl;
    }
    else
    {
        cout << "All names found and in order, 1 point earned" << endl;
        ++score;
    }

    tester.confirmAbsent({0, 1, 4}, names, error);
    if (error > 0)
    {
        cout << "Error: Camper found but should not be" << endl;
    }
    else
    {
        cout << "No extra campers found, 1 point earned" << endl;
        ++score;
    }

    cout << "Removing camper " << names[3] << " from campsite 2" << endl;
    campground.removeCamper(2, names[3]);

    cout << endl
         << "*Printing campsite 2 after removing " << names[3] << endl;
    tester.initCapture();
    campground.printCampers(2);
    tester.endCapture();

    tester.find({2}, names, error);
    if (error > 0)
    {
        cout << "Error: Camper " << names[2] << " not found" << endl;
    }
    else
    {
        cout << names[2] << " found, 1 point earned" << endl;
        ++score;
    }

    tester.confirmAbsent({3}, names, error);
    if (error > 0)
    {
        cout << "Error: Camper " << names[3] << " found but was removed" << endl;
    }
    else
    {
        cout << "Camper " << names[3] << " not found, 1 point earned" << endl;
        ++score;
    }

    cout << "Printing from campsite that does not exist" << endl;
    tester.initCapture();
    campground.printCampers(5);
    tester.endCapture();

    tester.confirmAbsent({0, 1, 2, 3, 4}, names, error);
    if (error > 0)
    {
        cout << "Error: Camper found but should not be" << endl;
    }
    else
    {
        cout << "No extra campers found, 1 point earned" << endl;
        ++score;
    }

    cout << "Printing camper on date: ";
    check_ins[2].print();
    tester.initCapture();
    campground.printCampers(check_ins[2]);
    tester.endCapture();

    tester.find({0, 2}, names, error);
    if (error > 0)
    {
        cout << "Error: some names not found" << endl;
    }
    else
    {
        cout << "All names found, 1 point earned" << endl;
        ++score;
    }

    tester.confirmAbsent({1, 3, 4}, names, error);
    if (error > 0)
    {
        cout << "Error: Wrong campers printed" << endl;
    }
    else
    {
        cout << "No wrong campers printed, 1 point earned" << endl;
        ++score;
    }

    cout << "Points earned: " << score << "/9" << endl
         << endl;
    return score;
}
