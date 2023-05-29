package com.springbootmvctesting.controller;


import com.springbootmvctesting.models.component.Gradebook;
import com.springbootmvctesting.models.student.CollegeStudent;
import com.springbootmvctesting.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradebookController {

	@Autowired
	private Gradebook gradebook;

	@Autowired
	private StudentAndGradeService service;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getStudents(Model m) {
		Iterable<CollegeStudent> collegeStudents = service.getGradeBook();
		m.addAttribute("students", collegeStudents);
		return "index";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String createStudent(@ModelAttribute("student") CollegeStudent student, Model m) {
		service.createStudent(student.getFirstname(), student.getLastname(), student.getEmailAddress());
		return "index";
	}

	@GetMapping("/delete/student/{id}")
	public String deleteStudent(@PathVariable int id, Model m) {
		service.deleteStudent(id);
		Iterable<CollegeStudent> collegeStudents = service.getGradeBook();
		m.addAttribute("students", collegeStudents);
		return "index";
	}

	@GetMapping("/studentInformation/{id}")
	public String studentInformation(@PathVariable int id, Model m) {
		return "studentInformation";
	}
}
