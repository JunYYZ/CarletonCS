
#include "TestControl.h"
#include <limits>
#include <vector>
#include <algorithm>
#include "Search.h"
#include <fstream>

using namespace std;

void TestControl::launch(){

    vector<string> menu ={
        "Test add and print Podcasts",
        "Test add and print Episodes",
        "Test get Episodes by host, and test print episodes from View class",
        "Test get Episodes by category",
        "Test get Episodes by host or category",
        //"Test print current episode list",
        "Test play current episode list with video",
        "Test all and get mark"
    };

    int choice = -1;

    while (choice!= 0){
        view.menu(menu, choice);
        tester.clearInputBuffer();
        switch(choice){
            case 1: tester.recordMark(1, testAddPodcasts()); break;
            case 2: tester.recordMark(2, testAddEpisodes()); break;
            case 3: tester.recordMark(3, testGetEpisodesByHost()); break;
            case 4: tester.recordMark(4, testGetEpisodesByCategory()); break;
            case 5: tester.recordMark(5, testGetEpisodesByHostAndCategory()); break;
            // case 6: testPrintCurrentEpisodeList(); break;
            case 6: tester.recordMark(6, testPlayCurrentEpisodeList()); break;
            case 7: tester.recordMark(7, testAllAndMark(), 22); break;
        }
    }
    std::cout<<"exiting program!!!"<<endl;
}

void TestControl::initPodcasts(Podify& podify,vector<string>& podcasts, vector<string>& hosts){
    ifstream podFile;
    podFile.open("media/media.txt");
    string podcast, host, temp;
    string title, content, category, video;
    int numEpisodes = 0;

    while( true ){
        if(!getline(podFile, podcast))break;
        getline(podFile, host);
        getline(podFile, temp);
        numEpisodes = stoi(temp);
        //this one should be an integer representing the 
        //number of episodes on this podcast
        podify.addPodcast(pf.createPodcast(podcast, host));
        // we will use these vectors to test the output
        podcasts.push_back(podcast);
        hosts.push_back(host);
        for (int i = 0; i < numEpisodes; ++i){
            getline(podFile, title);
            // we skip adding the episodes to the podcast
            // but we still have to read in the titles
        }
        
    }

    podFile.close();
}

void TestControl::initWithEpisodes(Podify& podify,
                            vector<string>& podcasts, 
                            vector<string>& hosts, 
                            vector<string>& epTitles){
    ifstream podFile;
    podFile.open("media/media.txt");
    string podcast, host, temp;
    string title, content, category, video;
    int numEpisodes = 0;

    while( true ){
        if(!getline(podFile, podcast))break;
        getline(podFile, host);

        getline(podFile, temp);
        numEpisodes = stoi(temp);
        epTitles.push_back(podcast);
        epTitles.push_back(temp); //the number of episodes stored as a string
        podcasts.push_back(podcast);
        hosts.push_back(host);
        podify.addPodcast(pf.createPodcast(podcast, host));
        for (int i = 0; i < numEpisodes; ++i){
            getline(podFile, title);
            Episode* episode = pf.createEpisode(podcast, host, title);
            if (episode == nullptr){
                cout<<"WARNING*** Episode "<<title<< " not found"<<endl;
                continue;
            }else{
                epTitles.push_back(title);
            }
            podify.addEpisode(episode, podcast);
        }
        
    }
    podFile.close();
    
}

int TestControl::testAddPodcasts(){
    cout<<"Testing addPodcasts()"<<endl;
    Podify podify;
    vector<string> podcasts;
    vector<string> hosts;
    initPodcasts(podify, podcasts, hosts);

    cout<<endl<<"Printing all podcasts"<<endl<<endl;
    tester.initCapture();
    view.printAllPodcasts(podify.getPodcasts());
    tester.endCapture();

    int error = 0;
    tester.find(podcasts, error);
    if (error>0){
        cout<<"Error: "<<error<<" podcasts not found"<<endl;
    }else{
        cout<<"All podcast titles found"<<endl;
    }
    tester.find(hosts, error);
    if (error>0){
        cout<<"Error: "<<error<<" hosts not found"<<endl;
    }else{
        cout<<"All hosts found"<<endl;
    }

    int mark = 2 - error;
    if (mark < 0)mark = 0;

    mark += 1; // Your code didn't crash - congrats!

    cout<<"Test complete, mark: "<<mark<<"/3"<<endl;
    return mark;
}

int TestControl::testAddEpisodes(){
    cout<<"Testing addEpisodes()"<<endl;
    Podify podify;
    vector<string> podcasts;
    vector<string> hosts;
    vector<string> epTitles;
    initWithEpisodes(podify, podcasts, hosts, epTitles);

    cout<<endl<<"Printing podcasts with episodes"<<endl<<endl;
    int errors = 0;
    int index = 0;
    for (int i = 0; i < podify.getPodcasts().getSize(); ++i){
        tester.initCapture();
        podify.getPodcast(i)->printWithEpisodes(cout);
        tester.endCapture();

        string podcast = epTitles[index++];
        // cout<<"stoi "<<epTitles[index]<<endl;   
        int numEps = stoi(epTitles[index++]);
        vector<string> episodes;
        for (int j = 0; j < numEps; ++j){
            episodes.push_back(epTitles[index++]);
        }
        int error = 0;
        tester.find(episodes, error);
        if (error>0){
            cout<<"Error: "<<error<<" episodes not found"<<endl;
        }else{
            cout<<"All episodes found for "<<podcast<<endl;
        }
        errors += error;
    }

    int mark = 2 - errors;
    if (mark < 0)mark = 0;
    mark += 1; // Your code didn't crash - congrats!
    cout<<"Test complete, mark: "<<mark<<"/3"<<endl;
    return mark;
   
}

int TestControl::testGetEpisodesByHost(){
    cout<<"Testing get episodes by host and playPlaylist"<<endl;
    Podify podify;
    vector<string> podcasts;
    vector<string> hosts;
    vector<string> epTitles;
    initWithEpisodes(podify, podcasts, hosts, epTitles);

    // string hosts[] = {"Bif", "Elsa", "Weird Al", "Pat", "Dave Grohl"};
    vector<int> findHosts;
    int numHosts = 5;
    tester.ran(findHosts, 2, numHosts);

    vector<string> absentHosts;
    for (int i = 0; i < hosts.size(); ++i){
        if (find(findHosts.begin(), findHosts.end(), i) == findHosts.end()){
            absentHosts.push_back(hosts[i]);
        }
    }
    
    int errors = 0;

    for (vector<int>::iterator it = findHosts.begin(); it != findHosts.end(); ++it){
        
        int host = *it;
        Search* search = pf.hostSearch(hosts[host]);
        Array<Episode*> episodes;
        podify.getEpisodes(*search, episodes);
        delete search;
        cout<<"Printing episodes by host "<<hosts[host]<<endl;
        tester.initCapture();
        view.playPlaylist(episodes);
        tester.endCapture();

        int errors = 0;
        vector<string> titles;
        titles.push_back(hosts[host]);
        // this should gather all the episode titles with this host
        for (int i = 0; i < epTitles.size(); ++i){
            if (epTitles[i] == hosts[host]){
                int numEps = stoi(epTitles[++i]);
                for (int j = 0; j < numEps; ++j){
                    titles.push_back(epTitles[++i]);
                }
                break;
            }
        }
        tester.find(titles, errors);
        if (errors>0){
            cout<<"Error: "<<errors<<" episodes not found"<<endl;
        }else{
            cout<<"All episodes found for "<<hosts[host]<<endl;
        }

        tester.confirmAbsent(absentHosts, errors);
        if (errors>0){
            cout<<"Error: "<<errors<<" episodes found for absent hosts"<<endl;
        }else{
            cout<<"No episodes found for absent hosts"<<endl;
        }
    }

    int mark = 4 - errors;
    if (mark < 0)mark = 0;
    mark += 1; // Your code didn't crash - congrats!
    cout<<"Test complete, mark: "<<mark<<"/5"<<endl;
    return mark;
}

int TestControl::testGetEpisodesByCategory(){
    cout<<"Testing get episodes by category - choosing 2 categories at random"<<endl;
    Podify podify;
    vector<string> podcasts;
    vector<string> hosts;
    vector<string> epTitles;
    initWithEpisodes(podify, podcasts, hosts, epTitles);

    string categories[] = {"History", "Local", "R&B", "Parody", "Grunge", "Politics", "Health"};
    int numEachCat[] = {4, 1, 5, 5, 2, 1, 2};
    
    vector<int> findCats;
    int numCats = 7;
    tester.ran(findCats, 2, numCats);
    
    int errors = 0;

    for (vector<int>::iterator it = findCats.begin(); it != findCats.end(); ++it){
        int cat = *it;    
    
        Search* search = pf.categorySearch(categories[cat]);
        Array<Episode*> episodes;
        podify.getEpisodes(*search, episodes);
        delete search;
        cout<<"Printing episodes by category "<<categories[cat]<<endl;
        view.playPlaylist(episodes);

        

        if (episodes.getSize() != numEachCat[cat]){
            cout<<"Error: "<<numEachCat[cat]<<" episodes expected, "<<episodes.getSize()<<" found"<<endl;
            errors++;
        }
        
        for (int i = 0; i < episodes.getSize(); ++i){
            if (episodes[i]->getCategory() != categories[cat]){
                errors++;
            }
        }
        if (errors>0){
            cout<<"Error: "<<errors<<" incorrect categories found"<<endl;
        }else{
            cout<<"All episodes found for "<<categories[cat]<<endl;
        }

    }

    int mark = 2 - errors;
    if (mark < 0)mark = 0;

    mark += 1; // Your code didn't crash - congrats!

    cout<<"Test complete, mark: "<<mark<<"/3"<<endl;
    return mark;
}

int TestControl::testGetEpisodesByHostAndCategory(){
    cout<<"Testing get episodes by host or category - choosing 2 categories at random"<<endl;
    Podify podify;
    vector<string> podcasts;
    vector<string> hosts;
    vector<string> epTitles;
    initWithEpisodes(podify, podcasts, hosts, epTitles);

    string categories[] = {"History", "Local", "R&B", "Parody", "Grunge", "Politics", "Health"};
    int numEachCat[] = {4, 1, 5, 5, 2, 1, 2};
    
    int numCats = 7;
    int cat = tester.ran(0, numCats);
    int numHosts = 5;
    int host = tester.ran(0, numHosts);
    
    int errors = 0;

    
    Search* search = pf.hostCatSearch(hosts[host],categories[cat]);
    Array<Episode*> episodes;
    podify.getEpisodes(*search, episodes);
    delete search;
    cout<<"Printing episodes by host "<<hosts[host]<<" or category "<<categories[cat]<<endl;
    view.playPlaylist(episodes);

    
    
    for (int i = 0; i < episodes.getSize(); ++i){
        if (episodes[i]->getCategory() != categories[cat] && episodes[i]->getHost() != hosts[host]){
            errors++;
        }
    }
    if (errors>0){
        cout<<"Error: "<<errors<<" incorrect categories or hosts found"<<endl;
    }else{
        cout<<"All episodes found for "<<categories[cat]<<" or "<<hosts[host]<<endl;
    }



    int mark = 4 - errors;
    if (mark < 0)mark = 0;
    mark += 1; // Your code didn't crash - congrats!

    cout<<"Test complete, mark: "<<mark<<"/5"<<endl;
    return mark;
}

// int TestControl::testPrintCurrentEpisodeList(){

// }

int TestControl::testPlayCurrentEpisodeList(){
    vector<string> toFind = {"[[[[]", "[[[[]", "(~v~)",
                            ",'SSt`.", "<( ^.^ )"
                            };
    cout<<"Testing get episodes by host and playPlaylist"<<endl;
    Podify podify;
    vector<string> podcasts;
    vector<string> hosts;
    vector<string> epTitles;
    initWithEpisodes(podify, podcasts, hosts, epTitles);

    string host= "Bif";
    
    Search* search = pf.hostSearch(host);
    Array<Episode*> episodes;
    podify.getEpisodes(*search, episodes);
    delete search;
    for (int i = 0; i < episodes.getSize(); ++i){
        cout<<episodes[i]->getVideoFile()<<endl;
    }
    view.toggleVideo(true);
    cout<<"Playing episodes (with video) by host "<<host<<endl;
    tester.initCapture();
    view.playPlaylist(episodes);
    tester.endCapture();
    view.toggleVideo(false);
    int errors = 0;

    tester.find(toFind, errors);

    if (errors>0){
        cout<<"Error: "<<errors<<" videos not found"<<endl;   
    }else{
        cout<<"Evidence of videos found"<<endl;
    }
    

    

    int mark = 2 - errors;
    if (mark < 0)mark = 0;
    mark += 1; // Your code didn't crash - congrats!

    cout<<"Test complete, mark: "<<mark<<"/3"<<endl;
    return mark;
}

int TestControl::testAllAndMark(){
    int mark = 0;
    mark += testAddPodcasts();
    mark += testAddEpisodes();
    mark += testGetEpisodesByHost();
    mark += testGetEpisodesByCategory();
    mark += testGetEpisodesByHostAndCategory();
    // mark += testPrintCurrentEpisodeList();
    mark += testPlayCurrentEpisodeList();
    return mark;
}



