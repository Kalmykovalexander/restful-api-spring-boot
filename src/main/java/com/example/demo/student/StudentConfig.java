package com.example.demo.student;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student alex = new Student(
                    "Alex",
                    "kalmykovalexander28@gmail.com",
                    LocalDate.of(1992, Month.AUGUST, 28)
            );
            Student mike = new Student(
                    "Mike",
                    "mike@gmail.com",
                    LocalDate.of(1992, Month.AUGUST, 30)
            );
            List<Student> list = new ArrayList<>();
            list.add(alex);
            list.add(mike);
            repository.saveAll(list);
        };
    }

}
