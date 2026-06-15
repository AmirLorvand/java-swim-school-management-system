package swimschool;

public class DistanceSwim extends Qualification {
    
    // Attributes
    private final int distance; // Distance achieved in the swim
    private final SwimInstructor instructor; // Instructor who supervised the swim

    // Constructor
    public DistanceSwim(int distance, SwimInstructor instructor) {
        this.distance = distance; // Set the distance achieved
        this.instructor = instructor; // Set the instructor who supervised the swim
    }

    // Getters
    public int getDistance() {
        return distance; // Return the distance achieved
    }

    public SwimInstructor getInstructor() {
        return instructor; // Return the instructor who supervised the swim
    }
}
