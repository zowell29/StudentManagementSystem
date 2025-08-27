package org.example.service;

import org.example.model.StudentEntity;
import org.example.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;

public class StudentService {
    private StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentEntity> filterStudentsByAge(List<StudentEntity> studentEnityList, int age) {
        logger.debug("Filter students by age: {}", age);
        return studentEnityList.stream()
                .filter(s -> s.getAge() > age)
                .toList();
    }

    public List<StudentEntity> sortStudentsByGrade(List<StudentEntity> studentEntityList, String ordering) {
        logger.debug("Filter students by grade");
        if (ordering.equalsIgnoreCase("ascending") || ordering.equalsIgnoreCase("asc")) {
            logger.debug("Ascending order");
            return studentEntityList.stream()
                    .sorted(Comparator.comparing(StudentEntity::getGrade))
                    .toList();
        }
        if (ordering.equalsIgnoreCase("descending") || ordering.equalsIgnoreCase("desc")){
            logger.debug("Descending order");
            return  studentEntityList.stream()
                    .sorted(Comparator.comparing(StudentEntity::getGrade).reversed())
                    .toList();
        }
        logger.error("ordering must be ascending or descending");
        return List.of();
    }

    public double calculateAverageGrade(List<StudentEntity> studentEntityList){
        return studentEntityList.stream()
                .mapToDouble(StudentEntity::getGrade)
                .average()
                .orElse(0.0);
    }


}
