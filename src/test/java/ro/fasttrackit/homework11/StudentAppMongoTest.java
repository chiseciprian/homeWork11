package ro.fasttrackit.homework11;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ro.fasttrackit.homework11.model.entity.Course;
import ro.fasttrackit.homework11.repository.CourseRepository;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(initializers = StudentAppMongoTest.Initializer.class)
public class StudentAppMongoTest {
    @Container
    private static MongoDBContainer mongoDb = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    static {
        mongoDb.start();
    }

    @Autowired
    private MockMvc mvc;
    @Autowired
    private CourseRepository repository;

    @Test
    void getAllTest() throws Exception {
        repository.saveAll(List.of(
                new Course("randomCourseId1", "randomDiscipline1"),
                new Course("randomCourseId2", "randomDiscipline2")
        ));

        mvc.perform(get("/courses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].discipline", is("randomDiscipline1")))
                .andExpect(jsonPath("$[1].discipline", is("randomDiscipline2")));
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    String.format("spring.data.mongodb.uri: %s", mongoDb.getReplicaSetUrl())
            ).applyTo(configurableApplicationContext);
        }

    }
}
