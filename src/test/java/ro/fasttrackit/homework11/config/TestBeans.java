package ro.fasttrackit.homework11.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.fasttrackit.homework11.service.CourseService;
import ro.fasttrackit.homework11.service.CourseStudentService;
import ro.fasttrackit.homework11.service.StudentService;

import static org.mockito.Mockito.mock;
@Configuration
public class TestBeans {
    @Bean
    CourseService courseService() {
        return mock(CourseService.class);
    }

    @Bean
    CourseStudentService courseStudentService() {
        return mock(CourseStudentService.class);
    }

    @Bean
    StudentService studentService() {
        return mock(StudentService.class);
    }
}
