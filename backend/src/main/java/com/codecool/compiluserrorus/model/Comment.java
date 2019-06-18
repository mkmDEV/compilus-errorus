package com.codecool.compiluserrorus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @CreationTimestamp
    private LocalDate postingDate;

    private Integer likes = 0;

    private Integer dislikes = 0;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Post post;

    @Transient
    private String romanDate;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", postingDate=" + postingDate +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                '}';
    }
}
