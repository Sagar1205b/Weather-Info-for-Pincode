package com.sagar.Weather_Info_for_Pincode.repository;

import com.sagar.Weather_Info_for_Pincode.model.Pincode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PincodeRepository extends JpaRepository<Pincode,Integer> {
}
