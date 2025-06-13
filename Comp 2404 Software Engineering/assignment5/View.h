#ifndef VIEW_H
#define VIEW_H

#include <iostream>
#include <string>
#include <vector>
#include "Podcast.h"
#include "PodcastPlayer.h"

using namespace std;

class View
{
public:
    // constructors
    View();
    ~View();
    void menu(std::vector<std::string> &, int &choice);
    void printAllPodcasts(const Array<Podcast *> &podcasts);
    void podcastMenu(const Array<Podcast *> &podcasts, int &choice);
    void printPodcast(const Podcast *);
    // void episodeMenu(Podcast&, int& choice);
    void promptHost(std::string &host);
    void promptCategory(std::string &category);
    void printPlaylist(Array<Episode *> &);
    void playPlaylist(Array<Episode *> &);

    void promptVideo();
    void toggleVideo(bool);

private:
    PodcastPlayer *player;
};

#endif // VIEW_H
