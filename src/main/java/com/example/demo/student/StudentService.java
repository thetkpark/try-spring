package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return this.studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = this.studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalStateException("Email taken");
        }
        this.studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        boolean exists = this.studentRepository.existsById(id);
        if(!exists) throw new IllegalStateException("Student with id " + id + " does not exists");
        this.studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = this.studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException(("Student with id " + studentId + " does not exists")));
        if(name != null && name.length() > 0) student.setName(name);
        if(email != null && email.length() > 0) student.setEmail(email);
    }
}
