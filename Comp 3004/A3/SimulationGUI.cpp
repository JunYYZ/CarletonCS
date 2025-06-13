#include "SimulationGUI.h"
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QGroupBox>
#include <QTextEdit>
#include <QDebug>
#include <QTimer>
#include <QRandomGenerator>

SimulationGUI::SimulationGUI(QWidget* parent)
    : QMainWindow(parent),
      timer(new QTimer(this)),
      consoleLog(nullptr),
      elevatorStatusList(nullptr),
      timeLabel(nullptr),
      floorsLabel(nullptr),
      elevsLabel(nullptr),
      passengerLabel(nullptr),
      startButton(nullptr),
      pauseButton(nullptr),
      resumeButton(nullptr),
      stopButton(nullptr),
      fireButton(nullptr),
      doorStuckButton(nullptr),
      running(false),
      simTime(0),
      totalFloors(5),
      totalElevators(2),
      e1(nullptr),
      e2(nullptr)
{
    setupUI();

    connect(timer, &QTimer::timeout, this, &SimulationGUI::onTimeTick);

    // create Elevator objects
    e1 = new Elevator(1, totalFloors, this);
    e2 = new Elevator(2, totalFloors, this);

    // connect elevator logs
    connect(e1, &Elevator::logMessage, this, &SimulationGUI::logMessage);
    connect(e2, &Elevator::logMessage, this, &SimulationGUI::logMessage);

    // generate random passengers
    generatePassengers();
    displayPassengers();

    updateElevatorStatus();
}

SimulationGUI::~SimulationGUI()
{
    delete e1;
    delete e2;
}

void SimulationGUI::setupUI()
{
    QWidget* central = new QWidget(this);
    setCentralWidget(central);

    QHBoxLayout* mainLayout = new QHBoxLayout(central);

    QVBoxLayout* leftColumn = new QVBoxLayout;

    // setup panel
    QGroupBox* setupPanel = new QGroupBox("Simulation Setup Panel", central);
    QVBoxLayout* setupLayout = new QVBoxLayout(setupPanel);
    floorsLabel = new QLabel(QString("- Number of Floors: [%1]").arg(totalFloors), setupPanel);
    elevsLabel  = new QLabel(QString("- Number of Elevators: [%1]").arg(totalElevators), setupPanel);
    passengerLabel = new QLabel("", setupPanel);

    setupLayout->addWidget(floorsLabel);
    setupLayout->addWidget(elevsLabel);
    setupLayout->addWidget(passengerLabel);
    setupPanel->setLayout(setupLayout);

    timeLabel = new QLabel("Time: 0", this);

    // controls
    QGroupBox* controlsGroup = new QGroupBox("Simulation Controls", this);
    QHBoxLayout* controlsLayout = new QHBoxLayout(controlsGroup);

    startButton     = new QPushButton("Start", controlsGroup);
    pauseButton     = new QPushButton("Pause", controlsGroup);
    resumeButton    = new QPushButton("Resume", controlsGroup);
    stopButton      = new QPushButton("Stop", controlsGroup);
    fireButton      = new QPushButton("Fire Alarm", controlsGroup);
    doorStuckButton = new QPushButton("Door Stuck", controlsGroup);

    controlsLayout->addWidget(startButton);
    controlsLayout->addWidget(pauseButton);
    controlsLayout->addWidget(resumeButton);
    controlsLayout->addWidget(stopButton);
    controlsLayout->addWidget(fireButton);
    controlsLayout->addWidget(doorStuckButton);
    controlsGroup->setLayout(controlsLayout);

    QGroupBox* consoleGroup = new QGroupBox("Console Log", this);
    QVBoxLayout* consoleLayout = new QVBoxLayout(consoleGroup);
    consoleLog = new QPlainTextEdit(consoleGroup);
    consoleLog->setReadOnly(true);
    consoleLayout->addWidget(consoleLog);
    consoleGroup->setLayout(consoleLayout);

    leftColumn->addWidget(setupPanel);
    leftColumn->addWidget(timeLabel);
    leftColumn->addWidget(controlsGroup);
    leftColumn->addWidget(consoleGroup);
    leftColumn->addStretch();

    // Right column => elevator status
    QVBoxLayout* rightColumn = new QVBoxLayout;
    QLabel* elevStatusLabel = new QLabel("Elevator Status", this);
    elevatorStatusList = new QListWidget(this);

    rightColumn->addWidget(elevStatusLabel);
    rightColumn->addWidget(elevatorStatusList);
    rightColumn->addStretch();

    QWidget* leftWidget = new QWidget(central);
    leftWidget->setLayout(leftColumn);

    QWidget* rightWidget = new QWidget(central);
    rightWidget->setLayout(rightColumn);

    mainLayout->addWidget(leftWidget, 3);
    mainLayout->addWidget(rightWidget, 2);

    central->setLayout(mainLayout);

    setWindowTitle("Elevator Simulation");
    resize(900, 600);

    // Connect buttons
    connect(startButton,  &QPushButton::clicked, this, &SimulationGUI::onStartClicked);
    connect(pauseButton,  &QPushButton::clicked, this, &SimulationGUI::onPauseClicked);
    connect(resumeButton, &QPushButton::clicked, this, &SimulationGUI::onResumeClicked);
    connect(stopButton,   &QPushButton::clicked, this, &SimulationGUI::onStopClicked);
    connect(fireButton,   &QPushButton::clicked, this, &SimulationGUI::onFireClicked);
    connect(doorStuckButton, &QPushButton::clicked, this, &SimulationGUI::onDoorStuckClicked);
}

// generate 3 random passengers
void SimulationGUI::generatePassengers()
{
    passengers.clear();
    for(int i=0; i<3; i++){
        Passenger p;
        p.id = i+1;  // Passenger IDs: 1..3
        p.startFloor = QRandomGenerator::global()->bounded(1, totalFloors+1);
        do {
            p.destFloor = QRandomGenerator::global()->bounded(1, totalFloors+1);
        } while(p.destFloor == p.startFloor);

        passengers.push_back(p);
    }
}

void SimulationGUI::displayPassengers()
{
    QString text;
    text += QString("- Passengers: [%1]\n").arg(passengers.size());
    for(const auto& p : passengers){
        text += QString("Passenger%1: Start:%2 => Dest:%3\n")
                .arg(p.id).arg(p.startFloor).arg(p.destFloor);
    }
    passengerLabel->setText(text);
}

// scheduling logic: 
// e1 => handle all passengers going UP => in ascending order of start floor
// e2 => handle all passengers going DOWN => in descending order of start floor
void SimulationGUI::schedulePassengers()
{
    // clear existing requests
    e1->reset(totalFloors);
    e2->reset(totalFloors);

    // collect up passengers
    QVector<Passenger> upPassengers;
    // collect down passengers
    QVector<Passenger> downPassengers;

    for(const auto& p : passengers) {
        if(p.startFloor < p.destFloor) {
            upPassengers.push_back(p);
        } else {
            downPassengers.push_back(p);
        }
    }
    // sort ascending by starting floor
    std::sort(upPassengers.begin(), upPassengers.end(), 
              [](const Passenger& a, const Passenger& b){
                  return a.startFloor < b.startFloor;
              });
    // sort descending for down passengers
    std::sort(downPassengers.begin(), downPassengers.end(),
              [](const Passenger& a, const Passenger& b){
                  return a.startFloor > b.startFloor;
              });

    // add requests to e1 for the up passengers
    for(const auto& p : upPassengers) {
        e1->addFloorRequest(p.startFloor);
        e1->addFloorRequest(p.destFloor);
    }

    // add requests to e2 for down passengers
    for(const auto& p : downPassengers) {
        e2->addFloorRequest(p.startFloor);
        e2->addFloorRequest(p.destFloor);
    }
}

// Timer
void SimulationGUI::onTimeTick()
{
    if(!running) return;
    simTime++;
    timeLabel->setText(QString("Time: %1").arg(simTime));

    e1->onTimeStep();
    e2->onTimeStep();

    updateElevatorStatus();
}

// Button handlers
void SimulationGUI::onStartClicked()
{
    running = true;
    simTime = 0;
    timeLabel->setText("Time: 0");
    timer->start(1000);
    logMessage("Start => generating passenger requests & scheduling.");

    schedulePassengers();
    updateElevatorStatus();
}

void SimulationGUI::onPauseClicked()
{
    running = false;
    logMessage("Simulation paused.");
}

void SimulationGUI::onResumeClicked()
{
    running = true;
    logMessage("Simulation resumed.");
}

void SimulationGUI::onStopClicked()
{
    running = false;
    simTime = 0;
    timeLabel->setText("Time: 0");
    timer->stop();
    logMessage("Stop => reset. Regenerating passengers & clearing elevator states.");

    e1->reset(totalFloors);
    e2->reset(totalFloors);

    generatePassengers();
    displayPassengers();
    updateElevatorStatus();
}

void SimulationGUI::onFireClicked()
{
    logMessage("Fire alarm => elevator(s) to floor 1. indefinite EMERGENCY.");
    e1->triggerFire();
    e2->triggerFire();
    updateElevatorStatus();
}

void SimulationGUI::onDoorStuckClicked()
{
    logMessage("Door stuck => if elevator door is closing, re-open.");
    e1->triggerDoorObstacle();
    e2->triggerDoorObstacle();
    updateElevatorStatus();
}

// logging
void SimulationGUI::logMessage(const QString &msg)
{
    consoleLog->appendPlainText(QString("[t=%1] %2").arg(simTime).arg(msg));
}

// elevator status
void SimulationGUI::updateElevatorStatus()
{
    elevatorStatusList->clear();
    elevatorStatusList->addItem(e1->describe());
    elevatorStatusList->addItem(e2->describe());
}
