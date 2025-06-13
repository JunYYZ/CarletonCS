
#include "TestControl.h"
#include "Repo.h"
#include <limits>

using namespace std;

#define NUM_REPOS 4
#define NUM_FILES 5


int TestControl::launch()
{
    int choice = 0;
    cout<<"0. Add Files to Repo and print test"<<endl;
    cout<<"1. Destructor test"<<endl;
    cout<<"2. Deep copy Repo test"<<endl;
    cin >> choice;
    switch (choice)
    {
    case 0:
        return repoTest();
    case 1:
        return destructorTest();
    case 2:
        return deepCopyTest();
    }
    return 0;
}


int TestControl::repoTest()
{
    Repo *repos[2];
    vector<int> rfile, rrepo; // random file, random repos
    vector<int>::iterator ifile, irepo; // iterators for the vectors

    int count = 0;
    tester.ran(rrepo, 2, 5);   // choose 2 out of 5 repos

    // store strings we search for for testing purposes
    vector<string> rTitles;
    vector<string> fNames;
    vector<string> fileContent;

    for (irepo = rrepo.begin(); irepo != rrepo.end(); ++irepo)
    {
        std::cout << endl
                  << "Adding Repo  " << repoTitles[*irepo] << endl;
        repos[count] = new Repo(repoTitles[*irepo], owners[*irepo]);
        rTitles.push_back(repoTitles[*irepo]);

        rfile.clear();
        tester.ran(rfile, 2, 5); // choose 2 out of 5 files

        for (ifile = rfile.begin(); ifile != rfile.end(); ++ifile)
        {
            std::cout << "Adding File "<<fileNames[*irepo][*ifile]<<" to Repo: " << repoTitles[*irepo] << endl;
            repos[count]->addFile(fileNames[*irepo][*ifile], 
                                        content[*irepo][*ifile],
                                        dates[*irepo][*ifile]);
            fNames.push_back(fileNames[*irepo][*ifile]);
            fileContent.push_back(content[*irepo][*ifile]);
        }
        ++count;
    }

    std::cout << endl
              << "Printing repos with files (should be 2 repos and 2 files each repo):" << endl;

    
    tester.initCapture();
    for (int i = 0; i < 2; ++i)
    {
        repos[i]->printFiles();
    }

    tester.endCapture();



    int error = 0;

    tester.find(rTitles, error);

    std::cout<<"All repo titles "<<(error==0?"found":"not found")<<endl;


    error = 0;
    tester.find(fNames, error); 
    std::cout<<"All file titles "<<(error==0?"found":"not found")<<endl;

    std::cout<<"Deleting repos"<<endl;
    for (int i = 0; i < 2; ++i)
    {
        delete repos[i];
    }

    return 0;
}

int TestControl::destructorTest()
{
    Repo *repos[2];
    vector<int> rfile, rrepo; // random file, random repos
    vector<int>::iterator ifile, irepo; // iterators for the vectors

    int count = 0;
    tester.ran(rrepo, 2, 5);   // choose 2 out of 5 repos

    // store strings we search for for testing purposes
    vector<string> rTitles;
    vector<string> fNames;
    vector<string> fileContent;

    for (irepo = rrepo.begin(); irepo != rrepo.end(); ++irepo)
    {
        std::cout << endl
                  << "Adding Repo  " << repoTitles[*irepo] << endl;
        repos[count] = new Repo(repoTitles[*irepo], owners[*irepo]);
        rTitles.push_back(repoTitles[*irepo]);

        rfile.clear();
        tester.ran(rfile, 2, 5); // choose 2 out of 5 files

        for (ifile = rfile.begin(); ifile != rfile.end(); ++ifile)
        {
            std::cout << "Adding File "<<fileNames[*irepo][*ifile]<<" to Repo: " << repoTitles[*irepo] << endl;
            repos[count]->addFile(fileNames[*irepo][*ifile], 
                                        content[*irepo][*ifile],
                                        dates[*irepo][*ifile]);
            fNames.push_back(fileNames[*irepo][*ifile]);
            fileContent.push_back(content[*irepo][*ifile]);
        }
        ++count;
    }
    cout << "Delete all files in first repo" << endl;
    
    while (repos[0]->getNumFiles()!=0)
    {
        cout<<"Deleting file in TestControl"<<endl;
        repos[0]->removeFile(0);
    }

    cout << "Destroy all repos" << endl;
    for (int j = 0; j < 2; ++j)
    {
        delete repos[j];
    }

    

    cout << "Destructor test complete" << endl;
    return 0;
}

int TestControl::deepCopyTest()
{
    Repo *repos[2];
    Repo *copies[2];
    vector<int> rfile, rrepo; // random file, random repos
    vector<int>::iterator ifile, irepo; // iterators for the vectors

    int count = 0;
    tester.ran(rrepo, 2, 5);   // choose 2 out of 5 repos

    // store strings we search for for testing purposes
    vector<string> rTitles;
    vector<string> fNames;
    vector<string> fileContent;

    for (irepo = rrepo.begin(); irepo != rrepo.end(); ++irepo)
    {
        std::cout << endl
                  << "Adding Repo  " << repoTitles[*irepo] << endl;
        repos[count] = new Repo(repoTitles[*irepo], owners[*irepo]);
        rTitles.push_back(repoTitles[*irepo]);

        rfile.clear();
        tester.ran(rfile, 2, 5); // choose 2 out of 5 files

        for (ifile = rfile.begin(); ifile != rfile.end(); ++ifile)
        {
            std::cout << "Adding File "<<fileNames[*irepo][*ifile]<<" to Repo: " << repoTitles[*irepo] << endl;
            repos[count]->addFile(fileNames[*irepo][*ifile], 
                                        content[*irepo][*ifile],
                                        dates[*irepo][*ifile]);
            fNames.push_back(fileNames[*irepo][*ifile]);
            fileContent.push_back(content[*irepo][*ifile]);
        }
        ++count;
    }

    cout<<"Files found in first repo: "<<repos[0]->getNumFiles()<<endl;
    cout<<"Files found in second repo: "<<repos[1]->getNumFiles()<<endl;

    cout << "Making a deep copy of the two repos" << endl;
    copies[0] = new Repo(*repos[0]);
    copies[1] = new Repo(*repos[1]);

    cout << "Deleting all files in first repo, then deleting both original repos (copies should still exist)" << endl;

    while (repos[0]->getNumFiles()!=0)
    {
        cout<<"Deleting file in first Repo"<<endl;
        repos[0]->removeFile(0);
    }

    cout<<"Files found in first repo: "<<repos[0]->getNumFiles()<<endl;
    cout<<"Files found in second repo: "<<repos[1]->getNumFiles()<<endl;
    delete repos[0];
    delete repos[1];
    // Date date;
    // repos[0]->addFile("junk", "junk", date);
    // repos[0]->addFile("junk", "junk", date);
    // repos[0]->addFile("junk", "junk", date);
    // repos[0]->addFile("junk", "junk", date);

    cout << "Printing copied Repos with files" << endl;

    tester.initCapture();
    for (int i = 0; i < 2; ++i)
    {
        copies[i]->printFiles();
    }

    tester.endCapture();
    int error = 0;

    tester.find(rTitles, error);

    std::cout<<"All repo titles "<<(error==0?"found":"not found")<<endl;


    error = 0;
    tester.find(fNames, error); 
    std::cout<<"All file names "<<(error==0?"found":"not found")<<endl;

    std::cout<<"Deleting copied repos"<<endl;
    for (int i = 0; i < 2; ++i)
    {
        delete copies[i];
    }

    return 0;
}


const string TestControl::repoTitles[5] = {
    "TechTrendsUnleashed",
    "CulinaryExplorerHQ",
    "LaughLagoonNetwork",
    "FitnessFiestaRepo",
    "MindfulMysteriesShow"};

const string TestControl::owners[5] = {"Bob", "Sue", "Joe", "Mary", "Bill"};

// These do not make any sense - just for testing
const string TestControl::fileNames[5][5] = {
    {"Lion", "Lion 2", "Fishbowl", "Alien", "Castle"},
    {"Love", "Anonymous", "Games","Music","Welcome"},
    {"Monkey", "Stranger","Ant","Turtle","Smile"},
    {"Crab","City","Apple","Pig","Dog"},
    {"Helicopter","Unlock","Santa's Sleigh", "Guitar","Whale"}
};

// some of these dates are out of order - on purpose for testing
 const Date TestControl::dates[5][5]={
    {Date(2024, 1, 1), Date(2023, 1, 2), Date(2024, 8, 3), Date(2022, 6, 4), Date(2023, 3, 5)},
    {Date(2024, 1, 1), Date(2023, 1, 2), Date(2024, 8, 3), Date(2022, 6, 4), Date(2023, 3, 5)},
    {Date(2023, 4, 15), Date(2024, 11, 7), Date(2023, 8, 22), Date(2024, 5, 12), Date(2023, 6, 30)},
    {Date(2023, 7, 18), Date(2024, 3, 12), Date(2023, 11, 5), Date(2024, 2, 20), Date(2023, 9, 9)},
    {Date(2024, 7, 25), Date(2023, 2, 14), Date(2024, 10, 8), Date(2023, 5, 28), Date(2024, 1, 19)},
 };

const string TestControl::content[5][5] = {
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
)"
};

