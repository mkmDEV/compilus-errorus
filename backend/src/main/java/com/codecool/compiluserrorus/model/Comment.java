package com.codecool.compiluserrorus.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Comment {

    @TableGenerator(name = "Comment_Gen", initialValue = 4)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Comment_Gen")
    private Long id;

    @Column(nullable = false)
    private String message;

    @CreationTimestamp
    private LocalDateTime postingDate;

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
