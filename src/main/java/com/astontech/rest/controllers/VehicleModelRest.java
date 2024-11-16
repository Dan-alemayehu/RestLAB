package com.astontech.rest.controllers;

import com.astontech.rest.domain.VehicleModel;
import com.astontech.rest.services.VehicleModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vehicle-models")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class VehicleModelRest {

    private final VehicleModelService vehicleModelService;

    @Autowired
    public VehicleModelRest(VehicleModelService vehicleModelService){
        this.vehicleModelService = vehicleModelService;
    }

    //Get Method: Retrieve vehicle model by ID
    @GetMapping("/{makeId}/{id}")
    public ResponseEntity<VehicleModel> findVehicleModelById(@PathVariable Integer id,
                                                             @PathVariable Integer makeId) {
        return ResponseEntity.ok(vehicleModelService.findVehicleModelById(makeId, id));
    }

    // Get Method: Retrieve all vehicle models by Make ID
    @GetMapping("/{makeId}")
    public ResponseEntity<List<VehicleModel>> findModelsByMakeId(@PathVariable Integer makeId) {
        List<VehicleModel> models = vehicleModelService.findModelsByMakeId(makeId);
        return ResponseEntity.ok(models);
    }

    //Get Method: Get all vehicle models
    @GetMapping("/")
    public ResponseEntity<List<VehicleModel>> findAllVehicleModels(){
        return ResponseEntity.ok(vehicleModelService.findAllVehicleModels());
    }

    //Post Method: Add a vehicle make
    @PostMapping("/{makeId}")
    public ResponseEntity<VehicleModel> addVehicleModel(@PathVariable Integer makeId,
                                                        @RequestBody VehicleModel vehicleModel){
        System.out.println("Vehicle Model: " + vehicleModel);
        return new ResponseEntity<>(
                vehicleModelService.saveVehicleModel(makeId, vehicleModel),
                HttpStatus.CREATED
                );
    }
    //Put Method: Update a vehicle model
    @PutMapping("/{makeId}/{id}")
    public ResponseEntity<VehicleModel> updateVehicleModel(@PathVariable Integer makeId,
                                                           @PathVariable Integer id,
                                                           @RequestBody VehicleModel vehicleModel){
        vehicleModel.setId(id);
        VehicleModel updatedVehicleModel = vehicleModelService.updateVehicleModel(makeId, vehicleModel);
        return ResponseEntity.ok(updatedVehicleModel);
    }

    //Patch Method: Patch a vehicle model
    @PatchMapping("/{id}")
    public ResponseEntity<VehicleModel> patchVehicleModel(@PathVariable Integer id,
                                                          @RequestBody Map<String, Object> updates){
        return ResponseEntity.ok(vehicleModelService.patchVehicleModel(updates, id));
    }


    //DeleteMethod: Delete a vehicle model
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleModel(@PathVariable Integer id){
        vehicleModelService.deleteVehicleModelById(id);
        return ResponseEntity.noContent().build();
    }


}