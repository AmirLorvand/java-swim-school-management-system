package swimschool;

import java.util.*;
import java.util.stream.Collectors;

class SwimSchool {
    private final Scanner scanner = new Scanner(System.in); // Scanner for user input
    private final List<SwimStudent> students; // List of students in the school
    private final List<SwimInstructor> instructors; // List of instructors in the school
    private final List<SwimClass> classes; // List of classes offered by the school
    private final List<SwimStudent> waitingList; // List of students on the waiting list

    // Constructor
    public SwimSchool() {
        students = new ArrayList<>(); // Initialize the list of students
        instructors = new ArrayList<>(); // Initialize the list of instructors
        classes = new ArrayList<>(); // Initialize the list of classes
        waitingList = new ArrayList<>(); // Initialize the waiting list
    }

    // Getters
    public List<SwimStudent> getStudents() {
        sortStudentsByName(); // Sort students by name before returning
        return students; // Return the list of students
    }

    public List<SwimInstructor> getInstructors() {
        return instructors; // Return the list of instructors
    }

    public List<SwimClass> getClasses() {
        return classes; // Return the list of classes
    }

    // Methods
    void addStudent(SwimStudent student) {
        students.add(student); // Add a student to the school
    }

    void addInstructor(SwimInstructor instructor) {
        instructors.add(instructor); // Add an instructor to the school
    }

    void addClass(SwimClass swimClass) {
        classes.add(swimClass); // Add a class to the school
    }

    public List<SwimClass> getClassesByLevel(String level) {
        List<SwimClass> filteredClasses = new ArrayList<>(); // List to store classes of the specified level
        for (SwimClass swimClass : classes) {
            if (swimClass.getLevel().equalsIgnoreCase(level)) { // Check if the class level matches the specified level
                filteredClasses.add(swimClass); // Add the class to the filtered list
            }
        }
        return filteredClasses; // Return the filtered list of classes
    }

    // Method to add a student to the waiting list
    void addToWaitingList(SwimStudent student) {
        waitingList.add(student);
    }

    // Method to sort students in the school by their name
    public void sortStudentsByName() {
        students.sort(new Comparator<SwimStudent>() {
            @Override
            public int compare(SwimStudent s1, SwimStudent s2) {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });
    }

    // Method to find the class for a given student
    public SwimClass findClassForStudent(SwimStudent studentSelected) {
        for (SwimClass swimClass : classes) {
            for (SwimStudent student : swimClass.getStudents()) {
                if (student.getName().equalsIgnoreCase(studentSelected.getName()) && student.getLevel().equalsIgnoreCase(studentSelected.getLevel())) {
                    return swimClass;
                }
            }
        }
        return null;
    }

    // Method to check if a student is in the waiting list
    public boolean isStudentInWaitingList(String studentName, String level) {
        for (SwimStudent student : waitingList) {
            if (student.getName().equalsIgnoreCase(studentName) && student.getLevel().equalsIgnoreCase(level)) {
                return true;
            }
        }
        return false;
    }

    // Method to display details of a class based on day, time, and level
    public void displayClassDetails(String day, String time, String level) {
        // Iterate through all classes in the school
        for (SwimClass swimClass : classes) {
            // Check if the class matches the specified day, time, and level
            if (swimClass.getDay().equalsIgnoreCase(day) &&
                    swimClass.getTime().equalsIgnoreCase(time) &&
                    swimClass.getLevel().equalsIgnoreCase(level)) {

                // Print the instructor's name
                System.out.println("Instructor: " + swimClass.getInstructor().getName());
                // Print the names of students in the class
                System.out.println("Students in class:");
                for (SwimStudent student : swimClass.getStudents()) {
                    System.out.println(" - " + student.getName());
                }
                // Calculate the number of available spaces in the class
                int spacesAvailable = 4 - swimClass.getStudents().size();
                // Print the number of available spaces or indicate if the class is full
                if (spacesAvailable > 0) {
                    System.out.println("Spaces available: " + spacesAvailable);
                } else {
                    System.out.println("The class is currently full.");
                }
                // Exit the method after displaying details for the matching class
                return;
            }
        }
        // Print a message if no class is found for the specified criteria
        System.out.println("No class found for the specified day, time, and level.");
    }


    // Method to add a student to a class or the waiting list
    public void addStudentToClassOrWaitingList(String studentName) {
        boolean classFound = false; // Flag to track if a class has been found for the student
        // Print the weekly schedule of classes for the novice level
        System.out.println("Weekly schedule of classes for the novice level:");
        for (SwimClass swimClass : classes) {
            if (swimClass.getLevel().equalsIgnoreCase("Novice")) {
                int spacesAvailable = 4 - swimClass.getStudents().size(); // Calculate available spaces
                String availability = spacesAvailable > 0 ? "Spaces available: " + spacesAvailable : "Class is full";
                // Print the class schedule and availability
                System.out.println(swimClass.getDay() + ", " + swimClass.getTime() + " - " + availability);
            }
        }

        // Prompt the user to enter the class details or 'wait' to add to the waiting list
        System.out.println("Enter the day and time of the class you want to add the student to (e.g., Monday 17:00), or type 'wait' to add to the waiting list:");
        String input = scanner.nextLine(); // Read the user input

        if (!input.equalsIgnoreCase("wait")) { // If the user didn't choose to wait
            String[] inputParts = input.split("\\s+"); // Split the input into day and time
            if (inputParts.length == 2) { // Check if the input format is correct
                String day = inputParts[0];
                String time = inputParts[1];

                for (SwimClass swimClass : classes) { // Iterate through classes to find a match
                    if (swimClass.getLevel().equalsIgnoreCase("Novice") &&
                            swimClass.getDay().equalsIgnoreCase(day) &&
                            swimClass.getTime().equalsIgnoreCase(time) &&
                            swimClass.getStudents().size() < 4) { // Check for availability
                        // Create a new student and add to the class and student list
                        SwimStudent newStudent = new SwimStudent(studentName, "novice");
                        students.add(newStudent);
                        swimClass.addStudent(newStudent);
                        System.out.println("Student " + studentName + " has been added to the class on " + day + " at " + time);
                        classFound = true; // Set the flag to true
                        break; // Exit the loop
                    }
                }
            } else {
                // Print an error message for invalid input format
                System.out.println("Invalid input format. Please enter the day and time in the format 'Day Time' (e.g., Monday 17:00).");
            }

            if (!classFound) { // If no class was found or the class is full
                // Add the student to the waiting list
                System.out.println("No available class found at the specified time or the class is full. The student has been added to the waiting list.");
                waitingList.add(new SwimStudent(studentName, "Novice"));
            }
        } else { // If the user chose to wait
            // Add the student to the waiting list
            waitingList.add(new SwimStudent(studentName, "Novice"));
            System.out.println("Student " + studentName + " has been added to the waiting list.");
        }
    }


    // Method to move a student from the waiting list to a class
    public void moveSwimStudentFromWaitingList() {
        // Sort the waiting list by level in descending order
        List<SwimStudent> waitingListCopy = waitingList.stream()
                .sorted(Comparator.comparing(SwimStudent::getLevel).reversed())
                .collect(Collectors.toList());
        // Print the waiting list
        System.out.println("Waiting list:");

        // Lists to categorize students based on their level and class status
        List<SwimStudent> noviceStudentsInClass = new ArrayList<>();
        List<SwimStudent> noviceStudentsNotInClass = new ArrayList<>();
        List<SwimStudent> otherStudents = new ArrayList<>();

        // Categorize students based on their level and whether they are already in a class
        for (SwimStudent student : waitingListCopy) {
            if (student.getLevel().equalsIgnoreCase("novice")) {
                List<SwimClass> classesNovice = getClassesByLevel("novice");
                boolean studentHasClass = false;
                for (SwimClass swimClass : classesNovice) {
                    if (swimClass.getStudents().contains(student)) {
                        studentHasClass = true;
                        break;
                    }
                }
                if (studentHasClass) {
                    noviceStudentsInClass.add(student);
                } else {
                    noviceStudentsNotInClass.add(student);
                }
            } else {
                otherStudents.add(student);
            }
        }

        // Combine the categorized lists into a single list
        List<SwimStudent> combinedWaitingList = new ArrayList<>();
        combinedWaitingList.addAll(noviceStudentsNotInClass);
        combinedWaitingList.addAll(noviceStudentsInClass);
        combinedWaitingList.addAll(otherStudents);

        // Print the combined waiting list
        int i;
        for (i = 0; i < combinedWaitingList.size(); i++) {
            SwimStudent student = combinedWaitingList.get(i);
            System.out.println((i + 1) + ". " + student.getName() + " - Level: " + student.getLevel());
        }

        // Prompt the user to select a student to move
        System.out.println("Select a student to move (Please enter a number):");
        int selectedIndex;
        SwimStudent selectedStudent;

        // Loop to handle user input for selecting a student
        while (true) {
            try {
                selectedIndex = Integer.parseInt(scanner.nextLine()) - 1; // Read and parse the user input
                if (selectedIndex >= 0 && selectedIndex < combinedWaitingList.size()) { // Check if the index is valid
                    selectedStudent = combinedWaitingList.get(selectedIndex); // Get the selected student
                    break; // Exit the loop if a valid student is selected
                } else {
                    System.out.println("Invalid option. Please enter a number between 1 and " + combinedWaitingList.size() + ":");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number:");
            }
        }

        // Print the weekly schedule of classes for the selected student's level
        System.out.println("Weekly schedule of classes for the " + selectedStudent.getLevel() + " level:");
        for (SwimClass swimClass : classes) {
            if (swimClass.getLevel().equalsIgnoreCase(selectedStudent.getLevel())) {
                int spacesAvailable = 4 - swimClass.getStudents().size(); // Calculate available spaces
                String availability = spacesAvailable > 0 ? "Spaces available: " + spacesAvailable : "Class is full";
                System.out.println(swimClass.getDay() + ", " + swimClass.getTime() + " - " + availability);
            }
        }

        // Prompt the user to enter the day and time of the class to move the student to
        System.out.println("Enter the day and time of the class you want to move the student to (e.g., Monday 17:00):");
        String input;
        String[] inputParts;
        String day, time;
        boolean classFound = false; // Flag to track if a class is found for the student

        // Set of valid days for input validation
        Set<String> validDays = new HashSet<>(Arrays.asList("Monday", "Wednesday", "Friday"));

        // Loop to handle user input for selecting a class
        while (true) {
            input = scanner.nextLine(); // Read the user input
            inputParts = input.split("\\s+"); // Split the input into day and time

            if (inputParts.length == 2) { // Check if the input format is correct
                day = inputParts[0];
                time = inputParts[1];

                if (!validDays.contains(day)) { // Validate the entered day
                    System.out.println("Invalid day. Please enter a valid day of the week (e.g., Monday, Wednesday, etc.).");
                    System.out.println("Enter the day and time of the class you want to move the student to (e.g., Monday 17:00):");
                    continue; // Continue the loop if the day is invalid
                }

                // Iterate through classes to find a match
                for (SwimClass swimClass : classes) {
                    if (swimClass.getLevel().equalsIgnoreCase(selectedStudent.getLevel()) &&
                            swimClass.getDay().equalsIgnoreCase(day) &&
                            swimClass.getTime().equalsIgnoreCase(time) &&
                            swimClass.getStudents().size() < 4) { // Check for availability

                        swimClass.addStudent(new SwimStudent(selectedStudent.getName(), selectedStudent.getLevel())); // Add the student to the class
                        waitingList.remove(selectedStudent); // Remove the student from the waiting list
                        combinedWaitingList.remove(selectedStudent); // Remove the student from the combined waiting list
                        System.out.println("Student " + selectedStudent.getName() + " has been moved to the class on " + day + " at " + time);
                        classFound = true; // Set the flag to true
                        break; // Exit the loop if the student is added to a class
                    }
                }

                if (!classFound) { // If no class is found or the class is full
                    System.out.println("No available class found at the specified time or the class is full. The student remains on the waiting list.");
                } else {
                    // Remove the student from any other class they might be in
                    for (SwimClass c : classes) {
                        if (c.getStudents().contains(selectedStudent)) {
                            c.getStudents().remove(selectedStudent);
                            break;
                        }
                    }
                }
                break; // Exit the loop if input is valid
            } else {
                System.out.println("Invalid input format. Please enter the day and time in the format 'Day Time' (e.g., Monday 17:00).");
                System.out.println("Enter the day and time of the class you want to move the student to (e.g., Monday 17:00):");
            }
        }
    }

}