#include <QApplication>
#include "SimulationGUI.h"

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);

    SimulationGUI gui;
    gui.show();

    return app.exec();
}
