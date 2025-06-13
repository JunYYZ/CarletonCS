#ifndef EPISODE_H
#define EPISODE_H

#include <string>

class Episode
{
    std::string podcastTitle, host, episodeTitle, category, audio, videoFile;

public:
    Episode(const std::string &podcastTitle, const std::string &host,
            const std::string &episodeTitle, const std::string &category,
            const std::string &audio, const std::string &videoFile)
        : podcastTitle(podcastTitle), host(host), episodeTitle(episodeTitle),
          category(category), audio(audio), videoFile(videoFile) {}

    ~Episode();

    std::string getPodcastTitle() const { return podcastTitle; }
    std::string getHost() const { return host; }
    std::string getEpisodeTitle() const { return episodeTitle; }
    std::string getCategory() const { return category; }
    std::string getAudio() const { return audio; }
    std::string getVideoFile() const { return videoFile; }

    void print(std::ostream &ost) const;
    friend std::ostream &operator<<(std::ostream &ost, const Episode &episode)
    {
        episode.print(ost);
        return ost;
    }
};

#endif // EPISODE_H
