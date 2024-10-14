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

@RestController
@RequestMapping("/vehicle")
@Slf4j
public class VehicleRest {

    private final VehicleService vehicleService;

    public VehicleRest(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    //Get Method: Retrieve vehicle by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findVehicleById(@PathVariable Integer id) {
        return ResponseEntity.ok(vehicleService.findVehicleById(id));
    }

    //Get Method: Retrieve all vehicles
    @GetMapping("/")
    public ResponseEntity<List<Vehicle>> findAllVehicles(){
       return ResponseEntity.ok(vehicleService.findAllVehicles());
    }

    //Post Method: Add a vehicle
    @PostMapping("/{modelId}")
    public ResponseEntity<Vehicle> addVehicle(@PathVariable Integer modelId,
                                                  @RequestBody Vehicle vehicle){
        return new ResponseEntity<>(
                vehicleService.saveVehicle(modelId, vehicle),
                HttpStatus.CREATED
                );
    }

    //Put Method: Update a vehicle model
    @PutMapping("{modelId}/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Integer id,
                                                           @PathVariable Integer modelId,
                                                           @RequestBody Vehicle vehicle){
        vehicle.setId(id);
        Vehicle updatedVehicle = vehicleService.updateVehicle(modelId, vehicle);
        return ResponseEntity.ok(updatedVehicle);
    }

    //Patch Method: Update a field in the vehicle model
    @PatchMapping("/{id}")
    public ResponseEntity<Vehicle> patchVehicle(@PathVariable Integer id, @RequestBody Map<String, Object> updates){
        return ResponseEntity.ok(vehicleService.patchVehicle(updates, id));
    }

    //Delete Method: Delete a vehicle
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Integer id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

}
