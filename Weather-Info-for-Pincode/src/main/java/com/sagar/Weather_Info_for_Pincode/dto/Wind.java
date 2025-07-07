package com.sagar.Weather_Info_for_Pincode.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wind {

    private double speed;
    private int deg;
}
