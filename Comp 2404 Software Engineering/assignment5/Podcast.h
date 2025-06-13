#ifndef PODCAST_H
#define PODCAST_H

#include <string>
#include "Array.h"
#include "Episode.h"

class Podcast
{
    std::string title, host;
    Array<Episode *> episodes;

public:
    Podcast(const std::string &title, const std::string &host)
        : title(title), host(host) {}

    ~Podcast();

    bool equals(const std::string &title) const;
    Episode *get(int index) const;
    int getSize() const { return episodes.getSize(); }
    void add(Episode *episode) { episodes += episode; }
    void print(std::ostream &ost) const;
    void printWithEpisodes(std::ostream &ost) const;

    friend std::ostream &operator<<(std::ostream &ost, const Podcast &podcast)
    {
        podcast.print(ost);
        return ost;
    }

};

#endif // PODCAST_H
