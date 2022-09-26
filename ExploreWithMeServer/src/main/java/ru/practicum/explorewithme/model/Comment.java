package ru.practicum.explorewithme.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "Comment")
@Table(name = "comments")
public class Comment {
    @Id
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comments_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    @Column(name = "id", updatable = false, unique = true)
    private int id;
    @Column(name = "content")
    private String content;
    @Column(name = "created")
    private LocalDateTime createdOn;
    @Column(name = "is_edited")
    private boolean isEdited;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;
}
