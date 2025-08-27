package org.example.repository;

import org.example.exception.BusinessInsertException;
import org.example.model.StudentEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository {

    private final Logger logger = LoggerFactory.getLogger(StudentRepository.class);

    /*
    info
    warn
    debug
    trace
    error
     */

    private final Connection con;

    private PreparedStatement insertNewStudentRecordsStatement;
    private PreparedStatement listAllStudentsFromDatabaseStatement;
    private PreparedStatement searchStudentsByNameStatement;
    private PreparedStatement deleteStudentByIdStatement;
    private PreparedStatement updateStudentEmailByIdStatement;
    private PreparedStatement filterStudentsByAgeStatement;
    private PreparedStatement sortStudentsByGradeStatement;
    private PreparedStatement calculateAverageGradeStatement;


    public StudentRepository(Connection con) {
        this.con = con;
        try {
            insertNewStudentRecordsStatement = con.prepareStatement("INSERT INTO students VALUES(NULL,?,?,?,?,?)");
            listAllStudentsFromDatabaseStatement = con.prepareStatement("SELECT * FROM students");
            searchStudentsByNameStatement = con.prepareStatement("SELECT * FROM students WHERE name = ?"); //todo improve for partial name
            updateStudentEmailByIdStatement = con.prepareStatement("UPDATE students SET email = ? where id = ?");
            deleteStudentByIdStatement = con.prepareStatement("DELETE FROM students WHERE id = ?");


        } catch (SQLException e) {
           e.printStackTrace();
        }
    }

    public boolean insertStudent(StudentEntity student) {
        logger.info("Insert new student");
        try {
            logger .debug("Insert student with name: {}, date: {}", student.getName(), student.getEnrollment_date());
            insertNewStudentRecordsStatement.setString(1,student.getName());
            insertNewStudentRecordsStatement.setString(2,student.getEmail());
            insertNewStudentRecordsStatement.setInt(3,student.getAge());
            insertNewStudentRecordsStatement.setDate(4,Date.valueOf(student.getEnrollment_date()));
            insertNewStudentRecordsStatement.setDouble(5,student.getGrade());
            //if insert => result will be 1
            int result = insertNewStudentRecordsStatement.executeUpdate();
            // return true if insert
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return false;
    }
    public List<StudentEntity> listAllStudents() {
        try (ResultSet resultSet = listAllStudentsFromDatabaseStatement.executeQuery()) { //asa inchidem cu try cu resurse, fara finally
            List<StudentEntity> studentEntityList = new ArrayList<>();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                LocalDate enrollment_date = resultSet.getDate("enrollment_date").toLocalDate();
                double grade = resultSet.getDouble("grade");

                studentEntityList.add(new StudentEntity(id, name,email,age,enrollment_date,grade));
            }
            return studentEntityList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessInsertException(e.getMessage());
        }
    }

    public Optional<List<StudentEntity>> searchStudentByName(String name){
        ResultSet resultSet = null;
        try {
            List<StudentEntity> studentEntityList = new ArrayList<>();

            searchStudentsByNameStatement.setString(1,name); //take query and set the parameter "?"
            resultSet = searchStudentsByNameStatement.executeQuery(); //return resultSet

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String studentName = resultSet.getString("name");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                LocalDate enrollment_date = resultSet.getDate("enrollment_date").toLocalDate();
                double grade = resultSet.getDouble("grade");

                studentEntityList.add(new StudentEntity(id,studentName,email,age,enrollment_date,grade));
            }
            return studentEntityList.isEmpty() ? Optional.empty() : Optional.of(studentEntityList);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessInsertException(e.getMessage());
        } finally {  //finally se acceseaza chiar daca am dat catch la o exceptie.
            try {
                if(resultSet != null) resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public boolean updateStudentEmailById(String email, int id){
        /*
        1. setam parametrii pe care ii avem cu ? in query.
        2. apelam executeUpdate care porneste query-ul
        3. punem rezultatul intr-o variabila. daca este > 0 => a facut update
         */
        try {
            updateStudentEmailByIdStatement.setString(1, email);
            updateStudentEmailByIdStatement.setInt(2, id);

            int result = updateStudentEmailByIdStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessInsertException(e.getMessage());
        }
    }

    public boolean deleteStudentById(int id){
        try {
            deleteStudentByIdStatement.setInt(1, id);
            int result = deleteStudentByIdStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessInsertException(e.getMessage());
        }
    }

    public void destroy(){
        try {
            if (insertNewStudentRecordsStatement != null) insertNewStudentRecordsStatement.close();
            if (listAllStudentsFromDatabaseStatement != null) listAllStudentsFromDatabaseStatement.close();
            if (searchStudentsByNameStatement != null) searchStudentsByNameStatement.close();
            if (updateStudentEmailByIdStatement != null) updateStudentEmailByIdStatement.close();
            if (deleteStudentByIdStatement != null) deleteStudentByIdStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
