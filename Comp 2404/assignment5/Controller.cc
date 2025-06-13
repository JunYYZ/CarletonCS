
#include "Controller.h"
#include "View.h"
#include <fstream>
#include <limits>
#include <vector>

void Controller::launch()
{
    cout << "Launching..." << endl;
    initFromFile();
    cout << "Done initializing" << endl;
    int choice = -1;

    // This is what the View shows:
    // cout << "What would you like to do?"<< endl;
    // cout << "  (1) Show all Podcasts" << endl;
    // cout << "  (2) Show single Podcast" << endl;
    // cout << "  (3) Get episode by host" << endl;
    // cout << "  (4) Get episode by category" << endl;
    // cout << "  (5) Get episode by host or category" << endl;
    // cout << "  (6) Print current episode list" << endl;
    // cout << "  (7) Play current episode list" << endl;
    // cout << "  (8) Toggle video" << endl;
    // cout << "  (0) Exit" << endl<<endl;

    while (true)
    {
        choice = -1;
        vector<string> menu = {
            "Show all Podcasts",
            "Show single Podcast",
            "Get episode by host",
            "Get episode by category",
            "Get episode by host or category",
            "Print current episode list",
            "Play current episode list",
            "Toggle video"};
        view.menu(menu, choice);
        cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

        if (choice == 0)
            break;
        switch (choice)
        {
        case 1:
            showAllPodcasts();
            break;
        case 2:
            showSinglePodcast();
            break;
        case 3:
            getEpisodeByH();
            break;
        case 4:
            getEpisodeByC();
            break;
        case 5:
            getEpisodeByHandC();
            break;
        case 6:
            printCurrentEpisode();
            break;
        case 7:
            playCurrentEpisode();
            break;
        case 8:
            toggleVideo();
            break;
        }
    }

    cout << "Good-bye!" << endl;
}

void Controller::initFromFile()
{
    ifstream episodeFile;
    episodeFile.open("media/media.txt");
    string podcast, host, temp;
    string title, content, category, video;
    int numEpisodes = 0;

    while (true)
    {
        if (!getline(episodeFile, podcast))
            break;
        getline(episodeFile, host);

        getline(episodeFile, temp);
        numEpisodes = stoi(temp);
        // this one should be an integer representing the
        // number of episodes on this podcast
        av.addPodcast(pf.createPodcast(podcast, host));
        for (int i = 0; i < numEpisodes; ++i)
        {
            getline(episodeFile, title);
            Episode *episode = pf.createEpisode(podcast, host, title);
            if (episode == nullptr)
            {
                cout << "WARNING*** Episode " << title << " not found" << endl;
                continue;
            }
            av.addEpisode(episode, podcast);
        }
    }

    episodeFile.close();
}

void Controller::showAllPodcasts()
{
    cout << "Showing all podcasts: " << endl;
    view.printAllPodcasts(av.getPodcasts());
}

void Controller::showSinglePodcast()
{
    int choice = -1;
    view.podcastMenu(av.getPodcasts(), choice);
    Podcast *podcast = av.getPodcast(choice);
    view.printPodcast(podcast);
}

void Controller::getEpisodeByH()
{
    string host;
    view.promptHost(host);
    Search *crit = pf.hostSearch(host);
    playlist.clear();
    av.getEpisodes(*crit, playlist);
    delete crit;
}
void Controller::getEpisodeByC()
{
    string cat;
    view.promptCategory(cat);
    Search *crit = pf.categorySearch(cat);
    playlist.clear();
    av.getEpisodes(*crit, playlist);
    delete crit;
}
void Controller::getEpisodeByHandC()
{
    string host, cat;
    view.promptHost(host);
    view.promptCategory(cat);
    Search *crit = pf.hostCatSearch(host, cat);
    playlist.clear();
    av.getEpisodes(*crit, playlist);
    delete crit;
}
void Controller::printCurrentEpisode()
{
    cout << "Playlist size: " << playlist.getSize() << endl;
    view.printPlaylist(playlist);
}
void Controller::playCurrentEpisode()
{
    view.playPlaylist(playlist);
}
void Controller::toggleVideo()
{
    view.promptVideo();
}