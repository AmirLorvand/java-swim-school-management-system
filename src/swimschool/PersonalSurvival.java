package swimschool;

public class PersonalSurvival extends Qualification {
    
    // Attributes
    private final String level; // Level of personal survival qualification (e.g., basic, intermediate, advanced)
    private final SwimInstructor instructor; // Instructor who awarded the qualification

    // Constructor
    public PersonalSurvival(String level, SwimInstructor instructor) {
        this.level = level;
        this.instructor = instructor;
    }

    // Getters
    public String getLevel() {
        return level; // Return the level of the qualification
    }

    public SwimInstructor getInstructor() {
        return instructor; // Return the instructor who awarded the qualification
    }
}

