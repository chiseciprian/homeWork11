package ro.fasttrackit.homework11.model;

import lombok.Value;

@Value
public class StudentFilters {
    String name;
    String studentId;
    Integer age;
    Integer minAge;
    Integer maxAge;
}
