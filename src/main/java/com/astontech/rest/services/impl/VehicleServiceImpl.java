package com.astontech.rest.services.impl;

import com.astontech.rest.domain.Vehicle;
import com.astontech.rest.exceptions.FieldNotFoundException;
import com.astontech.rest.exceptions.VehicleAlreadyExistsException;
import com.astontech.rest.exceptions.VehicleNotFoundException;
import com.astontech.rest.repositories.VehicleRepository;
import com.astontech.rest.services.VehicleModelService;
import com.astontech.rest.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Vehicle saveVehicle(Vehicle vehicle) {
        Optional<Vehicle> existingVehicle = vehicleRepository.findByVin(vehicle.getVin());
        if (existingVehicle.isPresent()) {
            throw new VehicleAlreadyExistsException(vehicle.getVin());
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle findVehicleById(Integer id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id.toString()));
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle patchVehicle(Map<String, Object> updates, Integer id) throws FieldNotFoundException{
        //Find vehicle by ID or throw exception if not found
        Vehicle vehiclePatch = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(String.valueOf(id)));
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
    public void deleteVehicleById(Integer id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }
}
