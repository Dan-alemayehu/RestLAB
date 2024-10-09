package com.astontech.rest.repositories;

import com.astontech.rest.domain.VehicleMake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleMakeRepository extends JpaRepository<VehicleMake, Integer> {
    Optional<VehicleMake> findByVehicleMakeName(String vehicleMakeName);
}
