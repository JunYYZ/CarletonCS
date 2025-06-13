#ifndef TESTCONTROL_H
#define TESTCONTROL_H

#include <iostream>
#include <string>
#include <random>
#include <unordered_set>
#include <sstream>
#include "Podify.h"
#include "View.h"
#include "Tester.h"
#include "PodcastFactory.h"

using namespace std;

class TestControl {
		
	public:
		void launch();
	
	private:
		void initPodcasts(Podify& podify, vector<string>& podcasts, vector<string>& hosts);
		void initWithEpisodes(Podify& podify, 
						vector<string>& podcasts, 
						vector<string>& hosts, 
						vector<string>& epTitles);
		// vector<string> menu ={
		// 	"Test add and print Podcasts",
		// 	"Test add and print Episodes",
		// 	"Test get Episodes by host",
		// 	"Test get Episodes by category",
		// 	"Test get Episodes by host or category",
		// 	"Test print current episode list",
		// 	"Test play current episode list",
		// 	"Test all and get mark"
		// };
		int testAddPodcasts();
		int testAddEpisodes();
		int testGetEpisodesByHost();
		int testGetEpisodesByCategory();
		int testGetEpisodesByHostAndCategory();
		// int testPrintCurrentEpisodeList();
		int testPlayCurrentEpisodeList();
		int testAllAndMark();

		

		View view;
		Tester tester;
		PodcastFactory pf;	

		
	
};
#endif