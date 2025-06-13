#ifndef PODCASTPLAYER_H
#define PODCASTPLAYER_H

#include "Episode.h"
#include <fstream>
#include <iostream>

class PodcastPlayer {
public:
    virtual void play(const Episode& episode, std::ostream& ost) const = 0;
    virtual ~PodcastPlayer() = default;
};

class AudioPlayer : public PodcastPlayer {
public:
    void play(const Episode& episode, std::ostream& ost) const override;
};

class VideoPlayer : public AudioPlayer {
public:
    void play(const Episode& episode, std::ostream& ost) const override;
};

#endif // PODCASTPLAYER_H
