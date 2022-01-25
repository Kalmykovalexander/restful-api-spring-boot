package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // GET
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    // POST
    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.
                findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()) {
            throw new IllegalStateException("email is already present in database");
        }
        studentRepository.save(student);
    }

    // DELETE
    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException(
                    "Student with id " + studentId + " does not exists");
        }
        studentRepository.deleteById(studentId);
    }

    // PUT
    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        // Check user, exists or not
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException(
                    "Student with id " + studentId + " does not exist");
        }

        Student student = studentRepository.getById(studentId);

        // Check name
        if (Objects.equals(student.getName(), name)) {
            throw new IllegalStateException("Name is already exists");
        }
        if (name != null && name.length() > 0) {
            student.setName(name);
        }

        // Check email
        if (email != null &&
                 email.length() > 0 &&
                 !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException(
                        "This email is already exists, choose another one");
            }
            student.setEmail(email);
        }
    }
}
