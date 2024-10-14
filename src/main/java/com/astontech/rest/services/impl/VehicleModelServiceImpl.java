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

    private final VehicleMakeRepository vehicleMakeRepository;
    private VehicleModelRepository vehicleModelRepository;

    @Autowired
    public VehicleModelServiceImpl(VehicleModelRepository vehicleModelRepository, VehicleMakeRepository vehicleMakeRepository) {
        this.vehicleModelRepository = vehicleModelRepository;
        this.vehicleMakeRepository = vehicleMakeRepository;
    }

    @Override
//    @Cacheable(value = "vehicleModels")
    public List<VehicleModel> findAllVehicleModels() {
        return vehicleModelRepository.findAll();
    }

    @Override
//    @Cacheable(value = "vehicleModels", key = "#id")
    public VehicleModel findVehicleModelById(Integer id) {
        return vehicleModelRepository.findById(id)
                .orElseThrow(() -> new VehicleModelNotFoundException(id.toString()));
    }

    @Override
    public VehicleModel saveVehicleModel(Integer makeId, VehicleModel vehicleModel) {
        Optional<VehicleMake> vehicleMake = vehicleMakeRepository.findById(makeId);
        if (vehicleMake.isEmpty()) {
            throw new VehicleMakeNotFoundException(makeId.toString());
        }
        Optional<VehicleModel> existingModel = vehicleModelRepository.findByModelName(vehicleModel.getModelName());
        if (existingModel.isPresent()) {
            throw new VehicleModelAlreadyExistsException(vehicleModel.getModelName());
        }
        vehicleMake.get().getVehicleModelList().add(vehicleModel);
        vehicleMakeRepository.save(vehicleMake.get());
        return vehicleModelRepository.findById(vehicleModel.getId()).get();
    }

    @Override
//    @CacheEvict(value = "vehicleModels", key = "#vehicleModel.id")
    public VehicleModel updateVehicleModel(Integer makeId, VehicleModel vehicleModel) {
        Optional<VehicleMake> vehicleMake = vehicleMakeRepository.findById(makeId);
        if (vehicleMake.isEmpty()) {
            throw new VehicleMakeNotFoundException(makeId.toString());
        }

        VehicleModel existingModel = vehicleModelRepository.findById(vehicleModel.getId())
                .orElseThrow(() -> new VehicleModelNotFoundException(vehicleModel.getId().toString()));

        // Check if another VehicleMake with the same name exists
        Optional<VehicleModel> makeWithSameName = vehicleModelRepository.findByModelName(vehicleModel.getModelName());
        if (makeWithSameName.isPresent() && !makeWithSameName.get().getId().equals(vehicleModel.getId())) {
            throw new VehicleModelAlreadyExistsException(vehicleModel.getModelName());
        }

        //Update the existingModel fields with the new values
        existingModel.setModelName(vehicleModel.getModelName());

        //Save the updated entity
        vehicleMake.get().getVehicleModelList().add(vehicleModel);
        vehicleMakeRepository.save(vehicleMake.get());
        System.out.println("Saved model: " + vehicleModel.getModelName());
        return vehicleModelRepository.findById(vehicleModel.getId()).get();
    }

    //Patch Method: Change a field in the method
    @Override
//    @CacheEvict(value = "vehicleModels", key = "#id")
    public VehicleModel patchVehicleModel(Map<String, Object> updates, Integer id) throws FieldNotFoundException {
        VehicleModel vehicleModelPatch = vehicleModelRepository.findById(id)
                .orElseThrow(() -> new VehicleModelNotFoundException(id.toString()));

        //Check if the update includes a new vehicle model name
        if (updates.containsKey("modelName")) {
            String newModelName = updates.get("modelName").toString();

            //Find existing vehicle model with the same name, ignore the current one being updated
            Optional<VehicleModel> duplicateModel = vehicleModelRepository.findByModelName(newModelName);

            if (duplicateModel.isPresent() && !duplicateModel.get().getId().equals(id)){
                throw new VehicleModelAlreadyExistsException(newModelName);
            }
        }

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
//    @CacheEvict(value = "vehicleModels", key = "#id")
    public void deleteVehicleModelById(Integer id) {
        vehicleModelRepository.deleteById(id);
    }

}
