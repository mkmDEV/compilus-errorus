package com.codecool.compiluserrorus.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventTitle;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate eventDate;

    @Singular
    @ElementCollection
    @EqualsAndHashCode.Exclude
    private Set<Member> participants;

    @JsonManagedReference
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
