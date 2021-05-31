package ro.fasttrackit.homework11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.homework11.controller.exception.EntityNotFoundException;
import ro.fasttrackit.homework11.model.StudentCourse;
import ro.fasttrackit.homework11.model.StudentFilters;
import ro.fasttrackit.homework11.model.entity.Student;
import ro.fasttrackit.homework11.service.StudentService;


import java.util.List;

@RestController
@RequestMapping("students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    List<Student> getAll(StudentFilters filters) {
        return studentService.getAll(filters);
    }

    @GetMapping(path = "{studentId}")
    Student getStudent(@PathVariable String studentId) {
        return studentService.getStudent(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with id " + studentId + " not found"));
    }

    @GetMapping(path = "{studentId}/courses")
    List<StudentCourse> getStudentCourses(@PathVariable String studentId){
        return studentService.getStudentCourses(studentId);
    }

    @PostMapping
    Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }
}
