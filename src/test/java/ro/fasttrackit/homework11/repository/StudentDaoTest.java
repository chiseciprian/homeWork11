//package ro.fasttrackit.homework11.repository;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import ro.fasttrackit.homework11.model.StudentFilters;
//import ro.fasttrackit.homework11.model.entity.Student;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataMongoTest
//class StudentDaoTest {
//    @Autowired
//    private StudentDao studentDao;
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Test
//    void getAll() {
//        mongoTemplate.save(new Student("randomStudentId1", "randomName", 1234));
//        StudentFilters studentFilters = new StudentFilters("randomName", null, null, null, null);
//        studentDao.getAll(studentFilters);
//
//        Criteria criteriaMock = new Criteria();
//        criteriaMock.and("name").is("randomName");
//        Query queryMock = new Query(criteriaMock);
//        List<Student> result = mongoTemplate.find(queryMock, Student.class);
//        assertThat(result.get(0)).extracting("name", "age")
//                .containsExactly("randomName", 1234);
//    }
//}