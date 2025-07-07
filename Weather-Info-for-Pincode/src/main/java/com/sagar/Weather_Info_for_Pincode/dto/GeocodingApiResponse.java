package com.sagar.Weather_Info_for_Pincode.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeocodingApiResponse {

    private String zip;
    private transient String name;
    private double lat;
    private double lon;
    private transient String country;
}
