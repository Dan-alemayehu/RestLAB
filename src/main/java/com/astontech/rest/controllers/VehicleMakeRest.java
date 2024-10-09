package com.astontech.rest.controllers;

import com.astontech.rest.domain.Vehicle;
import com.astontech.rest.domain.VehicleMake;
import com.astontech.rest.services.VehicleMakeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicle-makes")
@Slf4j
public class VehicleMakeRest {

    private final VehicleMakeService vehicleMakeService;

    @Autowired
    public VehicleMakeRest(VehicleMakeService vehicleMakeService){
        this.vehicleMakeService = vehicleMakeService;
    }

    //Get Method: Retrieve vehicle Make by ID
    @GetMapping("/{id}")
    public ResponseEntity<VehicleMake> findVehicleMakeById(@PathVariable Integer id) {
        return ResponseEntity.ok(vehicleMakeService.findVehicleMakeById(id));
    }

    //Get Method: Get all vehicle makes
    @GetMapping("/")
    public ResponseEntity<List<VehicleMake>> findAllVehicleMakes(){
        return ResponseEntity.ok(vehicleMakeService.findAllVehicleMakes());
    }

    //PostMethod: Add a vehicle make
    @PostMapping("/")
    public ResponseEntity<VehicleMake> addVehicleMake(@RequestBody VehicleMake vehicleMake){
        return new ResponseEntity<>(
                vehicleMakeService.saveVehicleMake(vehicleMake),
                HttpStatus.CREATED
        );
    }

    //Put Method: Update a vehicle model
    @PutMapping("/{id}")
    public ResponseEntity<VehicleMake> updateVehicleMake(@PathVariable Integer id,
                                                 @RequestBody VehicleMake vehicleMake){
        vehicleMake.setId(id);
        return ResponseEntity.ok(vehicleMakeService.saveVehicleMake(vehicleMake));
    }

    //DeleteMethod: Delete a vehicle make
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleMake(@PathVariable Integer id){
        vehicleMakeService.deleteVehicleMake(id);
        return ResponseEntity.noContent().build();
    }
}