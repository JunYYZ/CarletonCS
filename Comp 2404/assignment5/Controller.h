#ifndef CONTROLLER_H
#define CONTROLLER_H

#include <iostream>
#include <string>
#include "Podify.h"
#include "View.h"
#include "PodcastFactory.h"

using namespace std;

class Controller {
		
	public:
				
		void launch();
	
	private:
		void initFromFile();
		void showAllPodcasts();
		void showSinglePodcast();
		void getEpisodeByH(); 
		void getEpisodeByC(); 
		void getEpisodeByHandC(); 
		void printCurrentEpisode(); 
		void playCurrentEpisode(); 
		void toggleVideo(); 
		View view;
		Podify av;
		PodcastFactory pf;
		Array<Episode*> playlist;

	
};
#endif