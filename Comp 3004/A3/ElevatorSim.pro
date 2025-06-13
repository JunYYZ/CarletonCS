TEMPLATE = app
TARGET = my_elevator_sim

QT += widgets
CONFIG += c++17

SOURCES += \
    main.cpp \
    SimulationGUI.cpp \
    Elevator.cpp \
    Building.cpp \
    Floors.cpp \
    Passengers.cpp

HEADERS += \
    SimulationGUI.h \
    Elevator.h \
    Building.h \
    Floors.h \
    Passengers.h
