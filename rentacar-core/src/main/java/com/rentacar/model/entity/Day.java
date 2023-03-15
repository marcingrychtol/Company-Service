package com.rentacar.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Additional Entity made to manage overlapping problem.
 * Object stores id's of all reservations that occur in that day.
 * It isn't necessary for reservation to start or end in that particular day.
 *
 * Example:
 * User creates reservation that starts 01-01-2020r and ends 31-12-2020r.
 * Service's logic will add ID of this reservation to list of Day object,
 * if that reservation will occur in this particular day, which means that
 * all 356 Day objects from 2020r will contain that reservation ID on its list.
 *
 * When other user will make query for reservation for example in 02-02-2020r.
 * service at first will ask database for Day object, retrieve all its reservation
 * and then ask database for reservation objects of that list.
 *
 * Brilliant.
 *
 * Warning - id is not generated automaticly
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// needed due to initialization error, "day" is reserved keyword
@Table(name = "\"DAY\"")
public class Day {

    @Id
    private LocalDate id;

    /**
     * Used to store reservation list.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "day_trip",
            joinColumns = @JoinColumn(name = "day_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id")
    )
    private List<Trip> trips;

    public Day(LocalDate date){
        this.id = date;
        trips = new ArrayList<>();
    }
}
