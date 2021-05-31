package ro.fasttrackit.homework11.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ro.fasttrackit.homework11.model.entity.Course;
import ro.fasttrackit.homework11.model.entity.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository repository;

    @Test
    void findByStudentId() {
        repository.saveAll(List.of(
                new Student("randomStudentId1", "randomName",1234)
        ));

        Student result = repository.findByStudentId("randomStudentId1");

        assertEquals(result.getName(), "randomName");
        assertEquals(result.getAge(), 1234);
    }
}