package org.example.service;

import org.example.model.StudentEntity;
import org.example.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class StudentService {
    private StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentEntity> filterStudentsByAgeStatement(List<StudentEntity> studentEnityList, int age) {
        logger.info("Filter students by age");
        logger.debug("Filter students by age: {}", age);
        return studentEnityList.stream()
                .filter(s -> s.getAge() > age)
                .toList();
    }



}
