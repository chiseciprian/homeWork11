package ro.fasttrackit.homework11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.homework11.model.entity.CourseStudent;
import ro.fasttrackit.homework11.model.entity.Student;
import ro.fasttrackit.homework11.service.CourseStudentService;

import java.util.List;

@RestController
@RequestMapping("course")
@RequiredArgsConstructor
public class CourseStudentController {
    private final CourseStudentService courseStudentService;

    @GetMapping(path = "{courseId}/students")
    List<Student> studentsByCourse(@PathVariable String courseId) {
        return courseStudentService.studentsByCourse(courseId);
    }

    @PostMapping(path = "{courseId}/students")
    CourseStudent enrollStudentToCourse(@PathVariable String courseId, @RequestBody CourseStudent courseStudent) {
        return courseStudentService.enrollStudentToCourse(courseId, courseStudent);
    }
}
