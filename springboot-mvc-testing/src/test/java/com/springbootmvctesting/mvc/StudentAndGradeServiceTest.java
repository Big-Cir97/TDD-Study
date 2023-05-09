package com.springbootmvctesting.mvc;

import com.springbootmvctesting.SpringbootMvcTestingApplication;
import com.springbootmvctesting.models.student.CollegeStudent;
import com.springbootmvctesting.repository.StudentDao;
import com.springbootmvctesting.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest(classes = SpringbootMvcTestingApplication.class)
public class StudentAndGradeServiceTest {

    @Autowired
    StudentAndGradeService studentService;

    @Autowired
    StudentDao studentDao;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    void setupData() {
        jdbc.execute("insert into student(id, firstname, lastname, email_address) " +
                        "values (1, 'Gildong', 'Hong', 'gildong@school.ac.kr')");
    }

    @AfterEach
    void setupAfterTransaction() {
        jdbc.execute("delete from student");
    }

    @Test

    void createStudentAndService() {
        // given
        studentService.createStudent("Gilsoon", "Hong", "gilsoon@school.ac.kr");

        // when
        CollegeStudent student = studentDao.findByEmailAddress("gilsoon@school.ac.kr");

        // then
        assertEquals("gilsoon@school.ac.kr", student.getEmailAddress(), "find email");
    }

    @Test
    void studentCheckNull() {
        // return true if id = 1, exists in db
        assertTrue(studentService.checkIfStudentIsNull(1));

        // return false if id = 0, doesn't exists in db
        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    void deleteStudent() {
        Optional<CollegeStudent> deleteStudent = studentDao.findById(1);

        assertTrue(deleteStudent.isPresent(), "return true");

        studentService.deleteStudent(1);

        deleteStudent = studentDao.findById(1);

        assertFalse(deleteStudent.isPresent(), "return false");
    }

    @Sql("/insertdata.sql")
    @Test
    void getGradeBook() {
        Iterable<CollegeStudent> iterableCollegeStudents = studentService.getGradeBook();

        List<CollegeStudent> list = new ArrayList<>();

        for (CollegeStudent student : iterableCollegeStudents) {
            list.add(student);
        }

        assertEquals(5, list.size());
    }
}
