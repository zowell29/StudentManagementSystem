package org.example.util;

import org.example.model.StudentEntity;

import java.util.List;
import java.util.stream.Collectors;

public class StudentUtils {
    public static String formatStudentsList(List<StudentEntity> studentsList){
        return studentsList.stream()
                .map(StudentEntity::toString)  //.map(s->s.toString()
                .collect(Collectors.joining("\n"));
    }
}
