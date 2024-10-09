package com.astontech.rest.services.impl;

import com.astontech.rest.domain.VehicleMake;
import com.astontech.rest.exceptions.VehicleMakeAlreadyExistsException;
import com.astontech.rest.exceptions.VehicleMakeNotFoundException;
import com.astontech.rest.exceptions.VehicleNotFoundException;
import com.astontech.rest.repositories.VehicleMakeRepository;
import com.astontech.rest.repositories.VehicleModelRepository;
import com.astontech.rest.services.VehicleMakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleMakeServiceImpl implements VehicleMakeService{

    private VehicleMakeRepository vehicleMakeRepository;

    @Autowired
    public VehicleMakeServiceImpl(VehicleMakeRepository vehicleMakeRepository) {
        this.vehicleMakeRepository = vehicleMakeRepository;
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
    public VehicleMake findVehicleMakeById(Integer id) {
        return vehicleMakeRepository.findById(id)
                .orElseThrow(() -> new VehicleMakeNotFoundException(id.toString()));
    }

    @Override
    public VehicleMake updateVehicleMake(VehicleMake vehicleMake) {
        return vehicleMakeRepository.save(vehicleMake);
    }

    @Override
    public void deleteVehicleMake(Integer id) {
        vehicleMakeRepository.deleteById(id);

    }

    @Override
    public List<VehicleMake> findAllVehicleMakes() {
        return vehicleMakeRepository.findAll();
    }
}
