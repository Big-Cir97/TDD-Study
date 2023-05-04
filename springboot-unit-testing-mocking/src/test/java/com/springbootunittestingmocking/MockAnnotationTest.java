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
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MockAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @Mock
    private ApplicationDao applicationDao;

    @InjectMocks
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

        verify(applicationDao, times(3))
                .addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }
}
