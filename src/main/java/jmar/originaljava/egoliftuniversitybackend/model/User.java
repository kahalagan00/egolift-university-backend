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
@Table(name = "user_account")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Column(length = 50, nullable = false)
    private String email;

    // Hash it later
    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 15)
    private String phone;

    @Column(length = 50)
    private String address;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String country;

    @Column(length = 50)
    private LocalDate birthDate;

    @Column()
    private float height;

    @Column()
    private float weight;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise favoriteExercise;
//
//    @OneToMany(mappedBy = "user")
//    private Set<Workout> workouts;
}
