
import java.io.*;
import java.util.*;

public class StudentManager {
    private ArrayList<Student> students;
    private final String FILE_NAME = "students.ser";

    public StudentManager() {
        students = loadStudentsFromFile();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveStudentsToFile();
        System.out.println("Student added successfully.");
    }

    public void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    public Student searchStudent(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public void updateStudent(int id, String newName, double newMarks) {
        Student s = searchStudent(id);
        if (s != null) {
            s.setName(newName);
            s.setMarks(newMarks);
            saveStudentsToFile();
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public void deleteStudent(int id) {
        Student s = searchStudent(id);
        if (s != null) {
            students.remove(s);
            saveStudentsToFile();
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public void sortByName() {
        students.sort(Comparator.comparing(Student::getName));
        System.out.println("Sorted by name:");
        viewAllStudents();
    }

    public void sortByMarks() {
        students.sort(Comparator.comparingDouble(Student::getMarks));
        System.out.println("Sorted by marks:");
        viewAllStudents();
    }

    private void saveStudentsToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Student> loadStudentsFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<Student>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
