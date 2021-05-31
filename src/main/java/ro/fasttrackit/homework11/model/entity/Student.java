package ro.fasttrackit.homework11.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "student")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    private String studentId;
    @NonNull
    private String name;
    @NonNull
    private int age;
}
