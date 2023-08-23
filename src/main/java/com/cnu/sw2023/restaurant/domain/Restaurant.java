package com.cnu.sw2023.restaurant.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Restaurant implements Serializable { // Serializable 는 entity의 fk 가 pk가 아닐경우 구현해줘야함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "restaurant_id")
    private String restaurantId;
}
