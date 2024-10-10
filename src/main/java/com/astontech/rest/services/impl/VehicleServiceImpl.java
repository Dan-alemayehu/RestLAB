package com.astontech.rest.services.impl;

import com.astontech.rest.domain.Vehicle;
import com.astontech.rest.domain.VehicleModel;
import com.astontech.rest.exceptions.VehicleAlreadyExistsException;
import com.astontech.rest.exceptions.VehicleNotFoundException;
import com.astontech.rest.repositories.VehicleModelRepository;
import com.astontech.rest.repositories.VehicleRepository;
import com.astontech.rest.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleModelRepository vehicleModelRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository, VehicleModelRepository vehicleModelRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleModelRepository = vehicleModelRepository;
    }

    @Override
    public List<Vehicle> findAllVehicles(Integer makeId, Integer modelId) {
        // Assuming VehicleModel contains a list of vehicles
        VehicleModel vehicleModel = vehicleModelRepository.findById(modelId)
                .orElseThrow(() -> new VehicleNotFoundException(modelId.toString()));
        return vehicleModel.getVehicles();
    }

    @Override
    @Cacheable(value = "vehicles", key = "#id")
    public Vehicle findVehicleById(Integer makeId, Integer modelId, Integer id) {
        VehicleModel vehicleModel = vehicleModelRepository.findById(modelId)
                .orElseThrow(() -> new VehicleNotFoundException(modelId.toString()));
        return vehicleModel.getVehicles().stream()
                .filter(vehicle -> vehicle.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new VehicleNotFoundException(id.toString()));
    }

    @Override
    public Vehicle saveVehicle(Integer makeId, Integer modelId, Vehicle vehicle) {
        VehicleModel vehicleModel = vehicleModelRepository.findById(modelId)
                .orElseThrow(() -> new VehicleNotFoundException(modelId.toString()));
        Optional<Vehicle> existingVehicle = vehicleRepository.findByVin(vehicle.getVin());
        if (existingVehicle.isPresent()) {
            throw new VehicleAlreadyExistsException(vehicle.getVin());
        }
        vehicleModel.getVehicles().add(vehicle);
        vehicleModelRepository.save(vehicleModel);
        return vehicle;
    }

    @Override
    @CacheEvict(value = "vehicles", key = "#vehicle.id")
    public Vehicle updateVehicle(Integer makeId, Integer modelId, Vehicle vehicle) {
        VehicleModel vehicleModel = vehicleModelRepository.findById(modelId)
                .orElseThrow(() -> new VehicleNotFoundException(modelId.toString()));

        Vehicle existingVehicle = vehicleModel.getVehicles().stream()
                .filter(v -> v.getId().equals(vehicle.getId()))
                .findFirst()
                .orElseThrow(() -> new VehicleNotFoundException(vehicle.getId().toString()));

        Optional<Vehicle> vehicleWithSameVin = vehicleRepository.findByVin(vehicle.getVin());
        if (vehicleWithSameVin.isPresent() && !vehicleWithSameVin.get().getId().equals(vehicle.getId())) {
            throw new VehicleAlreadyExistsException(vehicle.getVin());
        }

        existingVehicle.setLicensePlate(vehicle.getLicensePlate());
        existingVehicle.setVin(vehicle.getVin());
        existingVehicle.setColor(vehicle.getColor());
        existingVehicle.setYear(vehicle.getYear());
        existingVehicle.setIsPurchase(vehicle.getIsPurchase());
        existingVehicle.setPurchaseDate(vehicle.getPurchaseDate());
        existingVehicle.setPurchasePrice(vehicle.getPurchasePrice());

        vehicleModelRepository.save(vehicleModel);
        return existingVehicle;
    }

    @Override
    @CacheEvict(value = "vehicles", key = "#id")
    public Vehicle patchVehicle(Integer makeId, Integer modelId, Map<String, Object> updates, Integer id) {
        VehicleModel vehicleModel = vehicleModelRepository.findById(modelId)
                .orElseThrow(() -> new VehicleNotFoundException(modelId.toString()));

        Vehicle vehiclePatch = vehicleModel.getVehicles().stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new VehicleNotFoundException(id.toString()));

        updates.forEach((fieldName, fieldValue) -> {
            try {
                Field field = Vehicle.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(vehiclePatch, fieldValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Invalid field: " + fieldName);
            }
        });

        vehicleModelRepository.save(vehicleModel);
        return vehiclePatch;
    }

    @Override
    @CacheEvict(value = "vehicles", key = "#id")
    public void deleteVehicle(Integer makeId, Integer modelId, Integer id) {
        VehicleModel vehicleModel = vehicleModelRepository.findById(modelId)
                .orElseThrow(() -> new VehicleNotFoundException(modelId.toString()));

        Vehicle vehicleToDelete = vehicleModel.getVehicles().stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new VehicleNotFoundException(id.toString()));

        vehicleModel.getVehicles().remove(vehicleToDelete);
        vehicleModelRepository.save(vehicleModel);
    }
}
