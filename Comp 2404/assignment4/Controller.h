
#ifndef CONTROLLER_H
#define CONTROLLER_H

#include <iostream>
#include <string>
#include "RideShare.h"
#include "View.h"

using namespace std;

class Controller
{

public:
	// constructor
	Controller();
	// destructor
	~Controller();

	void launch();

	void displayMap();
	void findRide();

	int testDisplayMap();
	int testFindRide();

private:
	void initRideShare();

	// initializers for testing
	void initRideShare(RideShare &rideshare);
	void initRideShare(RideShare &rideShare,
					   vector<string> driverNames,
					   vector<Size> driverSizes,
					   vector<int> driverRatings,
					   vector<Location> driverLocations,
					   vector<string> customers,
					   vector<int> customerRatings,
					   vector<Location> customerLocations);
	RideShare rideShare;
	View view;
};
#endif