package com.astontech.rest.controllers;

import com.astontech.rest.domain.VehicleMake;
import com.astontech.rest.domain.VehicleModel;
import com.astontech.rest.services.VehicleModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle-models")
@Slf4j
public class VehicleModelRest {

    private final VehicleModelService vehicleModelService;

    @Autowired
    public VehicleModelRest(VehicleModelService vehicleModelService){
        this.vehicleModelService = vehicleModelService;
    }

    //Get Method: Retrieve vehicle Make by ID
    @GetMapping("/{id}")
    public ResponseEntity<VehicleModel> findVehicleModelById(@PathVariable Integer id) {
        return ResponseEntity.ok(vehicleModelService.findVehicleModelById(id));
    }

    //Get Method: Get all vehicle models
    @GetMapping
    public ResponseEntity<List<VehicleModel>> findAllVehicleModels(){
        return ResponseEntity.ok(vehicleModelService.findAllVehicleModels());
    }

    //Post Method: Add a vehicle make
    @PostMapping("/")
    public ResponseEntity<VehicleModel> addVehicleModel(@RequestBody VehicleModel vehicleModel){
        return new ResponseEntity<>(
                vehicleModelService.saveVehicleModel(vehicleModel),
                HttpStatus.CREATED
                );
    }
    //Put Method: Update a vehicle model
    @PutMapping("/{id}")
    public ResponseEntity<VehicleModel> updateVehicleModel(@PathVariable Integer id,
                                                           @RequestBody VehicleModel vehicleModel){
        vehicleModel.setId(id);
        return ResponseEntity.ok(vehicleModelService.saveVehicleModel(vehicleModel));
    }


    //DeleteMethod: Delete a vehicle model
    @DeleteMapping("/{id}")
    public void deleteVehicleModel(@PathVariable Integer id){
        vehicleModelService.deleteVehicleModelById(id);
    }


}