package com.codecool.compiluserrorus.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String message;

    @CreationTimestamp
    private LocalDateTime postingDate;

    @Transient
    private String romanDate;

    private Integer likes = 0;

    private Integer dislikes = 0;

    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    @Singular
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Set<Comment> comments;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PostType postType;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", postingDate=" + postingDate +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", image='" + image + '\'' +
                '}';
    }
}
