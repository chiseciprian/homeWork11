package ro.fasttrackit.homework11.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.homework11.model.entity.CourseStudent;

import java.util.List;

@Repository
public interface CourseStudentRepository extends MongoRepository<CourseStudent, String> {
    List<CourseStudent> findAllByCourseId(String courseId);
}
