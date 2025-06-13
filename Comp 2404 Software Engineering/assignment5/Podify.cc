#include "Podify.h"
#include <iostream>

Podify::~Podify()
{
    for (int i = 0; i < podcasts.getSize(); ++i)
    {
        delete podcasts[i];
    }
}

void Podify::addPodcast(Podcast *podcast)
{
    podcasts += podcast;
}

void Podify::addEpisode(Episode *episode, const std::string &podcastTitle)
{
    for (int i = 0; i < podcasts.getSize(); ++i)
    {
        if (podcasts[i]->equals(podcastTitle))
        {
            podcasts[i]->add(episode);
            return;
        }
    }
    std::cerr << "Podcast not found: " << podcastTitle << std::endl;
    delete episode;
}

const Array<Podcast *> &Podify::getPodcasts() const
{
    return podcasts;
}

Podcast *Podify::getPodcast(int index) const
{
    if (index < 0 || index >= podcasts.getSize())
    {
        std::cerr << "Index out of bounds: " << index << std::endl;
        exit(1);
    }
    return podcasts[index];
}

Podcast *Podify::getPodcast(const std::string &title) const
{
    for (int i = 0; i < podcasts.getSize(); ++i)
    {
        if (podcasts[i]->equals(title))
        {
            return podcasts[i];
        }
    }
    std::cerr << "Podcast not found: " << title << std::endl;
    exit(1);
}

void Podify::getEpisodes(const Search &criteria, Array<Episode *> &results) const
{
    for (int i = 0; i < podcasts.getSize(); ++i)
    {
        Podcast *podcast = podcasts[i];
        for (int j = 0; j < podcast->getSize(); ++j)
        {
            Episode *episode = podcast->get(j);
            if (criteria.matches(episode))
            {
                results += episode;
            }
        }
    }
}
