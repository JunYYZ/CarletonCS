
#include "TestControl.h"

vector<string> TestControl::menu = {
    "RepoList test",
    "GetHub repo test",
    "GetHub file test\n",
    "Test all and get mark\n"};

void TestControl::launch()
{
    // get input, launch the appropriate test
    int choice = -1;

    while (choice != 0)
    {
        view.menu(menu, choice);
        switch (choice)
        {
        case 1:
            repoListTest();
            break;
        case 2:
            getHubRepoTest();
            break;
        case 3:
            getHubFileTest();
            break;
        case 4:
            int mark = 0;
            mark += repoListTest();
            mark += getHubRepoTest();
            mark += getHubFileTest();
            cout << endl
                 << "Your mark is " << mark << "/30" << endl;
            break;
        }
    }
}

void TestControl::initGetHub(GetHub &getHub)
{
    cout << "Initializing GetHub..." << endl;
    for (int j = 0; j < 5; ++j)
    {
        getHub.addRepo(repoTitles[j], owners[j]);
    }
    // we add the repos first, then the files for test purposes
    for (int j = 0; j < 5; ++j)
    {
        for (int i = 0; i < 5; ++i)
        {
            getHub.addFile(repoTitles[j], fileTitles[j][i], content[j][i], dates[j][i]);
        }
    }
}

int TestControl::repoListTest()
{
    // Add Repos to the list.
    // Print the list.
    // Check the size
    // Remove two repos.
    // Print the list. Check the size
    // Add a repo
    // Print the list. Check the size
    // Remove all repos
    // Print the list. Check the size

    cout << "RepoList test" << endl;
    Repo *repos[5];
    RepoList repoList;
    cout << "Adding repos" << endl;
    for (int i = 0; i < 5; ++i)
    {
        repos[i] = new Repo(repoTitles[i], owners[i]);
        repoList.add(repos[i]);
    }
    cout << "Printing repos" << endl;
    tester.initCapture();
    for (int i = 0; i < repoList.size(); ++i)
    {
        repoList.get(i)->print();
    }
    tester.endCapture();

    int marks = 0;

    cout << "Checking size" << endl;
    if (repoList.size() != 5)
    {
        cout << "Error: size should be 5, but is " << repoList.size() << endl;
    }
    else
    {
        marks += 1;
    }

    cout << "Checking output" << endl;
    int errors = 0;
    tester.find({0, 1, 2, 3, 4}, repoTitles, errors);
    if (errors != 0)
    {
        cout << "Error: output is incorrect" << endl;
    }
    else
    {
        cout << "Output is correct" << endl;
        marks += 1;
    }

    cout << "Removing two repos" << endl;
    repoList.remove(0);
    repoList.remove(2);
    repoList.remove(3);

    cout << "Checking size" << endl;
    if (repoList.size() != 3)
    {
        cout << "Error: size should be 3, but is " << repoList.size() << endl;
    }
    else
    {
        cout << "Size is correct" << endl;
        marks += 1;
    }

    cout << "Printing repos" << endl;
    tester.initCapture();
    for (int i = 0; i < repoList.size(); ++i)
    {
        repoList.get(i)->print();
    }
    tester.endCapture();

    cout << "Checking output for" << endl;
    cout << repoTitles[1] << endl;
    cout << repoTitles[2] << endl;
    cout << repoTitles[4] << endl;

    tester.find({1, 2, 4}, repoTitles, errors);
    if (errors != 0)
    {
        cout << "Error: output is incorrect" << endl;
    }
    else
    {
        cout << "Output is correct" << endl;
        marks += 1;
    }

    tester.confirmAbsent({0, 3}, repoTitles, errors);
    if (errors != 0)
    {
        cout << "Error: removed repos found" << endl;
    }
    else
    {
        cout << "No removed repos found, output is correct" << endl;
        marks += 1;
    }

    cout << "Adding a repo" << endl;
    repoList.add(repos[3]);

    cout << "Checking size" << endl;
    if (repoList.size() != 4)
    {
        cout << "Error: size should be 4, but is " << repoList.size() << endl;
    }
    else
    {
        cout << "Size is correct" << endl;
        marks += 1;
    }

    cout << "Printing repos" << endl;
    tester.initCapture();
    for (int i = 0; i < repoList.size(); ++i)
    {
        repoList.get(i)->print();
    }
    tester.endCapture();

    cout << "Checking output" << endl;
    tester.find({1, 2, 3, 4}, repoTitles, errors);
    if (errors != 0)
    {
        cout << "Error: output is incorrect" << endl;
    }
    else
    {
        cout << "Output is correct" << endl;
        marks += 1;
    }

    tester.confirmAbsent({0}, repoTitles, errors);
    if (errors != 0)
    {
        cout << "Error: removed repos found" << endl;
    }
    else
    {
        cout << "No removed repos found, output is correct" << endl;
        marks += 1;
    }

    cout << "Removing all repos" << endl;
    repoList.remove(0);
    repoList.remove(0);
    repoList.remove(0);
    repoList.remove(0);

    cout << "Checking size" << endl;
    if (repoList.size() != 0)
    {
        cout << "Error: size should be 0, but is " << repoList.size() << endl;
    }
    else
    {
        cout << "Size is correct" << endl;
        marks += 1;
    }

    cout << "Printing repos" << endl;
    tester.initCapture();
    for (int i = 0; i < repoList.size(); ++i)
    {
        repoList.get(i)->print();
    }
    tester.endCapture();

    cout << "Checking output" << endl;
    tester.confirmAbsent({0, 1, 2, 3, 4}, repoTitles, errors);
    if (errors != 0)
    {
        cout << "Error: removed repos found" << endl;
    }
    else
    {
        cout << "No removed repos found, output is correct" << endl;
        marks += 1;
    }

    cout << "RepoList test complete, deleting Repos" << endl;

    for (int i = 0; i < 5; ++i)
    {
        delete repos[i];
    }
    cout << "Marks: " << marks << "/10" << endl;
    return marks;
}

int TestControl::getHubRepoTest()
{

    cout << "GetHub repo test" << endl;
    GetHub getHub;
    initGetHub(getHub);
    int marks = 0;

    cout << "Printing repos" << endl;
    tester.initCapture();
    getHub.printRepos();
    tester.endCapture();

    cout << "Checking output" << endl;
    int errors = 0;
    tester.find({0, 1, 2, 3, 4}, repoTitles, errors);
    if (errors != 0)
    {
        cout << "Error: output is incorrect" << endl;
    }
    else
    {
        cout << "Output is correct" << endl;
        marks += 2;
    }

    cout << "Deleting repo 0" << endl;
    getHub.deleteRepo(0);

    cout << "Printing repos" << endl;
    tester.initCapture();
    getHub.printRepos();
    tester.endCapture();

    cout << "Checking output" << endl;
    tester.find({1, 2, 3, 4}, repoTitles, errors);
    if (errors != 0)
    {
        cout << "Error: output is incorrect" << endl;
    }
    else
    {
        cout << "Output is correct" << endl;
        marks += 2;
    }

    cout << "Deleting repo 2" << endl;
    getHub.deleteRepo(2);

    cout << "Printing repos" << endl;
    tester.initCapture();
    getHub.printRepos();
    tester.endCapture();

    cout << "Checking output" << endl;
    tester.find({1, 2, 4}, repoTitles, errors);
    if (errors != 0)
    {
        cout << "Error: output is incorrect" << endl;
    }
    else
    {
        cout << "Output is correct" << endl;
        marks += 2;
    }

    cout << "Printing the Repo at index 1 (should be " << repoTitles[2] << ")" << endl;
    tester.initCapture();
    getHub.printRepo(1);
    tester.endCapture();

    cout << "Checking output" << endl;
    tester.find({2}, repoTitles, errors);
    if (errors != 0)
    {
        cout << "Error: output is incorrect" << endl;
    }
    else
    {
        cout << "Output is correct" << endl;
        marks += 2;
    }
    tester.confirmAbsent({0, 1, 3, 4}, repoTitles, errors);
    if (errors != 0)
    {
        cout << "Error: other repos found" << endl;
    }
    else
    {
        cout << "No removed repos found, output is correct" << endl;
        marks += 2;
    }

    cout << "getHub repo test complete" << endl;
    cout << "Marks: " << marks << "/10" << endl;

    return marks;
}

int TestControl::getHubFileTest()
{
    cout << "GetHub repo test" << endl;
    GetHub getHub;
    initGetHub(getHub);
    int marks = 0;

    // print out and capture the relevant dates
    // so that we can search for them in the output
    string dates[5][5];
    cout << "Capturing Date data, please ignore" << endl;
    for (int i = 0; i < 5; ++i)
    {
        for (int j = 0; j < 5; j++)
        {
            tester.initCapture();
            TestControl::dates[i][j].print();
            tester.endCapture();
            dates[i][j] = tester.getOutput();
        }
    }

    cout << endl
         << endl
         << "Printing repo at index 2" << endl;
    tester.initCapture();
    getHub.printRepo(2);
    tester.endCapture();

    cout << "Checking output for correct Files and Dates" << endl;
    int errors = 0, errors2 = 0;
    tester.find({0, 1, 2, 3, 4}, fileTitles[2], errors);
    tester.find({0, 1, 2, 3, 4}, dates[2], errors2);
    if (errors != 0)
    {
        cout << "Error: output is incorrect" << endl;
    }
    else if (errors2 != 0)
    {
        cout << "Error: dates not found" << endl;
    }
    else
    {
        cout << "Output is correct" << endl;
        marks += 2;
    }

    cout << "Deleting file 2 and 3 from repo 2" << endl;
    getHub.deleteFile(2, 2);
    getHub.deleteFile(2, 2);

    cout << "Printing repo at index 2" << endl;
    tester.initCapture();
    getHub.printRepo(2);
    tester.endCapture();

    cout << "Checking output for correct Files and Dates" << endl;
    errors = 0, errors2 = 0;
    tester.find({0, 1, 4}, fileTitles[2], errors);
    tester.find({0, 1, 4}, dates[2], errors2);
    if (errors != 0)
    {
        cout << "Error: output is incorrect" << endl;
    }
    else if (errors2 != 0)
    {
        cout << "Error: dates not found" << endl;
    }
    else
    {
        cout << "Output is correct" << endl;
        marks += 2;
    }

    tester.confirmAbsent({2, 3}, fileTitles[2], errors);
    tester.confirmAbsent({2, 3}, dates[2], errors2);
    if (errors != 0)
    {
        cout << "Error: removed files found" << endl;
    }
    else if (errors2 != 0)
    {
        cout << "Error: removed dates found" << endl;
    }
    else
    {
        cout << "No removed files or dates found, output is correct" << endl;
        marks += 2;
    }

    cout << "Testing play file" << endl;
    cout << "Playing file 1 from repo 2" << endl;
    tester.initCapture();
    getHub.printFileContents(2, 1);
    tester.endCapture();

    cout << "Checking output" << endl;
    tester.find({4}, content[2], errors);
    if (errors != 0)
    {
        cout << "Error: file content not found" << endl;
    }
    else
    {
        cout << "Output is correct" << endl;
        marks += 2;
    }

    cout << "Removing all files from repo 2" << endl;
    getHub.deleteFile(2, 0);
    getHub.deleteFile(2, 0);
    getHub.deleteFile(2, 0);

    cout << "Printing repo at index 2" << endl;
    tester.initCapture();
    getHub.printRepo(2);
    tester.endCapture();

    cout << "Checking output for correct Files and Dates (should be none)" << endl;

    errors = 0, errors2 = 0;
    tester.confirmAbsent({0, 1, 2, 3, 4}, fileTitles[2], errors);

    if (errors != 0 || errors2 != 0)
    {
        cout << "Error: something was found" << endl;
    }
    else
    {
        cout << "Output is correct" << endl;
        marks += 2;
    }

    cout << "getHub file test complete" << endl;
    cout << "Marks: " << marks << "/10" << endl;

    return marks;
}

string TestControl::repoTitles[5] = {
    "TechTrendsUnleashed",
    "CulinaryExplorerHQ",
    "LaughLagoonNetwork",
    "FitnessFiestaRepo",
    "MindfulMysteriesShow"};

string TestControl::owners[5] = {"Bob", "Sue", "Joe", "Mary", "Bill"};

// These do not make any sense - just for testing
string TestControl::fileTitles[5][5] = {
    {"Lion", "Lion 2", "Fishbowl", "Alien", "Castle"},
    {"Love", "Anonymous", "Games", "Music", "Welcome"},
    {"Monkey", "Stranger", "Ant", "Turtle", "Smile"},
    {"Crab", "City", "Apple", "Pig", "Dog"},
    {"Helicopter", "Unlock", "Santa's Sleigh", "Guitar", "Whale"}};

// some of these dates are out of order - on purpose for testing
Date TestControl::dates[5][5] = {
    {Date(2024, 1, 1), Date(2023, 1, 2), Date(2024, 8, 3), Date(2022, 6, 4), Date(2023, 3, 5)},
    {Date(2024, 1, 1), Date(2023, 1, 2), Date(2024, 8, 3), Date(2022, 6, 4), Date(2023, 3, 5)},
    {Date(2023, 4, 15), Date(2024, 11, 7), Date(2023, 8, 22), Date(2024, 5, 12), Date(2023, 6, 30)},
    {Date(2023, 7, 18), Date(2024, 3, 12), Date(2023, 11, 5), Date(2024, 2, 20), Date(2023, 9, 9)},
    {Date(2024, 7, 25), Date(2023, 2, 14), Date(2024, 10, 8), Date(2023, 5, 28), Date(2024, 1, 19)},
};

string TestControl::content[5][5] = {
    R"(
 ▄▀▀▀▀▀───▄█▀▀▀█▄
▐▄▄▄▄▄▄▄▄██▌▀▄▀▐██
▐▒▒▒▒▒▒▒▒███▌▀▐███
 ▌▒▓▒▒▒▒▓▒██▌▀▐██
 ▌▓▐▀▀▀▀▌▓─▀▀▀▀▀)",

    R"(
┼┼┼┼┼┼┼┼▓▓▓▓┼┼┼
┼╔═▒▒▒▒▓▄░░▄▓┼┼
┼▀┼▒▒▒▓▓▒──▒▓▓┼
┼┼┼▒▌▒▐┼▓▓▓▓┼┼┼)",

    R"(
─▀▀▌───────▐▀▀
─▄▀░◌░░░░░░░▀▄
▐░░◌░▄▀██▄█░░░▌
▐░░░▀████▀▄░░░▌
═▀▄▄▄▄▄▄▄▄▄▄▄▀═
)",

    R"(
▒▒▄▀▀▀▀▀▄▒▒▒▒▒▄▄▄▄▄▒▒▒
▒▐░▄░░░▄░▌▒▒▄█▄█▄█▄█▄▒
▒▐░▀▀░▀▀░▌▒▒▒▒▒░░░▒▒▒▒
▒▒▀▄░═░▄▀▒▒▒▒▒▒░░░▒▒▒▒
▒▒▐░▀▄▀░▌▒▒▒▒▒▒░░░▒▒▒▒
)",
    R"(
─────────█▄██▄█
█▄█▄█▄█▄█▐█┼██▌█▄█▄█▄█▄█
███┼█████▐████▌█████┼███
█████████▐████▌█████████
)",
    R"(
█───▄▀▀▀▀▄─▐█▌▐█▌▐██
█──▐▄▄────▌─█▌▐█─▐▌─
█──▐█▀█─▀─▌─█▌▐█─▐██
█──▐████▄▄▌─▐▌▐▌─▐▌─
███─▀████▀───██──▐██
)",
    R"(
─────█─▄▀█──█▀▄─█─────
────▐▌──────────▐▌────
────█▌▀▄──▄▄──▄▀▐█────
───▐██──▀▀──▀▀──██▌───
──▄████▄──▐▌──▄████▄──
)",
    R"(
─▄▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▄
█░░░█░░░░░░░░░░▄▄░██░█
█░▀▀█▀▀░▄▀░▄▀░░▀▀░▄▄░█
█░░░▀░░░▄▄▄▄▄░░██░▀▀░█
─▀▄▄▄▄▄▀─────▀▄▄▄▄▄▄▀
)",
    R"(
║░█░█░║░█░█░█░║░█░█░║
║░█░█░║░█░█░█░║░█░█░║
║░║░║░║░║░║░║░║░║░║░║
╚═╩═╩═╩═╩═╩═╩═╩═╩═╩═╝
)",
    R"(
█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█
█░░╦─╦╔╗╦─╔╗╔╗╔╦╗╔╗░░█
█░░║║║╠─║─║─║║║║║╠─░░█
█░░╚╩╝╚╝╚╝╚╝╚╝╩─╩╚╝░░█
█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█

)",
    R"(
───▄██▄─────────────▄▄
──█████▄▄▄▄───────▄▀
────▀██▀▀████▄───▄▀
───▄█▀▄██▄████▄─▄█
▄▄█▀▄▄█─▀████▀██▀
)",
    R"(
█▓▒▓█▀██▀█▄░░▄█▀██▀█▓▒▓█
█▓▒░▀▄▄▄▄▄█░░█▄▄▄▄▄▀░▒▓█
█▓▓▒░░░░░▒▓░░▓▒░░░░░▒▓▓█
)",
    R"(
──▄──▄────▄▀
───▀▄─█─▄▀▄▄▄
▄██▄████▄██▄▀█▄
─▀▀─█▀█▀▄▀███▀
──▄▄▀─█──▀▄▄
)",
    R"(
─▄▀▀▀▄────▄▀█▀▀█▄
▄▀─▀─▀▄▄▀█▄▀─▄▀─▄▀▄
█▄▀█───█─█▄▄▀─▄▀─▄▀▄
──█▄▄▀▀█▄─▀▀▀▀▀▀▀─▄█
─────▄████▀▀▀▀████─▀▄
)",
    R"(
╔══╗░░░░╔╦╗░░╔═════╗
║╚═╬════╬╣╠═╗║░▀░▀░║
╠═╗║╔╗╔╗║║║╩╣║╚═══╝║
╚══╩╝╚╝╚╩╩╩═╝╚═════╝
)",
    R"(
░░▄█▀▀▀░░░░░░░░▀▀▀█▄
▄███▄▄░░▀▄██▄▀░░▄▄███▄
▀██▄▄▄▄████████▄▄▄▄██▀
░░▄▄▄▄██████████▄▄▄▄
░▐▐▀▐▀░▀██████▀░▀▌▀▌▌
)",
    R"(
▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
▒▒▄▄▄▒▒▒█▒▒▒▒▄▒▒▒▒▒▒▒▒
▒█▀█▀█▒█▀█▒▒█▀█▒▄███▄▒
░█▀█▀█░█▀██░█▀█░█▄█▄█░
░█▀█▀█░█▀████▀█░█▄█▄█░
████████▀█████████████
)",
    R"(
┊┊┊┊┊┊┊╱▏┊┊┊┊┊┊┊
┊┊┊┊┊┊▕╱┊┊┊┊┊┊┊┊
┊┊┊╱▔▔╲┊╱▔▔╲┊┊┊┊
┊┊▕┈┈┈┈▔┈┈┈╱┊┊┊┊
┊┊▕┈┈┈┈┈┈┈┈╲┊┊┊┊
┊┊┊╲┈┈┈┈┈┈┈╱┊┊┊┊
┊┊┊┊╲▂▂▂▂▂╱┊┊┊┊┊
)",
    R"(
▂╱▔▔╲╱▔▔▔▔╲╱▔▔╲▂
╲┈▔╲┊╭╮┈┈╭╮┊╱▔┈╱
┊▔╲╱▏┈╱▔▔╲┈▕╲╱▔┊
┊┊┊┃┈┈▏┃┃▕┈┈┃┊┊┊
┊┊┊▏╲┈╲▂▂╱┈╱▕┊┊┊
)",
    R"(
╥━━━━━━━━╭━━╮━━┳
╢╭╮╭━━━━━┫┃▋▋━▅┣
╢┃╰┫┈┈┈┈┈┃┃┈┈╰┫┣
╢╰━┫┈┈┈┈┈╰╯╰┳━╯┣
╢┊┊┃┏┳┳━━┓┏┳┫┊┊┣
╨━━┗┛┗┛━━┗┛┗┛━━┻
)",
    R"(
▀▀▀▀█▀▀▀▀
─▄▀█▀▀█──────▄
█▄▄█▄▄██████▀
▀▀█▀▀▀█▀▀
─▀▀▀▀▀▀▀
)",
    R"(
──▄▀▀▀▄───────────────
──█───█───────────────
─███████─────────▄▀▀▄─
░██─▀─██░░█▀█▀▀▀▀█░░█░
░███▄███░░▀░▀░░░░░▀▀░░
)",
    R"(
░╔╔╩╩╝
▄██▄
░░██████▄░░░░░░▄▄▄▄▄▄█
░░█▀█▀█▀█░░▄░▄████████
░▄▌▄▌▄▌▄▌░▀▄▄▄▄█▄▄▄▄█▄
)",
    R"(
░▄▀▀▀▀▄░░▄▄
█░░░░░░▀▀░░█░░░░░░▄░▄
█░║░░░░██░████████████ 
█░░░░░░▄▄░░█░░░░░░▀░▀
░▀▄▄▄▄▀░░▀▀
)",
    R"(
─────▀▄▀─────▄─────▄
──▄███████▄──▀██▄██▀
▄█████▀█████▄──▄█
███████▀████████▀
─▄▄▄▄▄▄███████▀
)"};
