package com.astontech.rest.services;

import com.astontech.rest.domain.Vehicle;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VehicleService {

    Vehicle saveVehicle(Integer modelId, Vehicle vehicle);

    Vehicle findVehicleById(Integer id);

    Vehicle updateVehicle(Integer modelId, Vehicle vehicle);

    Vehicle patchVehicle(Map<String, Object> updates, Integer id);

    void deleteVehicle(Integer id);

    List<Vehicle> findAllVehicles();
}
