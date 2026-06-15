# Java Swim School Management System

This project is a console-based **Swim School Management System** developed in **Java**. It was originally created as a university coursework project and demonstrates object-oriented programming, class design, collections, menu-driven interaction, scheduling logic, and basic student administration.

The system allows users to manage swim students, swim classes, instructors, qualifications, and waiting lists through a terminal-based menu.

---

## Project Timeline

* **Originally completed:** 2024
* **Refactored and published on GitHub:** 2026
* **Context:** Java programming coursework project

This repository has been cleaned and documented for portfolio purposes.

---

## Project Status

This is a coursework-style Java console application. It is not a production system, but it demonstrates core Java programming skills, including object-oriented design, student management, class scheduling, and qualification handling.

---

## Features

* View swim student information
* View swim lesson details
* View instructor schedules
* Add new swim students
* Manage students by ability level
* Award swim qualifications
* Move students from a waiting list
* Store students, classes, instructors, and qualifications using Java objects
* Menu-driven console interaction

---

## Technologies Used

* Java
* Object-Oriented Programming
* Collections and lists
* Console input/output
* Menu-driven application design
* Makefile

---

## Project Structure

```text
.
├── src/
│   └── swimschool/
│       ├── Main.java
│       ├── SwimSchool.java
│       ├── SwimStudent.java
│       ├── SwimClass.java
│       ├── SwimInstructor.java
│       ├── Qualification.java
│       ├── DistanceSwim.java
│       └── PersonalSurvival.java
├── Makefile
├── .gitignore
└── README.md
```

---

## Main Classes

### `Main`

Entry point of the application. It handles the main menu, user interaction, and program flow.

### `SwimSchool`

Stores and manages the main swim school data, including students, instructors, classes, and waiting lists.

### `SwimStudent`

Represents a swim student and stores information such as name, swimming level, and qualifications.

### `SwimClass`

Represents a swimming lesson, including the day, time, level, instructor, and enrolled students.

### `SwimInstructor`

Represents an instructor assigned to swimming lessons.

### `Qualification`

Abstract base class for swim qualifications.

### `DistanceSwim`

Represents distance-based swimming qualifications.

### `PersonalSurvival`

Represents personal survival qualifications such as Bronze, Silver, and Gold.

---

## How to Compile

Use the Makefile:

```bash
make
```

This compiles the Java files into the `out/` directory.

---

## How to Run

```bash
make run
```

Or run manually after compilation:

```bash
java -cp out swimschool.Main
```

---

## How to Clean Build Files

```bash
make clean
```

This removes the generated `out/` directory.

---

## Example Functionality

The application allows users to perform tasks such as:

```text
View swim students
View swim lessons
View instructor schedules
Add a new swim student
Award a qualification
Move students from the waiting list
Exit the system
```

---

## What I Learned

Through this coursework project, I practised:

* Designing Java applications using multiple classes
* Applying object-oriented programming principles
* Using inheritance through qualification classes
* Managing lists of students, classes, and instructors
* Building a menu-driven console application
* Handling user input with `Scanner`
* Modelling a real-world administration system in code
* Preparing a Java coursework project for GitHub presentation

---

## Future Improvements

Possible improvements include:

* Improving input validation for invalid menu entries
* Adding unit tests
* Saving student and class data to files
* Loading data from files when the program starts
* Refactoring the system into clearer service/controller classes
* Adding a graphical user interface
* Adding search and filter features for students and classes
* Adding persistent storage using a database

---

## Author

**Amir Lorvand**
