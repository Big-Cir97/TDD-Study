package com.springbootunittestingmocking;

import com.springbootunittestingmocking.dao.ApplicationDao;
import com.springbootunittestingmocking.models.CollegeStudent;
import com.springbootunittestingmocking.service.ApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class SpringbootUnitTestingMockingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootUnitTestingMockingApplication.class, args);
    }

    @Bean(name = "applicationService")
    ApplicationService getApplicationService() {
        return new ApplicationService();
    }

    @Bean(name = "applicationDao")
    ApplicationDao getApplicationDao() {
        return new ApplicationDao();
    }

    @Bean(name = "collegeStudent")
    @Scope(value = "prototype")
    CollegeStudent getCollegeStudent() {
        return new CollegeStudent();
    }

}
