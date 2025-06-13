#include "Algorithms.h"

// Constructor
Algorithms::Algorithms() {}

// Destructor
Algorithms::~Algorithms() {}

// Add a new Student object to the data structure
void Algorithms::addStudent(const string& number, const string& name, float gpa) {
    students.emplace_back(number, name, gpa);
}

// Copy or add all passing Students to v
void Algorithms::getPassingStudents(vector<Student>& v) {
    for (const auto& student : students) {
        if (student.gpa >= 6.0) {
            v.push_back(student);
        }
    }
}

// Copy or add all Students to v in sorted order by student number
void Algorithms::sortByNumber(vector<Student>& v) {
    v = students; // Copy the current list
    sort(v.begin(), v.end(), [](const Student& a, const Student& b) {
        return a.number < b.number;
    });
}

// Copy or add all Students to v in sorted order by name
void Algorithms::sortByName(vector<Student>& v) {
    v = students; // Copy the current list
    sort(v.begin(), v.end(), [](const Student& a, const Student& b) {
        return a.name < b.name;
    });
}

// Assign the Student with the highest GPA to stuIt
bool Algorithms::highestGpa(vector<Student>::iterator& stuIt) {
    if (students.empty()) {
        return false;
    }
    stuIt = max_element(students.begin(), students.end(), [](const Student& a, const Student& b) {
        return a.gpa < b.gpa;
    });
    return true;
}

// Find a Student by name and assign to stuIt
bool Algorithms::findStudent(const string& name, vector<Student>::iterator& stuIt) {
    auto it = find_if(students.begin(), students.end(), [&name](const Student& student) {
        return student.name == name;
    });
    if (it != students.end()) {
        stuIt = it;
        return true;
    }
    return false;
}
