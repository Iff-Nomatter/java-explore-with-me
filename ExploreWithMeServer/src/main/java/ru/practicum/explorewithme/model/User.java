package ru.practicum.explorewithme.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "User")
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Column(name = "id", updatable = false, unique = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
}
