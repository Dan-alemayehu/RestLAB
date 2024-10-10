package com.astontech.rest.services.impl;

import com.astontech.rest.domain.VehicleMake;
import com.astontech.rest.domain.VehicleModel;
import com.astontech.rest.exceptions.FieldNotFoundException;
import com.astontech.rest.exceptions.VehicleMakeNotFoundException;
import com.astontech.rest.exceptions.VehicleModelAlreadyExistsException;
import com.astontech.rest.exceptions.VehicleModelNotFoundException;
import com.astontech.rest.repositories.VehicleMakeRepository;
import com.astontech.rest.repositories.VehicleModelRepository;
import com.astontech.rest.services.VehicleModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VehicleModelServiceImpl implements VehicleModelService {

    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleMakeRepository vehicleMakeRepository;

    @Autowired
    public VehicleModelServiceImpl(VehicleModelRepository vehicleModelRepository, VehicleMakeRepository vehicleMakeRepository) {
        this.vehicleModelRepository = vehicleModelRepository;
        this.vehicleMakeRepository = vehicleMakeRepository;
    }

    @Override
    public List<VehicleModel> findAllVehicleModelsByMake(Integer makeId) {
        VehicleMake vehicleMake = vehicleMakeRepository.findById(makeId)
                .orElseThrow(() -> new VehicleMakeNotFoundException(makeId.toString()));
        return vehicleMake.getVehicleModelList();
    }

    @Override
    @Cacheable(value = "vehicleModels", key = "#id")
    public VehicleModel findVehicleModelByMakeAndId(Integer makeId, Integer id) {
        VehicleMake vehicleMake = vehicleMakeRepository.findById(makeId)
                .orElseThrow(() -> new VehicleMakeNotFoundException(makeId.toString()));
        return vehicleMake.getVehicleModelList().stream()
                .filter(vehicleModel -> vehicleModel.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new VehicleModelNotFoundException(id.toString()));
    }

    @Override
    public VehicleModel saveVehicleModel(Integer makeId, VehicleModel vehicleModel) {
        VehicleMake vehicleMake = vehicleMakeRepository.findById(makeId)
                .orElseThrow(() -> new VehicleMakeNotFoundException(makeId.toString()));

        Optional<VehicleModel> existingModel = vehicleModelRepository.findByModelName(vehicleModel.getModelName());
        if (existingModel.isPresent()) {
            throw new VehicleModelAlreadyExistsException(vehicleModel.getModelName());
        }

        vehicleMake.getVehicleModelList().add(vehicleModel);
        vehicleMakeRepository.save(vehicleMake);

        return vehicleModel;
    }

    @Override
    @CacheEvict(value = "vehicleModels", key = "#vehicleModel.id")
    public VehicleModel updateVehicleModel(Integer makeId, VehicleModel vehicleModel) {
        VehicleMake vehicleMake = vehicleMakeRepository.findById(makeId)
                .orElseThrow(() -> new VehicleMakeNotFoundException(makeId.toString()));

        VehicleModel existingModel = vehicleModelRepository.findById(vehicleModel.getId())
                .orElseThrow(() -> new VehicleModelNotFoundException(vehicleModel.getId().toString()));

        // Update the fields on the existing vehicle model
        existingModel.setModelName(vehicleModel.getModelName());

        vehicleMakeRepository.save(vehicleMake);
        return existingModel;
    }

    @Override
    @CacheEvict(value = "vehicleModels", key = "#id")
    public VehicleModel patchVehicleModel(Integer makeId, Map<String, Object> updates, Integer id) throws FieldNotFoundException {
        VehicleModel vehicleModelPatch = findVehicleModelByMakeAndId(makeId, id);

        updates.forEach((fieldName, fieldValue) -> {
            try {
                Field field = VehicleModel.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(vehicleModelPatch, fieldValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new FieldNotFoundException(fieldName);
            }
        });

        return vehicleModelRepository.save(vehicleModelPatch);
    }

    @Override
    @CacheEvict(value = "vehicleModels", key = "#id")
    public void deleteVehicleModelById(Integer makeId, Integer id) {
        VehicleMake vehicleMake = vehicleMakeRepository.findById(makeId)
                .orElseThrow(() -> new VehicleMakeNotFoundException(makeId.toString()));
        vehicleMake.getVehicleModelList().removeIf(model -> model.getId().equals(id));
        vehicleMakeRepository.save(vehicleMake);
    }
}

