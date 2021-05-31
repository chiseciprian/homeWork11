package ro.fasttrackit.homework11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ro.fasttrackit.homework11.Homework11Application;
import ro.fasttrackit.homework11.config.TestBeans;
import ro.fasttrackit.homework11.model.entity.Course;
import ro.fasttrackit.homework11.service.CourseService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {Homework11Application.class, TestBeans.class})
class CourseControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CourseService courseService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("GET /courses")
    void getAll() throws Exception {
        doReturn(List.of(
                new Course("randomId1", "History"),
                new Course("randomId2", "Biology")
        )).when(courseService).getAll();

        mvc.perform(get("/courses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].discipline", is("History")))
                .andExpect(jsonPath("$[1].discipline", is("Biology")));

        verify(courseService, times(1)).getAll();
    }

    @Test
    void addCourse() throws Exception {
        Course mockCourse = new Course("randomId1", "History");
        String body = objectMapper.writeValueAsString(mockCourse);
        doReturn(mockCourse)
                .when(courseService).addCourse(any(Course.class));

        mvc.perform(post("/courses")
                .contentType(APPLICATION_JSON)
                .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseId", is("randomId1")))
                .andExpect(jsonPath("$.discipline", is("History")));

        verify(courseService, times(1)).addCourse(any(Course.class));
    }

    @Test
    void getCourse() throws Exception {
        doReturn(Optional.of(new Course("randomId1", "History")))
                .when(courseService).getCourse("randomId1");

        mvc.perform(get("/courses/{courseId}", "randomId1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseId", is("randomId1")))
                .andExpect(jsonPath("$.discipline", is("History")));

        verify(courseService, times(1)).getCourse(anyString());
    }

    @Configuration
    static class TestBeans {
        @Bean
        CourseService courseService() {
            return mock(CourseService.class);
        }
    }

}