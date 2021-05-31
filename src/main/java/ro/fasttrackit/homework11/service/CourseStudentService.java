package ro.fasttrackit.homework11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.homework11.model.entity.CourseStudent;
import ro.fasttrackit.homework11.model.entity.Student;
import ro.fasttrackit.homework11.repository.CourseStudentRepository;
import ro.fasttrackit.homework11.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseStudentService {
    private final CourseStudentRepository courseStudentRepository;
    private final StudentRepository studentRepository;
    private final CourseStudentValidator validator;

    public CourseStudent enrollStudentToCourse(String courseId, CourseStudent courseStudent) {
        courseStudent.setCourseId(courseId);
        validator.validateCourseStudentOrThrow(courseStudent);
        return courseStudentRepository.save(courseStudent);
    }

    public List<Student> studentsByCourse(String courseId) {
        List<CourseStudent> studentsByCourseId = courseStudentRepository.findAllByCourseId(courseId);
        return studentsByCourseId.stream()
                .map(student -> studentRepository.findByStudentId(student.getStudentId()))
                .collect(Collectors.toList());
    }
}
