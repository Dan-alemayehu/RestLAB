package com.astontech.rest.services;

import com.astontech.rest.domain.VehicleMake;
import com.astontech.rest.repositories.VehicleModelRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleMakeService {

    VehicleMake saveVehicleMake (VehicleMake vehicleMake);

    VehicleMake findVehicleMakeById(Integer id);

    VehicleMake updateVehicleMake(VehicleMake vehicleMake);

    void deleteVehicleMake(Integer id);

    List<VehicleMake> findAllVehicleMakes();
}
