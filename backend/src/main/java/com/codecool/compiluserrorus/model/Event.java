package com.codecool.compiluserrorus.model;

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

    @ManyToOne
    @Column(nullable = false)
    private Member creator;
}
