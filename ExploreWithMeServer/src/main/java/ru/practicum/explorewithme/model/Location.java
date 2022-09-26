package ru.practicum.explorewithme.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Location")
@Table(name = "locations")
public class Location {
    @Id
    @SequenceGenerator(name = "location_sequence", sequenceName = "locations_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_sequence")
    @Column(name = "id", updatable = false, unique = true)
    private int id;
    @Column(name = "latitude")
    private float latitude;
    @Column(name = "longitude")
    private float longitude;
}
