package com.astontech.rest.configuration;

import com.astontech.rest.domain.Vehicle;
import com.astontech.rest.domain.VehicleMake;
import com.astontech.rest.domain.VehicleModel;
import com.astontech.rest.repositories.VehicleMakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
//@Profile("dev")
public class SeedData implements CommandLineRunner {

    private final VehicleMakeRepository vehicleMakeRepository;

    @Autowired
    public SeedData(VehicleMakeRepository vehicleMakeRepository) {
        this.vehicleMakeRepository = vehicleMakeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadVehicleData();
    }

    private void loadVehicleData() {
        if (vehicleMakeRepository.count() == 0) {
            List<VehicleMake> vehicleMakes = new ArrayList<>();

            // Create vehicles for the first model
            Vehicle vehicle1 = new Vehicle("WVU-345", 1995, "W349582028475", "Maroon");
            Vehicle vehicle2 = new Vehicle("DUE-284", 2009, "U488692834765", "Red");

            // Create first vehicle model
            VehicleModel model1 = new VehicleModel("Model X");
            model1.getVehicles().add(vehicle1);
            model1.getVehicles().add(vehicle2);

            // Create vehicles for the second model
            Vehicle vehicle3 = new Vehicle("IJE-485", 1999, "J49929386465", "Blue");

            // Create second vehicle model
            VehicleModel model2 = new VehicleModel("Model Y");
            model2.getVehicles().add(vehicle3);

            // Create vehicle make and adding models to it
            VehicleMake vehicleMake = new VehicleMake("Tesla");
            vehicleMake.getVehicleModelList().add(model1);
            vehicleMake.getVehicleModelList().add(model2);

            //Create a second Vehicle Make (for testing purposes)
            VehicleMake vehicleMake2 = new VehicleMake("BYD");


            // Adding make to list
            vehicleMakes.add(vehicleMake);
            vehicleMakes.add(vehicleMake2);

            // Saving all to the repository
            vehicleMakeRepository.saveAll(vehicleMakes);

            System.out.println(vehicleMakeRepository.count());

            // Printing out the vehicle make, vehicle model, and vehicle IDs after saving
            vehicleMakeRepository.findAll().forEach(make -> {
                System.out.println("Vehicle Make ID: " + make.getId() + ", Make Name: " + make.getVehicleMakeName());
                make.getVehicleModelList().forEach(model -> {
                    System.out.println("  Vehicle Model ID: " + model.getId() + ", Model Name: " + model.getModelName());
                    model.getVehicles().forEach(vehicle -> {
                        System.out.println("    Vehicle ID: " + vehicle.getId() + ", License Plate: " + vehicle.getLicensePlate());
                    });
                });
            });

            System.out.println(vehicleMakeRepository.count());

        }
    }
}