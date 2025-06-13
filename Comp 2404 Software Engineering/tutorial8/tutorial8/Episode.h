#ifndef EPISODE_H
#define EPISODE_H

#include <string>
#include <iostream>

class Episode
{
private:
    std::string podcastTitle;
    std::string host;
    std::string episodeTitle;
    std::string category;
    std::string audio;
    std::string videoFile;

public:
    // con
    Episode(const std::string &podcastTitle, const std::string &host,
            const std::string &episodeTitle, const std::string &category,
            const std::string &audio, const std::string &videoFile);

    // get
    const std::string &getPodcastTitle() const;
    const std::string &getHost() const;
    const std::string &getEpisodeTitle() const;
    const std::string &getCategory() const;
    const std::string &getAudio() const;
    const std::string &getVideoFile() const;

    // print
    void print(std::ostream &ost) const;

    // overload <<
    friend std::ostream &operator<<(std::ostream &ost, const Episode &episode);
};

#endif
