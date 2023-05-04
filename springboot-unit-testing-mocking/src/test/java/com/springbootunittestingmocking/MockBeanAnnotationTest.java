package com.springbootunittestingmocking;

import com.springbootunittestingmocking.dao.ApplicationDao;
import com.springbootunittestingmocking.models.CollegeStudent;
import com.springbootunittestingmocking.models.StudentGrades;
import com.springbootunittestingmocking.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MockBeanAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @MockBean
    private ApplicationDao applicationDao;

    //@InjectMocks
    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    void beforeEach() {
        student.setFirstname("hong");
        student.setLastname("gil dong");
        student.setEmailAddress("gildong@school.com");
        student.setStudentGrades(studentGrades);
    }

    @Test
    @DisplayName("When & Verify Test")
    void assertEqualsTestAddGrades() {
        when(applicationDao.addGradeResultsForSingleClass(
                studentGrades.getMathGradeResults()))
                .thenReturn(100.0)
                .thenReturn(200.0)
                .thenReturn(300.0);

        assertEquals(100, applicationService.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()));

        verify(applicationDao, times(1))
                .addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

    @Test
    @DisplayName("find gpa")
    void assertEqualsTestFindGpa() {
        when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults()))
                .thenReturn(50.0);

        assertEquals(50.0, applicationService.findGradePointAverage(studentGrades.getMathGradeResults()));
    }

    @Test
    @DisplayName("Not Null")
    void assertNotNullTest() {
        when(applicationDao.checkNull(studentGrades.getMathGradeResults()))
                .thenReturn(true);

        assertNotNull(applicationService.checkNull(student.getStudentGrades()
                .getMathGradeResults()), "obj should not be null");
    }

    @Test
    @DisplayName("Thrown an Exception")
    void throwExceptionTest() {
        CollegeStudent nullStudent = (CollegeStudent) context.getBean("collegeStudent");

        doThrow(new RuntimeException()).
                when(applicationDao.checkNull(nullStudent)); //.thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });
    }

    @Test
    @DisplayName("multi stubbing")
    void stubbingCalls() {
        CollegeStudent nullStudent = new CollegeStudent();

        when(applicationDao.checkNull(nullStudent))
                .thenThrow(new RuntimeException())
                .thenReturn("do not throw exception second time");

        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });

        assertEquals("do not throw exception second time",
                applicationService.checkNull(nullStudent));

         verify(applicationDao, times(2)).checkNull(nullStudent);
    }
}
