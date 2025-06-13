#include "Repo.h"
#include <iostream>

Repo::Repo(const std::string& title, const std::string& owner) 
    : title(title), owner(owner) {
    fileList = new FileList();
}

Repo::~Repo() {
    delete fileList;
}

bool Repo::lessThan(const Repo& other) {
    return title < other.title;
}

bool Repo::addFile(const std::string& name, const std::string& content, Date& date) {
    File* newFile = new File(name, content, date);
    return fileList->add(newFile);
}

bool Repo::addFile(const std::string& name, const std::string& content, const Date& date) {
    File* newFile = new File(name, content, date);
    return fileList->add(newFile);
}

bool Repo::removeFile(int index) {
    File* removedFile = fileList->remove(index);
    if (removedFile != nullptr) {
        delete removedFile;  // free memory of removed file
        return true;
    }
    return false;
}

int Repo::getNumFiles() const {
    return fileList->size();
}

void Repo::print() const {
    std::cout << "Repo Title: " << title << "\n"
              << "Owner: " << owner << "\n";
}

void Repo::printFiles() const {
    print();
    for (int i = 0; i < fileList->size(); ++i) {
        std::cout << i << ") ";
        fileList->get(i)->print();
    }
}

void Repo::printContents(int index) const {
    File* file = fileList->get(index);
    if (file != nullptr) {
        file->printContents();  // print
    } else {
        std::cout << "Error: Invalid file index" << std::endl;
    }
}

std::string Repo::getTitle() const {
    return title;
}
