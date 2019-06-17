package com.codecool.compiluserrorus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @CreationTimestamp
    private LocalDate postingDate;

    @Transient
    private String romanDate;

    private Integer likes = 0;

    private Integer dislikes = 0;

    private String image;

    @Transient
    private String username;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    @Singular
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
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

    public void modifyData() {
        this.username = this.member.getName();
        LocalDate changedPosting = postingDate.minusYears(1960);
        this.romanDate = changedPosting.format(DateTimeFormatter.ofPattern("yy-MM-dd"));
    }
}
