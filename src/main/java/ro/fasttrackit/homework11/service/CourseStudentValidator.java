package ro.fasttrackit.homework11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.homework11.controller.exception.ValidationException;
import ro.fasttrackit.homework11.model.entity.CourseStudent;
import ro.fasttrackit.homework11.repository.CourseRepository;
import ro.fasttrackit.homework11.repository.StudentRepository;

import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class CourseStudentValidator {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public void validateCourseStudentOrThrow(CourseStudent courseStudent) {
        existStudentId(courseStudent.getStudentId())
                .ifPresent(ex -> {
                    throw ex;
                });
        existCourseId(courseStudent.getCourseId())
                .ifPresent(ex -> {
                    throw ex;
                });
    }

    private Optional<ValidationException> existCourseId(String courseId) {
        return courseRepository.existsById(courseId)
                ? empty()
                : Optional.of(new ValidationException("Course with id " + courseId + " doesn't exist"));
    }

    private Optional<ValidationException> existStudentId(String studentId) {
        return studentRepository.existsById(studentId)
                ? empty()
                : Optional.of(new ValidationException("Student with id " + studentId + " doesn't exist"));
    }
}
