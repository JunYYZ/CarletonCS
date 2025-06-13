#include "Podcast.h"
#include <iostream>

Podcast::~Podcast() {
    for (int i = 0; i < episodes.getSize(); ++i) {
        delete episodes[i];
    }
}

bool Podcast::equals(const std::string &title) const
{
    return this->title == title;
}

Episode *Podcast::get(int index) const
{
    return episodes[index];
}

void Podcast::print(std::ostream &ost) const
{
    ost << "Podcast: " << title << " by " << host;
}

void Podcast::printWithEpisodes(std::ostream &ost) const
{
    print(ost);
    ost << "\nEpisodes:\n";
    for (int i = 0; i < episodes.getSize(); ++i)
    {
        ost << *episodes[i] << "\n";
    }
}
