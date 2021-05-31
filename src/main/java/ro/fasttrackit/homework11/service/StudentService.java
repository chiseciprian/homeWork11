package ro.fasttrackit.homework11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.homework11.controller.exception.EntityNotFoundException;
import ro.fasttrackit.homework11.model.StudentCourse;
import ro.fasttrackit.homework11.model.StudentFilters;
import ro.fasttrackit.homework11.model.entity.CourseStudent;
import ro.fasttrackit.homework11.model.entity.Student;
import ro.fasttrackit.homework11.repository.CourseRepository;
import ro.fasttrackit.homework11.repository.CourseStudentRepository;
import ro.fasttrackit.homework11.repository.StudentDao;
import ro.fasttrackit.homework11.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentDao studentDao;
    private final StudentRepository studentRepository;
    private final CourseStudentRepository courseStudentRepository;
    private final CourseRepository courseRepository;

    public List<Student> getAll(StudentFilters filters) {
        return studentDao.getAll(filters);
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> getStudent(String studentId) {
        return studentRepository.findById(studentId);
    }

    public List<StudentCourse> getStudentCourses(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with id " + studentId + " not found"));
        List<CourseStudent> courseStudentList = courseStudentRepository.findAll();
        return courseStudentList.stream()
                .filter(courseStudent -> courseStudent.getStudentId().equals(studentId))
                .map(courseStudent -> createStudentCourse(courseStudent, student))
                .collect(Collectors.toList());
    }

    private StudentCourse createStudentCourse(CourseStudent courseStudent, Student student) {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setName(student.getName());
        studentCourse.setAge(student.getAge());
        studentCourse.setDiscipline(getDiscipline(courseStudent.getCourseId()));
        studentCourse.setGrade(courseStudent.getGrade());
        return studentCourse;
    }

    private String getDiscipline(String courseId) {
        return courseRepository.findByCourseId(courseId).getDiscipline();
    }
}
