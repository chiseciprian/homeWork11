package ro.fasttrackit.homework11.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ro.fasttrackit.homework11.model.entity.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
class CourseRepositoryTest {
    @Autowired
    private CourseRepository repository;

    @Test
    void findByCourseId() {
        repository.saveAll(List.of(
                new Course("randomCourseId1", "History"),
                new Course("randomCourseId2", "Biology")
        ));

        Course result = repository.findByCourseId("randomCourseId1");

        assertEquals(result.getDiscipline(), "History");
    }
}