package jmar.originaljava.egoliftuniversitybackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Column(length = 255, nullable = false)
    private String email;

    // Hash it later
    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 15, nullable = true)
    private String phone;

    @Column(length = 255)
    private String address;

    @Column(length = 255)
    private String city;

    @Column(length = 255)
    private String country;

    @Column(length = 255)
    private LocalDate birthDate;

    @Column(nullable = false)
    private float height;

    @Column(nullable = false)
    private float weight;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise favoriteExercise;
//
//    @OneToMany(mappedBy = "user")
//    private Set<Workout> workouts;
}
