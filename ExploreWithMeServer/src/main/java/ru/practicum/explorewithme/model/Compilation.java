package ru.practicum.explorewithme.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "Compilation")
@Table(name = "compilations")
public class Compilation {
    @Id
    @SequenceGenerator(name = "compilation_sequence", sequenceName = "compilations_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compilation_sequence")
    @Column(name = "id", updatable = false, unique = true)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "is_pinned")
    private boolean isPinned;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "compilations_events", joinColumns = {@JoinColumn(name = "compilation_id")}, inverseJoinColumns = {@JoinColumn(name = "event_id")})
    private List<Event> events = new ArrayList<>();
}
