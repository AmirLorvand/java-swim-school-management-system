package swimschool;

import java.util.ArrayList;
import java.util.List;
class SwimClass {
    
    // Attributes
    String day; // Day of the week the class is held
    String time; // Time of the class
    String level; // Level of the class (e.g., beginner, intermediate, advanced)
    SwimInstructor instructor; // Instructor for the class
    List<SwimStudent> students; // List of students enrolled in the class

    // Constructor
    public SwimClass(String day, String time, String level, SwimInstructor instructor) {
        this.day = day;
        this.time = time;
        this.level = level;
        this.instructor = instructor;
        this.students = new ArrayList<>(); // Initialize the list of students
    }

    // Methods
    void addStudent(SwimStudent student) {
        students.add(student); // Add a student to the class
    }

    // Getters
    public String getDay() {
        return day; // Return the day of the class
    }

    public String getTime() {
        return time; // Return the time of the class
    }

    public String getLevel() {
        return level; // Return the level of the class
    }

    public SwimInstructor getInstructor() {
        return instructor; // Return the instructor of the class
    }

    public List<SwimStudent> getStudents() {
        return students; // Return the list of students in the class
    }
}

