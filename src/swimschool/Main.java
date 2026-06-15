package swimschool;

import java.util.*;
import java.util.regex.Pattern;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SwimSchool swimSchool = new SwimSchool();
    private static final String[] days = {"Monday", "Wednesday", "Friday"};
    private static final String[] levels = {"novice", "improver", "advanced"};
    private static final int[] noviceQualifications = {5, 10, 20};
    private static final int[] improverQualifications = {100, 200, 400};
    private static final int[] advancedQualifications = {800, 1500, 3000};
    private static final String[] personalSurvivalQualifications = {"Bronze", "Silver", "Gold"};
    private static final Random random = new Random();
    private static final int START_TIME = 17; // 5 PM
    private static final int END_TIME = 20; // 8 PM

    private static final int MAX_STUDENTS_PER_CLASS = 4;

    public static void main(String[] args) {
        boolean exit = false; // Flag to control the program loop

        // Create dummy data for instructors and schedule
        List<SwimInstructor> instructors = createInstructors();
        Map<String, List<SwimClass>> schedule = createSchedule(instructors);

        // Add instructors to the swim school
        for (SwimInstructor instructor : instructors) {
            swimSchool.addInstructor(instructor);
        }

        // Generate dummy students and add them to the swim school
        List<SwimStudent> students = generateDummyStudents(100);
        schedule.forEach((day, classes) -> {
            classes.forEach(swimSchool::addClass);
        });

        for (SwimStudent student : students) {
            swimSchool.addStudent(student);
        }

        // Shuffle students and randomly assign some of them to classes
        List<SwimStudent> shuffledStudents = new ArrayList<>(students);
        Collections.shuffle(shuffledStudents, new Random());
        List<SwimStudent> randomStudents = shuffledStudents.subList(0, Math.min(20, shuffledStudents.size()));
        for (SwimStudent student : randomStudents) {
            List<SwimClass> filterClass = swimSchool.getClassesByLevel(student.level);
            for (SwimClass Class : filterClass) {
                if (Class.getStudents().size() < MAX_STUDENTS_PER_CLASS) {
                    Class.addStudent(student);
                    break;
                }
            }
        }

        // Shuffle students again and add some of them to the waiting list
        Collections.shuffle(shuffledStudents, new Random());
        List<SwimStudent> randomStudents2 = shuffledStudents.subList(0, Math.min(20, shuffledStudents.size()));
        for (SwimStudent student : randomStudents2) {
            swimSchool.addToWaitingList(student);
        }

        // Program loop for the administration system
        while (!exit) {
            System.out.println("Welcome to the Swim School Administration System");
            System.out.println("1. View swim student information");
            System.out.println("2. View swim lesson details");
            System.out.println("3. View instructor schedule");
            System.out.println("4. Add new swim student");
            System.out.println("5. Award swim qualification");
            System.out.println("6. Move swim student from waiting list");
            System.out.println("7. Exit");
            System.out.print("Please enter an option: ");

            int option = Integer.parseInt(scanner.nextLine()); // Read user input

            // Switch case to handle user options
            switch (option) {
                case 1:
                    viewSwimStudentInformation();
                    break;
                case 2:
                    viewSwimLessonDetails();
                    break;
                case 3:
                    viewInstructorSchedule();
                    break;
                case 4:
                    addNewSwimStudent();
                    break;
                case 5:
                    awardSwimQualification();
                    break;
                case 6:
                    moveSwimStudentFromWaitingList();
                    break;
                case 7:
                    exit = true; // Set the exit flag to true to end the loop
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }

        scanner.close(); // Close the scanner
        System.out.println("Thank you for using the Swim School Administration System, Goodbye"); // Farewell message
    }



    // Method to create a list of swim instructors
    private static List<SwimInstructor> createInstructors() {
        List<SwimInstructor> instructors = new ArrayList<>();
        instructors.add(new SwimInstructor("Amir"));
        instructors.add(new SwimInstructor("John"));
        instructors.add(new SwimInstructor("Lina"));
        return instructors;
    }

    // Method to create a schedule for swim classes based on instructors and times
    private static Map<String, List<SwimClass>> createSchedule(List<SwimInstructor> instructors) {
        Map<String, List<SwimClass>> schedule = new HashMap<>();
        Map<Double, Set<SwimInstructor>> assignedInstructors = new HashMap<>();
        Random random = new Random();

        for (String day : days) {
            List<SwimClass> classes = new ArrayList<>();
            for (double time = START_TIME; time < END_TIME; time += 0.5) {
                assignedInstructors.putIfAbsent(time, new HashSet<>());
                String level = levels[random.nextInt(levels.length)];
                SwimInstructor instructor = null;
                for (SwimInstructor potentialInstructor : instructors) {
                    if (!assignedInstructors.get(time).contains(potentialInstructor)) {
                        instructor = potentialInstructor;
                        assignedInstructors.get(time).add(instructor);
                        break;
                    }
                }
                if (instructor == null) {
                    continue;
                }
                String formattedTime = String.format("%02d:%02d", (int) time, (int) ((time - (int) time) * 60));
                SwimClass swimClass = new SwimClass(day, formattedTime, level, instructor);
                classes.add(swimClass);
            }
            schedule.put(day, classes);
        }
        return schedule;
    }

    // Method to generate a list of dummy swim students
    private static List<SwimStudent> generateDummyStudents(int numberOfStudents) {
        List<SwimStudent> students = new ArrayList<>();

        for (int i = 0; i < numberOfStudents; i++) {
            String level = levels[random.nextInt(levels.length)];
            SwimStudent student = new SwimStudent("Student" + i, level);
            assignQualifications(student, level);
            students.add(student);
        }
        return students;
    }

    // Method to assign qualifications to a swim student based on their level
    private static void assignQualifications(SwimStudent student, String level) {
        List<SwimInstructor> listInstructor = swimSchool.getInstructors();
        switch (level) {
            case "novice":
                student.addQualification(new DistanceSwim(noviceQualifications[random.nextInt(noviceQualifications.length)], listInstructor.get(random.nextInt(listInstructor.size()))));
                break;
            case "improver":
                student.addQualification(new DistanceSwim(improverQualifications[random.nextInt(improverQualifications.length)], listInstructor.get(random.nextInt(listInstructor.size()))));
                break;
            case "advanced":
                student.addQualification(new DistanceSwim(advancedQualifications[random.nextInt(advancedQualifications.length)], listInstructor.get(random.nextInt(listInstructor.size()))));
                student.addQualification(new PersonalSurvival(personalSurvivalQualifications[random.nextInt(personalSurvivalQualifications.length)], listInstructor.get(random.nextInt(listInstructor.size()))));
                break;
        }
    }


    private static void viewSwimStudentInformation() {
        List<SwimStudent> students = swimSchool.getStudents(); // Get the list of students
        // Print the header for the student list
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("                                    list student                                   ");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("#     |name       |level");

        int i = 1; // Counter for student numbering
        // Iterate through the list of students and print their information
        for (SwimStudent student : students) {
            System.out.println(i + "| " + student.name + "  | " + student.level);
            i++;
        }
        // Print the footer
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.print(" Please enter a number: ");

        int indexOfStudent;
        SwimStudent studentIsSelected;

        // Loop until a valid index is entered by the user
        while (true) {
            try {
                indexOfStudent = Integer.parseInt(scanner.nextLine()); // Read and parse the user input

                // Check if the index is within the valid range
                if (indexOfStudent > 0 && indexOfStudent <= students.size()) {
                    studentIsSelected = students.get(indexOfStudent - 1); // Get the selected student
                    break; // Exit the loop if a valid index is entered
                } else {
                    System.out.println("Invalid option. Please enter a number between 1 and " + students.size() + ":");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number:");
            }
        }

        // Find the class of the selected student
        SwimClass classOfStudent = swimSchool.findClassForStudent(studentIsSelected);
        // Check if the selected student is in the waiting list
        boolean isInWaitingList = swimSchool.isStudentInWaitingList(studentIsSelected.getName(), studentIsSelected.getLevel());

        // Print the student information
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("Student Information:");
        System.out.println("Name: " + studentIsSelected.getName());
        System.out.println("Level: " + studentIsSelected.getLevel());

        // Print class information if the student is enrolled in a class
        if (classOfStudent != null) {
            System.out.println("Class Day: " + classOfStudent.getDay());
            System.out.println("Class Time: " + classOfStudent.getTime());
            System.out.println("Class Level: " + classOfStudent.getLevel());
            System.out.println("Class Instructor: " + classOfStudent.getInstructor().getName());
        } else {
            System.out.println("This student is not currently enrolled in any class.");
            // Print waiting list status for novice students
            if (studentIsSelected.getLevel().equalsIgnoreCase("novice")) {
                if (isInWaitingList) {
                    System.out.println("This student is on the waiting list.");
                } else {
                    System.out.println("This student is not on the waiting list.");
                }
            }
        }

        // Print the qualifications of the student
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("Qualification:");
        for (Qualification qualification : studentIsSelected.getQualifications()) {
            if (qualification instanceof DistanceSwim) {
                System.out.println("Distance: " + ((DistanceSwim) qualification).getDistance() + " meters");
                System.out.println("Instructor: " + ((DistanceSwim) qualification).getInstructor().getName());
            } else {
                System.out.println("Level: " + ((PersonalSurvival) qualification).getLevel());
                System.out.println("Instructor: " + ((PersonalSurvival) qualification).getInstructor().getName());
            }
        }
    }


    private static void viewSwimLessonDetails() {
        String day = "";
        while (!isValidDay(day)) {
            System.out.println("Please enter the day of the class (e.g., Monday):");
            day = scanner.nextLine();
            if (!isValidDay(day)) {
                System.out.println("Invalid day. Please enter a valid day of the week.");
            }
        }
        String time = "";
        while (!isValidTime(time)) {
            System.out.println("Please enter the time of the class (e.g., 17:00):");
            time = scanner.nextLine();
            if (!isValidTime(time)) {
                System.out.println("Invalid time. Please enter a valid time in the format HH:MM.");
            }
        }

        String level = "";
        while (!isValidLevel(level)) {
            System.out.println("Please enter the level of the class (e.g., Novice):");
            level = scanner.nextLine();
            if (!isValidLevel(level)) {
                System.out.println("Invalid level. Please enter a valid level (Novice, Improver, Advanced).");
            }
        }
        swimSchool.displayClassDetails(day, time, level);
    }

    private static boolean isValidDay(String day) {
        return day.matches("(?i)Monday|Wednesday|Friday");
    }

    private static boolean isValidTime(String time) {
        return Pattern.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$", time);
    }

    private static boolean isValidLevel(String level) {
        return level.equalsIgnoreCase("Novice") || level.equalsIgnoreCase("Improver") || level.equalsIgnoreCase("Advanced");
    }

    private static void viewInstructorSchedule() {
        List<SwimInstructor> swimInstructorList = swimSchool.getInstructors();
        swimInstructorList.sort((i1, i2) -> i1.getName().compareToIgnoreCase(i2.getName()));
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("                                    list Instructor                                   ");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("#     |name       ");
        int i = 1;
        for (SwimInstructor instructor : swimInstructorList) {
            System.out.println(i + "| " + instructor.name + "  | ");
            i++;
        }
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.print("Please enter an option (enter number): ");

        int indexOfInstructor;
        SwimInstructor instructorIsSelected;


        while (true) {
            try {
                indexOfInstructor = Integer.parseInt(scanner.nextLine());

                // Check if the index is within the valid range
                if (indexOfInstructor > 0 && indexOfInstructor <= swimInstructorList.size()) {
                    instructorIsSelected = swimInstructorList.get(indexOfInstructor - 1);
                    break; // Exit the loop if a valid index is entered
                } else {
                    System.out.println("Invalid option. Please enter a number between 1 and " + swimInstructorList.size() + ":");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number:");
            }
        }



        for (SwimClass swimClass : swimSchool.getClasses()) {
            if (swimClass.getInstructor().getName().equalsIgnoreCase(instructorIsSelected.getName())) {
                System.out.println("------------");
                System.out.println("Day: " + swimClass.getDay() + ", Time: " + swimClass.getTime() + ", Level: " + swimClass.getLevel());
                System.out.println("Students in class:");
                for (SwimStudent student : swimClass.getStudents()) {
                    System.out.println(" - " + student.getName());
                }
                System.out.println();
            }
        }
    }

    private static void addNewSwimStudent() {
        System.out.println("Please enter the name of the new student:");
        String studentName = scanner.nextLine();
        swimSchool.addStudentToClassOrWaitingList(studentName);
    }

    private static void awardSwimQualification() {
        List<SwimInstructor> instructors = swimSchool.getInstructors(); // Get the list of instructors
        instructors.sort((i1, i2) -> i1.getName().compareToIgnoreCase(i2.getName())); // Sort instructors by name

        System.out.println("Select an instructor:"); // Prompt the user to select an instructor
        for (int i = 0; i < instructors.size(); i++) { // List all instructors
            System.out.println((i + 1) + ". " + instructors.get(i).getName());
        }
        System.out.println("Select an instructor:(Please enter a number:)");

        int instructorIndex;
        SwimInstructor selectedInstructor;

        // Loop to select an instructor
        while (true) {
            try {
                instructorIndex = Integer.parseInt(scanner.nextLine()) - 1; // Read and parse the user input

                // Check if the index is within the valid range
                if (instructorIndex >= 0 && instructorIndex < instructors.size()) {
                    selectedInstructor = instructors.get(instructorIndex); // Get the selected instructor
                    break; // Exit the loop if a valid index is entered
                } else {
                    System.out.println("Invalid option. Please enter a number between 1 and " + instructors.size() + ":");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number:");
            }
        }

        List<SwimStudent> students = swimSchool.getStudents(); // Get the list of students
        students.sort((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName())); // Sort students by name

        System.out.println("Select a student:"); // Prompt the user to select a student
        for (int i = 0; i < students.size(); i++) { // List all students
            System.out.println((i + 1) + ". " + students.get(i).getName() + " - Level: " + students.get(i).getLevel());
        }

        int studentIndex;
        SwimStudent selectedStudent;

        // Loop to select a student
        while (true) {
            try {
                studentIndex = Integer.parseInt(scanner.nextLine()) - 1; // Read and parse the user input

                // Check if the index is within the valid range
                if (studentIndex >= 0 && studentIndex < students.size()) {
                    selectedStudent = students.get(studentIndex); // Get the selected student
                    break; // Exit the loop if a valid index is entered
                } else {
                    System.out.println("Invalid option. Please enter a number between 1 and " + students.size() + ":");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number:");
            }
        }

        // If the student is at the Advanced level, award either a distance swim or personal survival qualification
        if (selectedStudent.getLevel().equalsIgnoreCase("Advanced")) {
            System.out.println("Is the award a distance swim qualification or personal survival qualification? (Enter 'distance' or 'survival'):");
            String awardType;

            // Loop to select the type of qualification
            while (true) {
                awardType = scanner.nextLine().trim().toLowerCase(); // Read and normalize the user input

                if (awardType.equals("distance") || awardType.equals("survival")) {
                    break; // Exit the loop if a valid input is entered
                } else {
                    System.out.println("Invalid input. Please enter 'distance' or 'survival':");
                }
            }

            // Award a distance swim qualification
            if (awardType.equalsIgnoreCase("distance")) {
                int[] advancedDistanceQualifications = {800, 1500, 3000}; // Available distance qualifications for Advanced level
                System.out.println("Select a distance qualification:(Please enter a number:)");
                for (int i = 0; i < advancedDistanceQualifications.length; i++) { // List all distance qualifications
                    System.out.println((i + 1) + ". " + advancedDistanceQualifications[i] + " meter");
                }

                int qualificationIndex;

                // Loop to select a distance qualification
                while (true) {
                    try {
                        qualificationIndex = Integer.parseInt(scanner.nextLine()) - 1; // Read and parse the user input

                        // Check if the index is within the valid range
                        if (qualificationIndex >= 0 && qualificationIndex < advancedDistanceQualifications.length) {
                            break; // Exit the loop if a valid index is entered
                        } else {
                            System.out.println("Invalid option. Please enter a number between 1 and " + advancedDistanceQualifications.length + ":");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number:");
                    }
                }

                int selectedQualification = advancedDistanceQualifications[qualificationIndex]; // Get the selected qualification distance
                Qualification newQualification = new DistanceSwim(selectedQualification, selectedInstructor); // Create a new DistanceSwim qualification
                boolean haveSameQualification = false;
                List<Qualification> studentsQualification = selectedStudent.getQualifications(); // Get the student's existing qualifications

                // Check if the student already has the selected qualification
                for (Qualification qualification : studentsQualification) {
                    if (qualification instanceof DistanceSwim) {
                        if (((DistanceSwim) qualification).getDistance() == selectedQualification) {
                            haveSameQualification = true;
                            break;
                        }
                    }
                }

                // Add the new qualification to the student if they don't already have it
                if (!haveSameQualification) {
                    selectedStudent.addQualification(newQualification);
                    System.out.println("Qualification " + selectedQualification + " awarded to " + selectedStudent.getName() + " by " + selectedInstructor.getName() + ".");
                } else {
                    System.out.println("Student " + selectedStudent.getName() + " already has the qualification " + selectedQualification);
                }
            }
            // Award a personal survival qualification
            else if (awardType.equalsIgnoreCase("survival")) {
                String[] survivalQualifications = {"Bronze", "Silver", "Gold"}; // Available survival qualifications
                System.out.println("Select a survival qualification:(Please enter a number:)");
                for (int i = 0; i < survivalQualifications.length; i++) { // List all survival qualifications
                    System.out.println((i + 1) + ". " + survivalQualifications[i]);
                }

                int qualificationIndex;
                String selectedQualification;

                // Loop to select a survival qualification
                while (true) {
                    try {
                        qualificationIndex = Integer.parseInt(scanner.nextLine()) - 1; // Read and parse the user input

                        // Check if the index is within the valid range
                        if (qualificationIndex >= 0 && qualificationIndex < survivalQualifications.length) {
                            selectedQualification = survivalQualifications[qualificationIndex]; // Get the selected qualification level
                            break; // Exit the loop if a valid index is entered
                        } else {
                            System.out.println("Invalid option. Please enter a number between 1 and " + survivalQualifications.length + ":");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number:");
                    }
                }

                Qualification newQualification = new PersonalSurvival(selectedQualification, selectedInstructor); // Create a new PersonalSurvival qualification
                boolean haveSameQualification = false;
                List<Qualification> studentsQualification = selectedStudent.getQualifications(); // Get the student's existing qualifications

                // Check if the student already has the selected qualification
                for (Qualification qualification : studentsQualification) {
                    if (qualification instanceof PersonalSurvival) {
                        if (((PersonalSurvival) qualification).getLevel().equals(selectedQualification)) {
                            haveSameQualification = true;
                            break;
                        }
                    }
                }

                // Add the new qualification to the student if they don't already have it
                if (!haveSameQualification) {
                    selectedStudent.addQualification(newQualification);
                    System.out.println("Qualification " + selectedQualification + " awarded to " + selectedStudent.getName() + " by " + selectedInstructor.getName() + ".");
                } else {
                    System.out.println("Student " + selectedStudent.getName() + " already has the qualification " + selectedQualification);
                }
            }
        } else {
            // Award a distance qualification for Novice or Improver levels
            int[] distanceQualifications = selectedStudent.getLevel().equalsIgnoreCase("Novice") ? new int[]{5, 10, 20} : new int[]{100, 200, 400}; // Available distance qualifications for Novice and Improver levels
            System.out.println("Select a distance qualification:");

            for (int i = 0; i < distanceQualifications.length; i++) { // List all distance qualifications
                System.out.println((i + 1) + ". " + distanceQualifications[i] + " meters");
            }

            int qualificationIndex;

            // Loop to select a distance qualification
            while (true) {
                try {
                    qualificationIndex = Integer.parseInt(scanner.nextLine()) - 1; // Read and parse the user input

                    // Check if the index is within the valid range
                    if (qualificationIndex >= 0 && qualificationIndex < distanceQualifications.length) {
                        break; // Exit the loop if a valid index is entered
                    } else {
                        System.out.println("Invalid option. Please enter a number between 1 and " + distanceQualifications.length + ":");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number:");
                }
            }

            int selectedQualification = distanceQualifications[qualificationIndex]; // Get the selected qualification distance
            Qualification newQualification = new DistanceSwim(selectedQualification, selectedInstructor); // Create a new DistanceSwim qualification
            boolean haveSameQualification = false;
            List<Qualification> studentsQualification = selectedStudent.getQualifications(); // Get the student's existing qualifications

            // Check if the student already has the selected qualification
            for (Qualification qualification : studentsQualification) {
                if (qualification instanceof DistanceSwim) {
                    if (((DistanceSwim) qualification).getDistance() == selectedQualification) {
                        haveSameQualification = true;
                        break;
                    }
                }
            }

            // Add the new qualification to the student if they don't already have it
            if (!haveSameQualification) {
                selectedStudent.addQualification(newQualification);
                System.out.println("Qualification " + selectedQualification + " awarded to " + selectedStudent.getName() + " by " + selectedInstructor.getName() + ".");
            } else {
                System.out.println("Student " + selectedStudent.getName() + " already has the qualification " + selectedQualification);
            }

            // Check if the student should be moved to the next level
            if ((selectedStudent.getLevel().equalsIgnoreCase("Novice") && selectedQualification == 20) || (selectedStudent.getLevel().equalsIgnoreCase("Improver") && selectedQualification == 400)) {
                String newLevel = selectedStudent.getLevel().equalsIgnoreCase("novice") ? "improver" : "advanced";
                selectedStudent.setLevel(newLevel);
                swimSchool.addToWaitingList(selectedStudent);
                System.out.println("Student " + selectedStudent.getName() + " has been moved to the " + newLevel + " level and added to the waiting list.");
            }
        }
    }


    private static void moveSwimStudentFromWaitingList() {
        swimSchool.moveSwimStudentFromWaitingList();
    }
}

