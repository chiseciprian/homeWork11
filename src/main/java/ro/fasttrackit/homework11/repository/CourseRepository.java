package ro.fasttrackit.homework11.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.homework11.model.entity.Course;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    Course findByCourseId(String courseId);
}
