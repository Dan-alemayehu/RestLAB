package com.astontech.rest.repositories;

import com.astontech.rest.domain.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleModelRepository extends JpaRepository<VehicleModel, Integer> {
    Optional<VehicleModel> findByModelName(String modelName);
}
