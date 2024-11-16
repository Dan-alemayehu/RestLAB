package com.astontech.rest.repositories;

import com.astontech.rest.domain.VehicleMake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VehicleMakeRepository extends JpaRepository<VehicleMake, Integer> {
    Optional<VehicleMake> findByVehicleMakeName(String vehicleMakeName);

    @Query("SELECT vm FROM VehicleMake vm LEFT JOIN FETCH vm.vehicleModelList WHERE vm.id = :id")
    Optional<VehicleMake> findByIdWithModels(@Param("id") Integer id);

}
