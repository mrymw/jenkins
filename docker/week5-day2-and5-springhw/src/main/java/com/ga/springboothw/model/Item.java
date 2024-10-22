package com.ga.springboothw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "items")
public class Item {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column
    @Getter @Setter private String name;

    @Column
    @Getter @Setter private String description;

    @Column
    @Getter @Setter private Integer dueDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    @Getter @Setter private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter private User user;
    //orm - object
    //jpa - java persistence api

}

