package com.springbootmvctesting;

import com.springbootmvctesting.models.grade.Grade;
import com.springbootmvctesting.models.grade.HistoryGrade;
import com.springbootmvctesting.models.grade.MathGrade;
import com.springbootmvctesting.models.grade.ScienceGrade;
import com.springbootmvctesting.models.student.CollegeStudent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class SpringbootMvcTestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMvcTestingApplication.class, args);
    }

    @Bean
    @Scope(value = "prototype")
    CollegeStudent getCollegeStudent() {
        return new CollegeStudent();
    }

    @Bean
    @Scope(value = "prototype")
    Grade getMathGrade(double grade) {
        return new MathGrade(grade);
    }

    @Bean
    @Scope(value = "prototype")
    @Qualifier("mathGrades")
    MathGrade getGrade() {
        return new MathGrade();
    }

    @Bean
    @Scope(value = "prototype")
    @Qualifier("scienceGrades")
    ScienceGrade getScienceGrade() {
        return new ScienceGrade();
    }

    @Bean
    @Scope(value = "prototype")
    @Qualifier("historyGrades")
    HistoryGrade getHistoryGrade() {
        return new HistoryGrade();
    }

}
