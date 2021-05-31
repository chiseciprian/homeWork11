package ro.fasttrackit.homework11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ro.fasttrackit.homework11.Homework11Application;
import ro.fasttrackit.homework11.config.TestBeans;
import ro.fasttrackit.homework11.model.StudentCourse;
import ro.fasttrackit.homework11.model.entity.Student;
import ro.fasttrackit.homework11.service.StudentService;

import java.util.ArrayList;
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
class StudentControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private StudentService studentService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAll() throws Exception {
        doReturn(List.of(
                new Student("randomId1", "randomName", 20)
        )).when(studentService).getAll(any());

        mvc.perform(get("/students"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name", is("randomName")))
                .andExpect(jsonPath("$[0].age", is(20)));

        verify(studentService, times(1)).getAll(any());
    }

    @Test
    void getStudent() throws Exception {
        doReturn(Optional.of(new Student("randomId1", "randomName", 20)))
                .when(studentService).getStudent(anyString());

        mvc.perform(get("/students/{studentId}", "randomId1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("randomName")))
                .andExpect(jsonPath("$.age", is(20)));

        verify(studentService, times(1)).getStudent(anyString());
    }

    @Test
    void getStudentExpectEntityNotFoundException() throws Exception {
        doReturn(Optional.empty())
                .when(studentService).getStudent(anyString());

        mvc.perform(get("/students/{studentId}", "randomId1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getStudentCourses() throws Exception {
        List<StudentCourse> studentCourses = getStudentCoursesMock();
        doReturn(studentCourses)
                .when(studentService).getStudentCourses(anyString());

        mvc.perform(get("/students/{studentId}/courses", "randomStudentId"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name", is("randomName")))
                .andExpect(jsonPath("$[0].discipline", is("randomDiscipline")))
                .andExpect(jsonPath("$[0].grade", is(10)))
                .andExpect(jsonPath("$[0].age", is(12)));

        verify(studentService, times(1)).getStudentCourses(anyString());
    }

    @Test
    void addStudent() throws Exception {
        Student mockStudent = new Student("randomId1", "randomName", 20);
        String body = objectMapper.writeValueAsString(mockStudent);
        doReturn(mockStudent)
                .when(studentService).addStudent(any(Student.class));

        mvc.perform(post("/students")
                .contentType(APPLICATION_JSON)
                .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("randomName")))
                .andExpect(jsonPath("$.age", is(20)));

        verify(studentService, times(1)).addStudent(any(Student.class));
    }

    private List<StudentCourse> getStudentCoursesMock() {
        List<StudentCourse> studentCourseList = new ArrayList<>();
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setName("randomName");
        studentCourse.setAge(12);
        studentCourse.setDiscipline("randomDiscipline");
        studentCourse.setGrade(10);
        studentCourseList.add(studentCourse);
        return studentCourseList;
    }


}