package com.springbootunittestingmocking;

import com.springbootunittestingmocking.models.CollegeStudent;
import com.springbootunittestingmocking.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ReflectionTestUtilsTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void studentBeForEach() {
        studentOne.setFirstname("hong");
        studentOne.setLastname("gil dong");
        studentOne.setEmailAddress("gildong@ac.kr");
        studentOne.setStudentGrades(studentGrades);

        // setField(target Obj, field name, value)
        ReflectionTestUtils.setField(studentOne, "id", 1);
        ReflectionTestUtils.setField(studentOne, "studentGrades",
                new StudentGrades(new ArrayList<>(Arrays.asList(100.0, 90.0, 70.0, 60.0))));
    }

    @Test
    @DisplayName("get private Field")
    void getPrivateField() {
        assertEquals(1, ReflectionTestUtils.getField(studentOne, "id"));
    }

    @Test
    @DisplayName("invoke method")
    void invokeMethod() {
        assertEquals("hong 1",
                ReflectionTestUtils.invokeMethod(studentOne, "getFirstNameAndId"),
                "fail");
    }
}
