package com.astontech.rest.services.impl;

import com.astontech.rest.domain.Vehicle;
import com.astontech.rest.domain.VehicleModel;
import com.astontech.rest.exceptions.*;
import com.astontech.rest.repositories.VehicleModelRepository;
import com.astontech.rest.repositories.VehicleRepository;
import com.astontech.rest.services.VehicleModelService;
import com.astontech.rest.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.Cacheable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleModelRepository vehicleModelRepository;

    @Autowired
    public VehicleServiceImpl(VehicleModelRepository vehicleModelRepository, VehicleRepository vehicleRepository){
        this.vehicleRepository = vehicleRepository;
        this.vehicleModelRepository = vehicleModelRepository;
    }



    @Override
    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
//    @Cacheable(value = "vehicles", key = "#id")
    public Vehicle findVehicleById(Integer id, Integer modelId) {
        Optional<VehicleModel> vehicleModel = vehicleModelRepository.findById(modelId);
        if (vehicleModel.isEmpty()) {
            throw new VehicleModelNotFoundException(vehicleModel.toString());
        }
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id.toString()));
    }

    @Override
    public Vehicle saveVehicle(Integer modelId, Vehicle vehicle) {
        Optional<VehicleModel> vehicleModel = vehicleModelRepository.findById(modelId);
        if (vehicleModel.isEmpty()) {
            throw new VehicleModelNotFoundException(vehicleModel.toString());
        }
        Optional<Vehicle> existingVehicle = vehicleRepository.findByVin(vehicle.getVin());
        if (existingVehicle.isPresent()) {
            throw new VehicleAlreadyExistsException(vehicle.getVin());
        }
        vehicleModel.get().getVehicles().add(vehicle);
        vehicleModelRepository.save(vehicleModel.get());
        return vehicleRepository.findByVin(vehicle.getVin()).get();
    }

    @Override
//    @CacheEvict(value = "vehicles", key = "#vehicle.id")
    public Vehicle updateVehicle(Integer modelId,Vehicle vehicle) {
        Optional<VehicleModel> vehicleModel = vehicleModelRepository.findById(modelId);
        if (vehicleModel.isEmpty()) {
            throw new VehicleModelNotFoundException(vehicleModel.toString());
        }

        Vehicle existingVehicle = vehicleRepository.findById(vehicle.getId())
                .orElseThrow(() -> new VehicleNotFoundException(vehicle.getId().toString()));

        // Check if a different vehicle already has this VIN
        Optional<Vehicle> vehicleWithSameVin = vehicleRepository.findByVin(vehicle.getVin());
        if (vehicleWithSameVin.isPresent() && !vehicleWithSameVin.get().getId().equals(vehicle.getId())) {
            throw new VehicleAlreadyExistsException(vehicle.getVin());
        }

        // Update the fields on the existing vehicle
        existingVehicle.setLicensePlate(vehicle.getLicensePlate());
        existingVehicle.setVin(vehicle.getVin());
        existingVehicle.setColor(vehicle.getColor());
        existingVehicle.setYear(vehicle.getYear());
        existingVehicle.setIsPurchase(vehicle.getIsPurchase());
        existingVehicle.setPurchaseDate(vehicle.getPurchaseDate());
        existingVehicle.setPurchasePrice(vehicle.getPurchasePrice());

        // Save the updated vehicle back to the repository
        vehicleModel.get().getVehicles().add(vehicle);
        vehicleModelRepository.save(vehicleModel.get());
        System.out.println("Saved Vehicle: " + existingVehicle);
        return vehicleRepository.findByVin(vehicle.getVin()).get();
    }

    @Override
//    @CacheEvict(value = "vehicles", key = "#id")
    public Vehicle patchVehicle(Integer modelId, Map<String, Object> updates, Integer id) throws FieldNotFoundException{
        Optional<VehicleModel> vehicleModel = vehicleModelRepository.findById(modelId);
        if (vehicleModel.isEmpty()) {
            throw new VehicleModelNotFoundException(vehicleModel.toString());
        }

        //Find vehicle by ID or throw exception if not found
        Vehicle vehiclePatch = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(String.valueOf(id)));

        // Check if the VIN is part of the update
        if(updates.containsKey("vin")){
            String newVin = updates.get("vin").toString();

            Optional<Vehicle> existingVehicle = vehicleRepository.findByVin(newVin);
            if (existingVehicle.isPresent() && !existingVehicle.get().getId().equals(id)){
                throw new VehicleAlreadyExistsException(newVin);
            }
        }

        //Iterate over the map of fields to update
        updates.forEach((fieldName, fieldValue) -> {
            try{
                //Use reflection to get the field of the vehicle class
                Field field = Vehicle.class.getDeclaredField(fieldName);
                //Make field accessible if private
                field.setAccessible(true);
                //Set the field value on the vehicle
                field.set(vehiclePatch, fieldValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                //Throw custom exception if field is not found
                throw new FieldNotFoundException(fieldName);
            }
        });

        //Save the updated vehicle
        vehicleModel.get().getVehicles().add(vehiclePatch);
        vehicleModelRepository.save(vehicleModel.get());
        System.out.println("Saved Vehicle: " + vehiclePatch);
        return vehicleRepository.findByVin(vehiclePatch.getVin()).get();
    }

    @Override
//    @CacheEvict(value = "vehicles", key = "#id")
    public void deleteVehicle(Integer id) {
        vehicleRepository.deleteById(id);
    }
}
