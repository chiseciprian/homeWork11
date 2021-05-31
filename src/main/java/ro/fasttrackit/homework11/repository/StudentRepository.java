package ro.fasttrackit.homework11.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.homework11.model.entity.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    Student findByStudentId(String studentId);
}
