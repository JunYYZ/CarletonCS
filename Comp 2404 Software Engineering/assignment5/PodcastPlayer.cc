#include "PodcastPlayer.h"

void AudioPlayer::play(const Episode &episode, std::ostream &ost) const
{
    ost << "Playing audio: " << episode.getAudio() << "\n";
}

void VideoPlayer::play(const Episode &episode, std::ostream &ost) const
{
    AudioPlayer::play(episode, ost);

    std::ifstream videoFile(episode.getVideoFile());
    if (!videoFile)
    {
        ost << "Error: Could not open video file.\n";
        return;
    }

    std::string line;
    while (std::getline(videoFile, line))
    {
        ost << line << "\n";
    }
    videoFile.close();
}
