package org.qnu.cpl.collaborativepersonalizedlearningbe.entity;

import jakarta.persistence.*;
import lombok.*;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Gender;
import org.qnu.cpl.collaborativepersonalizedlearningbe.enums.Role;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @Column(name = "user_id", columnDefinition = "CHAR(36)")
    private String userId;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "avatar_object")
    private String avatarObject;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "gender", length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VerificationCode> verificationCodes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LearningPath> learningPaths;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tag> tags;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Note> notes;

}
