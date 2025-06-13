
#include "View.h"
#include <limits>
#include <vector>

View::View() : player(new AudioPlayer()) {}

View::~View()
{
  delete player;
}

void View::menu(vector<string> &menu, int &choice)
{
  cout << endl;
  cout << "Please make a selection:" << endl
       << endl;
  for (int i = 0; i < menu.size(); ++i)
  {
    cout << "  (" << i + 1 << ") " << menu[i] << endl;
  }
  cout << "  (0) Exit" << endl
       << endl;

  cout << "Enter your selection: ";
  cin >> choice;
  if (choice == 0)
    return;

  while (choice < 0 || choice > menu.size())
  {
    cout << "Enter your selection: ";
    cin >> choice;
  }
}

void View::printAllPodcasts(const Array<Podcast *> &podcasts)
{
  for (int i = 0; i < podcasts.getSize(); ++i)
  {
    cout << i + 1 << ": " << *podcasts[i] << endl;
  }
}

void View::printPodcast(const Podcast *podcast)
{
  podcast->printWithEpisodes(cout);
}

void View::podcastMenu(const Array<Podcast *> &podcasts, int &choice)
{
  int numOptions = podcasts.getSize();

  printAllPodcasts(podcasts);

  cout << endl;
  cout << "Choose an podcast" << endl;
  cout << "or select (0) to exit" << endl;

  cout << "Enter your selection: ";
  cin >> choice;
  if (choice == 0)
    return;

  while (choice < 0 || choice > numOptions)
  {
    cout << "Enter your selection: ";
    cin >> choice;
  }
  --choice;
}

void View::promptHost(string &host)
{
  cout << "Enter host: ";
  char str[80];
  cin.clear();
  // cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
  cin.getline(str, 80);
  host = str;
  // cin >> host;
}

void View::promptCategory(string &category)
{
  cout << "Enter category: ";
  char str[80];
  cin.clear();
  // cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
  // cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
  cin.getline(str, 80);
  category = str;
  // cin >> category;
}

void View::printPlaylist(Array<Episode *> &episode)
{
  for (int i = 0; i < episode.getSize(); ++i)
  {
    cout << *episode[i] << endl;
  }
}

void View::playPlaylist(Array<Episode *> &episode)
{
  cout << "Playing playlist size " << episode.getSize() << endl;
  ;
  for (int i = 0; i < episode.getSize(); ++i)
  {
    cout << endl
         << *episode[i] << endl;
    player->play(*episode[i], cout);
    cout << endl;
  }
}

void View::promptVideo()
{
  int numOptions = 2;
  int choice = -1;

  cout << endl;
  cout << "  (0) Exit (no change)" << endl;
  cout << "  (1) Audio player only" << endl;
  cout << "  (2) Audio and Video player" << endl
       << endl;

  cout << "Enter your selection: ";

  while (choice < 0 || choice > numOptions)
  {
    cout << "Enter your selection: ";
    cin >> choice;
  }

  toggleVideo(choice == 2);
}

void View::toggleVideo(bool video)
{
    delete player;
    if (video)
    {
        player = new VideoPlayer();
        std::cout << "Switched to video player.\n";
    }
    else
    {
        player = new AudioPlayer();
        std::cout << "Switched to audio player.\n";
    }
}
