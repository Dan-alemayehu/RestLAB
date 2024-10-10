package com.astontech.rest.services.impl;

import com.astontech.rest.domain.Vehicle;
import com.astontech.rest.exceptions.FieldNotFoundException;
import com.astontech.rest.exceptions.VehicleAlreadyExistsException;
import com.astontech.rest.exceptions.VehicleNotFoundException;
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

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository){
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
//    @Cacheable(value = "vehicles", key = "#id")
    public Vehicle findVehicleById(Integer id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id.toString()));
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        Optional<Vehicle> existingVehicle = vehicleRepository.findByVin(vehicle.getVin());
        if (existingVehicle.isPresent()) {
            throw new VehicleAlreadyExistsException(vehicle.getVin());
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
//    @CacheEvict(value = "vehicles", key = "#vehicle.id")
    public Vehicle updateVehicle(Vehicle vehicle) {
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
        Vehicle savedVehicle = vehicleRepository.save(existingVehicle);
        System.out.println("Saved Vehicle: " + savedVehicle);
        return savedVehicle;
    }

    @Override
//    @CacheEvict(value = "vehicles", key = "#id")
    public Vehicle patchVehicle(Map<String, Object> updates, Integer id) throws FieldNotFoundException{
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
        return vehicleRepository.save(vehiclePatch);
    }

    @Override
//    @CacheEvict(value = "vehicles", key = "#id")
    public void deleteVehicle(Integer id) {
        vehicleRepository.deleteById(id);
    }
}
