package com.astontech.rest.services;

import com.astontech.rest.domain.VehicleModel;

import java.util.List;
import java.util.Optional;

public interface VehicleModelService {

    VehicleModel saveVehicleModel (VehicleModel vehicleModel);

    VehicleModel findVehicleModelById(Integer id);

    VehicleModel updateVehicleModel(VehicleModel vehicleModel);

    void deleteVehicleModelById(Integer id);

    List<VehicleModel> findAllVehicleModels();
}
