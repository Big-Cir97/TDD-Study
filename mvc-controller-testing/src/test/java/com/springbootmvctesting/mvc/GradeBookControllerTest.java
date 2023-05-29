package com.springbootmvctesting.mvc;

import com.springbootmvctesting.models.GradebookCollegeStudent;
import com.springbootmvctesting.models.student.CollegeStudent;
import com.springbootmvctesting.repository.StudentDao;
import com.springbootmvctesting.service.StudentAndGradeService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class GradeBookControllerTest {

    private static MockHttpServletRequest request;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StudentDao studentDao;

    @Mock
    StudentAndGradeService studentAndGradeService;

    @BeforeAll
    static void setup() {
        request = new MockHttpServletRequest();
        request.setParameter("firstname", "Gildong");
        request.setParameter("lastname", "Hong");
        request.setParameter("emailAddress", "gildong@school.ac.kr");
    }

    @BeforeEach
    void beforeEach() {
        jdbc.execute("insert into student(id, firstname, lastname, email_address) " +
                "values (1, 'Gildong', 'Hong', 'gildong@school.ac.kr')");
    }

    @AfterEach
    void setupAfterTransaction() {
        jdbc.execute("delete from student");
    }

    @Test
    @DisplayName("저장된 학생이 일치한지 확인한다.")
    void getStudentHttpRequest() throws Exception {
        CollegeStudent one = new GradebookCollegeStudent("Gildong", "Hong", "gildong@school.ac.kr");
        CollegeStudent two = new GradebookCollegeStudent("GilSoon", "Hong", "gilsoon@school.ac.kr");

        List<CollegeStudent> list = new ArrayList<>(List.of(one, two));

        when(studentAndGradeService.getGradeBook()).thenReturn(list);

        Assertions.assertIterableEquals(list, studentAndGradeService.getGradeBook());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "index");
    }

    @Test
    @DisplayName("post 를 이용해서 학생을 추가한다.")
    void createStudentHttpRequest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firstname", request.getParameterValues("firstname"))
                .param("lastname", request.getParameterValues("lastname"))
                .param("emailAddress", request.getParameterValues("emailAddress"))
        ).andExpect(status().isOk())
        .andReturn();

        System.out.println("params : " + mvcResult.getRequest().getParameter("firstname")); // li
        System.out.println("params : " + mvcResult.getRequest().getParameter("lastname"));  // dong

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "index");

        // test 시 BeforeEach 제거, post 요청 시 서비스 로직을 통해 db 중복 저장
        CollegeStudent verifyStudent = studentDao.findByEmailAddress("gildong@school.ac.kr");
        assertNotNull(verifyStudent, "Student should be found");
    }

    @Test
    @DisplayName("특정 학생을 삭제한다.")
    void deleteStudentHttpRequest() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.
                get("/delete/student/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "index");

        assertFalse(studentDao.findById(1).isPresent());
    }
}
