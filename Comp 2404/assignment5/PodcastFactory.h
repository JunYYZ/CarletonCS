#ifndef PODCASTFACTORY_H
#define PODCASTFACTORY_H

#include <string>
#include "Podcast.h"
#include "Search.h"
#include "Episode.h"

class PodcastFactory {
public:
    Episode* createEpisode(const std::string& pod, const std::string& host, const std::string& title);
    Podcast* createPodcast(const std::string& title, const std::string& host);

    Search* hostSearch(const std::string& host);
    Search* categorySearch(const std::string& category);
    Search* hostCatSearch(const std::string& host, const std::string& category);
};

#endif // PODCASTFACTORY_H
