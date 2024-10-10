package com.astontech.rest.services.impl;

import com.astontech.rest.domain.VehicleMake;
import com.astontech.rest.exceptions.FieldNotFoundException;
import com.astontech.rest.exceptions.VehicleMakeAlreadyExistsException;
import com.astontech.rest.exceptions.VehicleMakeNotFoundException;
import com.astontech.rest.repositories.VehicleMakeRepository;
import com.astontech.rest.services.VehicleMakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class VehicleMakeServiceImpl implements VehicleMakeService{

    private final VehicleMakeRepository vehicleMakeRepository;

    @Autowired
    public VehicleMakeServiceImpl(VehicleMakeRepository vehicleMakeRepository) {
        this.vehicleMakeRepository = vehicleMakeRepository;
    }

    @Override
    public List<VehicleMake> findAllVehicleMakes() {
        return vehicleMakeRepository.findAll();
    }

    @Override
    @Cacheable(value = "vehicleMakes", key = "#id")
    public VehicleMake findVehicleMakeById(Integer id) {
        return vehicleMakeRepository.findById(id)
                .orElseThrow(() -> new VehicleMakeNotFoundException(id.toString()));
    }

    @Override
    public VehicleMake saveVehicleMake(VehicleMake vehicleMake) {
        Optional<VehicleMake> existingMake = vehicleMakeRepository.findByVehicleMakeName(vehicleMake.getVehicleMakeName());
        if (existingMake.isPresent()) {
            throw new VehicleMakeAlreadyExistsException(vehicleMake.getVehicleMakeName());
        }
        return vehicleMakeRepository.save(vehicleMake);
    }

    @Override
    @CacheEvict(value = "vehicleMakes", key = "#vehicleMake.id")
    public VehicleMake updateVehicleMake(VehicleMake vehicleMake) {
        VehicleMake existingMake = vehicleMakeRepository.findById(vehicleMake.getId())
                .orElseThrow(() -> new VehicleMakeNotFoundException(vehicleMake.getId().toString()));

        // Update the existingMake fields with the new values
        existingMake.setVehicleMakeName(vehicleMake.getVehicleMakeName());
        existingMake.setCreateDate(vehicleMake.getCreateDate());  // Set this even if it might be null
//        existingMake.setVehicleModelList(vehicleMake.getVehicleModelList());  // Set or replace the list of models

        // Save the updated entity
        VehicleMake savedMake = vehicleMakeRepository.save(existingMake);
        System.out.println("Saved VehicleMake: " + savedMake.getVehicleMakeName());
        return savedMake;
    }


    //Patch Method: Change a field in the method
    @Override
    @CacheEvict(value = "vehicleMakes", key = "#id")
    public VehicleMake patchVehicleMake(Map<String, Object> updates, Integer id) throws FieldNotFoundException {
        VehicleMake vehicleMakePatch = vehicleMakeRepository.findById(id)
                .orElseThrow(() -> new VehicleMakeNotFoundException(String.valueOf(id)));
        updates.forEach((fieldName, fieldValue) -> {
            try{
                Field field = VehicleMake.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(vehicleMakePatch, fieldValue);
            } catch (NoSuchFieldException | IllegalAccessException e){
                throw new FieldNotFoundException(fieldName);
            }
        });
        return vehicleMakeRepository.save(vehicleMakePatch);
    }

    @Override
    @CacheEvict(value = "vehicleMakes", key = "#id")
    public void deleteVehicleMake(Integer id) {
        vehicleMakeRepository.deleteById(id);
    }
}
