package com.astontech.rest.services;

import com.astontech.rest.domain.Vehicle;
import com.astontech.rest.domain.VehicleMake;
import com.astontech.rest.repositories.VehicleModelRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VehicleMakeService {

    VehicleMake saveVehicleMake (VehicleMake vehicleMake);

    VehicleMake findVehicleMakeById(Integer id);

    VehicleMake updateVehicleMake(VehicleMake vehicleMake);

    VehicleMake patchVehicleMake(Map<String, Object> updates, Integer id);

    void deleteVehicleMake(Integer id);

    List<VehicleMake> findAllVehicleMakes();
}
