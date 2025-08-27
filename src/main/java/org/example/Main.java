package org.example;

import org.example.config.DatabaseConfig;
import org.example.model.StudentEntity;
import org.example.repository.StudentRepository;
import org.example.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.util.StudentUtils;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        // Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/student-management-system", "root", "");
        Connection connection = DatabaseConfig.GET_INSTANCE().getConnection();


        StudentRepository studentRepository = new StudentRepository(connection);
        StudentService studentService = new StudentService(studentRepository);

//        studentRepository.insertStudent(new StudentEntity(0, "Student One", "email1@gmail.com", 18, LocalDate.of(2024, 9, 23), 9.5));
//        studentRepository.insertStudent(new StudentEntity(0, "Student Two", "email2@gmail.com", 20, LocalDate.of(2023, 8, 17), 7.32));
//        studentRepository.insertStudent(new StudentEntity(0, "Student Three", "email3@gmail.com", 22, LocalDate.of(2022, 2, 13), 8.76));
//        studentRepository.insertStudent(new StudentEntity(0, "Student Four", "email4@gmail.com", 30, LocalDate.of(2024, 6, 30), 9.85));

        studentRepository.deleteStudentById(1);
        studentRepository.updateStudentEmailById("email33@gmail.com", 3);

        List<StudentEntity> allStudents = studentRepository.listAllStudents();
        logger.info("All Students:\n{}", StudentUtils.formatStudentsList(allStudents));

        List<StudentEntity> filteredByAge = studentService.filterStudentsByAgeStatement(allStudents, 20);
        logger.info("Filtered by Age: \n{}", StudentUtils.formatStudentsList(filteredByAge));

        Optional<List<StudentEntity>> nameXStudents = studentRepository.searchStudentByName("Student One");
        List<StudentEntity> nameXStudentsUnwrapped = nameXStudents.orElse(Collections.emptyList());
        logger.info("Name X students:\n{}", StudentUtils.formatStudentsList(nameXStudentsUnwrapped));


        studentRepository.destroy();
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}