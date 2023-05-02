package com.example.applicationtesting;

import com.example.springbootunittesting.SpringbootUnitTestingApplication;
import com.example.springbootunittesting.models.CollegeStudent;
import com.example.springbootunittesting.models.StudentGrades;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringbootUnitTestingApplication.class)
@Slf4j
public class ApplicationTest {

    @Autowired
    ApplicationContext context;

    private static int count = 0;

    @Value("${info.app.name}")
    private String appInfo;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @Value("${info.school.name}")
    private String schoolName;

    @Autowired
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void beforeEach() {
        count += 1;
        log.info("appInfo : {}, appDescription : {}, appVersion : {}, schoolName : {}", appInfo, appDescription, appVersion, schoolName);
        student.setFirstname("student");
        student.setLastname("1_");
        student.setEmailAddress("1_student@school.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 67.50, 91.75)));
        student.setStudentGrades(studentGrades);
    }

    @Test
    @DisplayName("add grade results for student grades")
    void addGradeResultsForStudentGrades() {
        assertEquals(344.25, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()
        ));

        assertNotEquals(352.25, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()
        ));
    }

    @Test
    @DisplayName("grade greater")
    void gradeGreaterStudentGrades() {
        assertTrue(studentGrades.isGradeGreater(100, 55), "should be gradeOne > gradeTwo");
        assertFalse(studentGrades.isGradeGreater(-100, 55), "test only failure");
        assertNotNull(studentGrades.checkNull(student.getStudentGrades().getMathGradeResults()), "not null");
    }

    @Test
    @DisplayName("Create student without grade")
    void createStudent() {
        log.info("get context : " + context.getBean("collegeStudent", CollegeStudent.class));
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        studentTwo.setFirstname("student");
        studentTwo.setLastname("2_");
        studentTwo.setEmailAddress("2_student@school.com");

        assertNotNull(studentTwo.getFirstname());
        assertNotNull(studentTwo.getLastname());
        assertNotNull(studentTwo.getEmailAddress());
        assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()));
    }

    @Test
    @DisplayName("verity prototypes")
    void verityPrototypes() {
        log.info("get context : " + context.getBean("collegeStudent", CollegeStudent.class));
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);

        assertSame(student, studentTwo);
    }
}
