package com.astontech.rest.repositories;

import com.astontech.rest.domain.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleModelRepository extends JpaRepository<VehicleModel, Integer> {
}
