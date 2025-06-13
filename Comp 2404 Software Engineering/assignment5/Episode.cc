#include "Episode.h"
#include <iostream>

Episode::~Episode()
{
}

void Episode::print(std::ostream &ost) const
{
    ost << "Podcast: " << podcastTitle
        << ", Host: " << host
        << ", Title: " << episodeTitle
        << ", Category: " << category;
}
