package com.sagar.Weather_Info_for_Pincode.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pincode")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Pincode{

    @Id
    private Integer pincode;

    private double latitude;

    private double longitude;


}

