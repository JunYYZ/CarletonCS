General Files:
a3.pdf                       - main pdf with all deliverables and text answers like use case forms and images of diagrams
                            Does not include .h and .cpp files
                            if image is not high enough quality look at individual images
readme.txt                  - this readme
A3 Tracebility Matrix.xlsx  - excel file for the Tracebility matrix since it looks so disgusting when its in docs.
A3 Video.mov                - video for showing the thing, in case the video link doesn't work

Header files:
Building.cpp
Building.h
Elevator.cpp
Elevator.h
ElevatorSim.pro             - qmake file
Floors.cpp
Floors.h
main.cpp
Passengers.cpp
Passengers.h
SimulationGUI.cpp
SimulationGUI.h

Image Files:
Gui.png
machine state elevator.png 
machine state sim con.png 
Sequence Diagram door stuck.png
Sequence Diagram fire alarm.png
Sequence Diagram min op.png
Sequence Diagram operation.png
State machine elevator.png
state machine Simluation Controller.png
UML class diagram.png
Use Case Diagram.png



To compile after unzipping the files to vm, do:
qmake
make

or if that doesnt work, do:
qmake ElevatorSim.pro
make