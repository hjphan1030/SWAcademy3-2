package com.cnu.sw2023.restaurant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Restaurant implements Serializable { // Serializable 는 entity의 fk 가 pk가 아닐경우 구현해줘야함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurantName")
    private String restaurantName;

    @Column(name = "restaurantId")
    private String restaurantId;

    @Column(name = "addressName")
    private String addressName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "category")
    private String category;

    @Column(name = "region")
    private String region;

    public Restaurant() {
    }

}
