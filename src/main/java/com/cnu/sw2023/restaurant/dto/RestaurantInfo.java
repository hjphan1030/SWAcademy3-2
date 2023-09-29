package com.cnu.sw2023.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @Builder
public class RestaurantInfo {
    String address;
    String phone;
    Double rating;
}