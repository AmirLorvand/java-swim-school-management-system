package swimschool;

import java.util.ArrayList;
import java.util.List;

// Class to represent a student in a swimming school
class SwimStudent {
    
    // Attributes to store the student's name, level, and qualifications
    String name;
    String level;
    List<Qualification> qualifications;

    // Constructor to create a new SwimStudent with a name and level
    public SwimStudent(String name, String level) {
        this.name = name;
        this.level = level;
        this.qualifications = new ArrayList<>();
    }

    // Method to add a qualification to the student's list of qualifications
    void addQualification(Qualification qualification) {
        qualifications.add(qualification);
    }

    // Getter method to return the student's name
    public String getName() {
        return name;
    }

    // Getter method to return the student's level
    public String getLevel() {
        return level;
    }

    // Getter method to return the student's list of qualifications
    public List<Qualification> getQualifications() {
        return qualifications;
    }

    // Setter method to update the student's level
    public void setLevel(String newLevel) {
        level = newLevel;
    }
}
