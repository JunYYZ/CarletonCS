#include "Episode.h"

// Constructor
Episode::Episode(const std::string &podcastTitle, const std::string &host,
                 const std::string &episodeTitle, const std::string &category,
                 const std::string &audio, const std::string &videoFile)
    : podcastTitle(podcastTitle), host(host), episodeTitle(episodeTitle),
      category(category), audio(audio), videoFile(videoFile) {}

// Getters
const std::string &Episode::getPodcastTitle() const
{
    return podcastTitle;
}

const std::string &Episode::getHost() const
{
    return host;
}

const std::string &Episode::getEpisodeTitle() const
{
    return episodeTitle;
}

const std::string &Episode::getCategory() const
{
    return category;
}

const std::string &Episode::getAudio() const
{
    return audio;
}

const std::string &Episode::getVideoFile() const
{
    return videoFile;
}

// Print function
void Episode::print(std::ostream &ost) const
{
    ost << "Podcast Title: " << podcastTitle << "\n"
        << "Host: " << host << "\n"
        << "Episode Title: " << episodeTitle << "\n"
        << "Category: " << category << "\n";
}

// Overloaded << operator
std::ostream &operator<<(std::ostream &ost, const Episode &episode)
{
    episode.print(ost);
    return ost;
}
