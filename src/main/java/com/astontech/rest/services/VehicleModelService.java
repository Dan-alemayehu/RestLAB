package com.astontech.rest.services;

import com.astontech.rest.domain.VehicleModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VehicleModelService {

    VehicleModel saveVehicleModel(Integer makeId, VehicleModel vehicleModel);

    VehicleModel findVehicleModelByMakeAndId(Integer makeId, Integer id);

    VehicleModel updateVehicleModel(Integer makeId, VehicleModel vehicleModel);

    VehicleModel patchVehicleModel(Integer makeId, Map<String, Object> updates, Integer id);

    void deleteVehicleModelById(Integer makeId, Integer id);

    List<VehicleModel> findAllVehicleModelsByMake(Integer makeId);
}

