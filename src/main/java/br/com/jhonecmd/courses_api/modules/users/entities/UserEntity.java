package br.com.jhonecmd.courses_api.modules.users.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Email(message = "The email field is invalid.")
    @Column(unique = true, nullable = false)
    private String email;

    @Length(min = 8, max = 100, message = "The password length must be between 10 and 100 characters.")
    private String password;

    @Enumerated(EnumType.STRING)
    private Position position;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum Position {
        rector, director, coordinator, professor, student
    }

    @JsonCreator
    public static Position fromValue(String value) {
        if (value == null) {
            return null;
        }

        try {
            return Position.valueOf(value.toLowerCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Invalid Value" + "\nThe valid values are: 'rector, director, coordinator, professor, student'");
        }
    }
}
