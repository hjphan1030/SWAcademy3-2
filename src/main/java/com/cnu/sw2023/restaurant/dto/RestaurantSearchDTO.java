package com.cnu.sw2023.restaurant.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RestaurantSearchDTO {
    public String category;
    public String region;
    //public String college;
    //public String event;
}
