package com.codecool.compiluserrorus.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @CreationTimestamp
    private LocalDate regDate;

    @Singular
    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Set<Post> posts;

    @Singular
    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Set<Comment> comments;

    @Singular
    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Set<Comment> events;

    @Singular
    @ElementCollection
    @EqualsAndHashCode.Exclude
    private Set<Member> friends;

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}