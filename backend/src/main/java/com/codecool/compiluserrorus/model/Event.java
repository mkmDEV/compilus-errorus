package com.codecool.compiluserrorus.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Event {

    @TableGenerator(name = "Event_Gen", initialValue = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Event_Gen")
    private Long id;

    @Column(nullable = false)
    private String eventTitle;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    @Transient
    private String romanDate;

    @Singular
    @ElementCollection
    @EqualsAndHashCode.Exclude
    private Set<Member> participants;

    @ManyToOne
    private Member creator;

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventTitle='" + eventTitle + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                '}';
    }
}
