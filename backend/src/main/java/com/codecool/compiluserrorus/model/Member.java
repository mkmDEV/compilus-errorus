package com.codecool.compiluserrorus.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Member {

    @TableGenerator(name = "Member_Gen", initialValue = 5)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Member_Gen")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @CreationTimestamp
    private LocalDate regDate;

    @Singular
    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonBackReference(value = "posts")
    @EqualsAndHashCode.Exclude
    private Set<Post> posts;

    @Singular
    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonBackReference(value = "comments")
    @EqualsAndHashCode.Exclude
    private Set<Comment> comments;

    @Singular
    @ManyToMany(mappedBy = "participants", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonBackReference(value = "events")
    @EqualsAndHashCode.Exclude
    private Set<Event> events;

    @Singular
    @EqualsAndHashCode.Exclude
    @Fetch(FetchMode.JOIN)
    @JsonBackReference(value = "member")
    @ManyToMany
    private Set<Member> friends;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<String> roles = new HashSet<>();

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
