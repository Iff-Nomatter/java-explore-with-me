package ru.practicum.explorewithme.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Category")
@Table(name = "categories")
public class Category {
    @Id
    @SequenceGenerator(name = "category_sequence", sequenceName = "categories_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @Column(name = "id", updatable = false, unique = true)
    private int id;
    @Column(name = "name")
    private String name;
}
