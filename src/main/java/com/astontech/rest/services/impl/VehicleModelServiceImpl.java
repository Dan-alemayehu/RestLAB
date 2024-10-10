package com.astontech.rest.services.impl;

import com.astontech.rest.domain.VehicleModel;
import com.astontech.rest.exceptions.FieldNotFoundException;
import com.astontech.rest.exceptions.VehicleModelAlreadyExistsException;
import com.astontech.rest.exceptions.VehicleModelNotFoundException;
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

    private VehicleModelRepository vehicleModelRepository;

    @Autowired
    public VehicleModelServiceImpl(VehicleModelRepository vehicleModelRepository) {
        this.vehicleModelRepository = vehicleModelRepository;
    }

    @Override
    public List<VehicleModel> findAllVehicleModels() {
        return vehicleModelRepository.findAll();
    }

    @Override
    @Cacheable(value = "vehicleModels", key = "#id")
    public VehicleModel findVehicleModelById(Integer id) {
        return vehicleModelRepository.findById(id)
                .orElseThrow(() -> new VehicleModelNotFoundException(id.toString()));
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
    @CacheEvict(value = "vehicleModels", key = "#vehicleModel.id")
    public VehicleModel updateVehicleModel(VehicleModel vehicleModel) {
        VehicleModel existingModel = vehicleModelRepository.findById(vehicleModel.getId())
                .orElseThrow(() -> new VehicleModelNotFoundException(vehicleModel.getId().toString()));

        //Update the existingModel fields with the new values
        existingModel.setModelName(vehicleModel.getModelName());

        //Save the updated entity
        VehicleModel savedModel = vehicleModelRepository.save(existingModel);
        System.out.println("Saved model: " + savedModel.getModelName());
        return savedModel;
    }

    //Patch Method: Change a field in the method
    @Override
    @CacheEvict(value = "vehicleModels", key = "#id")
    public VehicleModel patchVehicleModel(Map<String, Object> updates, Integer id) throws FieldNotFoundException {
        VehicleModel vehicleModelPatch = vehicleModelRepository.findById(id)
                .orElseThrow(() -> new VehicleModelNotFoundException(id.toString()));
        updates.forEach((fieldName, fieldValue) -> {
            try{
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
    public void deleteVehicleModelById(Integer id) {
        vehicleModelRepository.deleteById(id);
    }

}
