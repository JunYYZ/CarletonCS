#ifndef SIMULATIONGUI_H
#define SIMULATIONGUI_H

#include <QMainWindow>
#include <QTimer>
#include <QPlainTextEdit>
#include <QLabel>
#include <QListWidget>
#include <QPushButton>
#include <QVector>
#include "Elevator.h"

struct Passenger {
    int id;
    int startFloor;
    int destFloor;
};

class SimulationGUI : public QMainWindow
{
    Q_OBJECT
public:
    explicit SimulationGUI(QWidget* parent=nullptr);
    ~SimulationGUI();

private slots:
    void onStartClicked();
    void onPauseClicked();
    void onResumeClicked();
    void onStopClicked();
    void onFireClicked();
    void onDoorStuckClicked();
    void onTimeTick();

    // console log
    void logMessage(const QString &msg);

private:
    void setupUI();
    void updateElevatorStatus();

    // passenger logic
    void generatePassengers();  // random start/dest for 3 passengers
    void displayPassengers();   // output to gui panel
    void schedulePassengers();  // pick which elevator is assigned and etc.

    QTimer* timer;
    QPlainTextEdit* consoleLog;
    QListWidget* elevatorStatusList;
    QLabel* timeLabel;

    // UI in setup panel
    QLabel* floorsLabel;
    QLabel* elevsLabel;
    QLabel* passengerLabel;

    // buttons
    QPushButton* startButton;
    QPushButton* pauseButton;
    QPushButton* resumeButton;
    QPushButton* stopButton;
    QPushButton* fireButton;
    QPushButton* doorStuckButton;

    bool running;
    int simTime;

    // gard-coded floors/elev, or could let user pick or randomize (randomize only the floors, not the elevators)
    int totalFloors;
    int totalElevators;

    QVector<Passenger> passengers;

    Elevator* e1;
    Elevator* e2;
};

#endif // SIMULATIONGUI_H
