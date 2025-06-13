
#include "PodcastFactory.h"

#include <fstream>
#include <limits>

Episode *PodcastFactory::createEpisode(const string &pod, const string &host, const string &epTitle)
{
    cout << "Attempting to load episode " << epTitle << endl;
    ifstream podFile;
    podFile.open("media/media2.txt");
    string temp;
    string episodeTitle, content, category, videoFile;

    while (true)
    {
        if (!getline(podFile, episodeTitle))
            break;
        if (epTitle == episodeTitle)
        {
            // found the correct Episode
            getline(podFile, category);
            getline(podFile, content);
            getline(podFile, videoFile);
            return new Episode(pod, host, episodeTitle, category, content, "videos/" + videoFile);
        }
        else
        {
            // We are not interested in this record, so we skip it
            // this is slightly more efficient than reading it into a
            // string if we don't have to
            podFile.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
            podFile.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
            podFile.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }
    }
    cout << "Episode " << epTitle << " not found." << endl;

    return nullptr;
}

Podcast *PodcastFactory::createPodcast(const std::string &title, const std::string &host)
{
    return new Podcast(title, host);
}

Search *PodcastFactory::hostSearch(const std::string &host)
{
    return new H_Search(host);
}

Search *PodcastFactory::categorySearch(const std::string &category)
{
    return new C_Search(category);
}

Search *PodcastFactory::hostCatSearch(const std::string &host, const std::string &category)
{
    return new HorC_Search(host, category);
}