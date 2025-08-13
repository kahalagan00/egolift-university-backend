package jmar.originaljava.egoliftuniversitybackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Workout {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Column(length = 50, nullable = false)
    private String workoutName;

    @OneToMany(
            mappedBy = "workout",
            cascade = CascadeType.ALL, // What does this do?
            orphanRemoval = true, // What does this do?
            fetch = FetchType.EAGER
    )
    private List<Exercise> exercises = new ArrayList<>();

}
