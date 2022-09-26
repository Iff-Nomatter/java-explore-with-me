package ru.practicum.explorewithme.model;

import lombok.Data;
import ru.practicum.explorewithme.model.enumerations.EventState;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "Event")
@Table(name = "events")
public class Event {
    @Id
    @SequenceGenerator(name = "event_sequence", sequenceName = "events_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_sequence")
    @Column(name = "id", updatable = false, unique = true)
    private int id;
    @Column(name = "annotation")
    private String annotation;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "created")
    private LocalDateTime createdOn;
    @Column(name = "description")
    private String description;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    private User initiator;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;
    @Column(name = "is_paid")
    private Boolean isPaid;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private EventState state;
    @Column(name = "title")
    private String title;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "event")
    private List<Comment> comments;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "event")
    private List<Request> requests;
}
