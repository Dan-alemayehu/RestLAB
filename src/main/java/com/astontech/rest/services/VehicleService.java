package com.astontech.rest.services;

import com.astontech.rest.domain.Vehicle;

import java.util.List;
import java.util.Map;

public interface VehicleService {

    Vehicle saveVehicle(Integer makeId, Integer modelId, Vehicle vehicle);  // Add makeId, modelId

    Vehicle findVehicleById(Integer makeId, Integer modelId, Integer id);  // Add makeId, modelId

    Vehicle updateVehicle(Integer makeId, Integer modelId, Vehicle vehicle);  // Add makeId, modelId

    Vehicle patchVehicle(Integer makeId, Integer modelId, Map<String, Object> updates, Integer id);  // Add makeId, modelId

    void deleteVehicle(Integer makeId, Integer modelId, Integer id);  // Add makeId, modelId

    List<Vehicle> findAllVehicles(Integer makeId, Integer modelId);  // Add makeId, modelId
}