package com.astontech.rest.services.impl;

import com.astontech.rest.domain.VehicleModel;
import com.astontech.rest.exceptions.VehicleModelAlreadyExistsException;
import com.astontech.rest.exceptions.VehicleModelNotFoundException;
import com.astontech.rest.repositories.VehicleMakeRepository;
import com.astontech.rest.repositories.VehicleModelRepository;
import com.astontech.rest.services.VehicleModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleModelServiceImpl implements VehicleModelService {

    private VehicleModelRepository vehicleModelRepository;

    @Autowired
    public VehicleModelServiceImpl(VehicleModelRepository vehicleModelRepository) {
        this.vehicleModelRepository = vehicleModelRepository;
    }

    @Override
    public VehicleModel saveVehicleModel(VehicleModel vehicleModel) {
        Optional<VehicleModel> existingModel = vehicleModelRepository.findByModelName(vehicleModel.getModelName());
        if (existingModel.isPresent()) {
            throw new VehicleModelAlreadyExistsException(vehicleModel.getModelName());
        }
        return vehicleModelRepository.save(vehicleModel);
    }

    @Override
    public VehicleModel findVehicleModelById(Integer id) {
            return vehicleModelRepository.findById(id)
                    .orElseThrow(() -> new VehicleModelNotFoundException(id.toString()));
    }

    @Override
    public VehicleModel updateVehicleModel(VehicleModel vehicleModel) {
        return vehicleModelRepository.save(vehicleModel);
    }

    @Override
    public void deleteVehicleModelById(Integer id) {
        vehicleModelRepository.deleteById(id);
    }

    @Override
    public List<VehicleModel> findAllVehicleModels() {
        return vehicleModelRepository.findAll();
    }
}
