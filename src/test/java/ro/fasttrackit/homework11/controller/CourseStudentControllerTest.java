package ro.fasttrackit.homework11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ro.fasttrackit.homework11.Homework11Application;
import ro.fasttrackit.homework11.config.TestBeans;
import ro.fasttrackit.homework11.model.entity.CourseStudent;
import ro.fasttrackit.homework11.model.entity.Student;
import ro.fasttrackit.homework11.service.CourseStudentService;

import java.util.List;

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
class CourseStudentControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CourseStudentService courseStudentService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void studentsByCourse() throws Exception {
        doReturn(List.of(
                new Student("randomId1", "randomName", 12)
        )).when(courseStudentService).studentsByCourse(anyString());

        mvc.perform(get("/course/{courseId}/students", "randomId"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name", is("randomName")))
                .andExpect(jsonPath("$[0].age", is(12)));

        verify(courseStudentService, times(1)).studentsByCourse(anyString());
    }

    @Test
    void enrollStudentToCourse() throws Exception {
        CourseStudent mockCourseStudent = getCourseStudentMock();
        String body = objectMapper.writeValueAsString(mockCourseStudent);
        doReturn(mockCourseStudent)
                .when(courseStudentService).enrollStudentToCourse(anyString(), any(CourseStudent.class));

        mvc.perform(post("/course/{courseId}/students", "randomCourseId")
                .contentType(APPLICATION_JSON)
                .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseStudentId", is("randomCourseStudentId")))
                .andExpect(jsonPath("$.courseId", is("randomCourseId")))
                .andExpect(jsonPath("$.studentId", is("randomStudentId")))
                .andExpect(jsonPath("$.grade", is(12)));

        verify(courseStudentService, times(1)).enrollStudentToCourse(anyString(), any(CourseStudent.class));
    }

    private CourseStudent getCourseStudentMock() {
        return CourseStudent.builder()
                .courseStudentId("randomCourseStudentId")
                .courseId("randomCourseId")
                .studentId("randomStudentId")
                .grade(12)
                .build();
    }


}