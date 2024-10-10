package com.astontech.rest.controllers;

import com.astontech.rest.domain.Vehicle;
import com.astontech.rest.domain.VehicleMake;
import com.astontech.rest.domain.VehicleModel;
import com.astontech.rest.services.VehicleService;
import com.astontech.rest.services.impl.VehicleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
//
@RestController
@RequestMapping("/makes/{makeId}/models/{modelId}/vehicles/{id}")
@Slf4j
public class VehicleRest {

    private final VehicleService vehicleService;

    public VehicleRest(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    //Get Method: Retrieve vehicle by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findVehicleById(@PathVariable Integer makeId,
                                                   @PathVariable Integer modelId,
                                                   @PathVariable Integer id) {
        return ResponseEntity.ok(vehicleService.findVehicleById(makeId, modelId, id));
    }

    //Get Method: Retrieve all vehicles
    @GetMapping("/")
    public ResponseEntity<List<Vehicle>> findAllVehicles(@PathVariable Integer makeId,
                                                         @PathVariable Integer modelId){
       return ResponseEntity.ok(vehicleService.findAllVehicles(makeId, modelId));
    }

    //Post Method: Add a vehicle
    @PostMapping("/")
    public ResponseEntity<Vehicle> addVehicle(@PathVariable Integer makeId,
                                              @PathVariable Integer modelId,
                                              @RequestBody Vehicle vehicle){
        return new ResponseEntity<>(
                vehicleService.saveVehicle(makeId, modelId, vehicle),
                HttpStatus.CREATED
                );
    }

    //Put Method: Update a vehicle model
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Integer makeId,
                                                 @PathVariable Integer modelId,
                                                 @PathVariable Integer id,
                                                 @RequestBody Vehicle vehicle){
        vehicle.setId(id);
        Vehicle updatedVehicle = vehicleService.updateVehicle(makeId, modelId, vehicle);
        return ResponseEntity.ok(updatedVehicle);
    }

    //Patch Method: Update a field in the vehicle model
    @PatchMapping("/{id}")
    public ResponseEntity<Vehicle> patchVehicle(@PathVariable Integer makeId,
                                                @PathVariable Integer modelId,
                                                @PathVariable Integer id,
                                                @RequestBody Map<String, Object> updates){
        return ResponseEntity.ok(vehicleService.patchVehicle(makeId, modelId, updates, id));
    }

    //Delete Method: Delete a vehicle
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Integer makeId,
                                              @PathVariable Integer modelId,
                                              @PathVariable Integer id) {
        vehicleService.deleteVehicle(makeId, modelId, id);
        return ResponseEntity.noContent().build();
    }

}
