package com.astontech.rest.repositories;

import com.astontech.rest.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}
