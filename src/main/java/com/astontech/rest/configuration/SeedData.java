package com.astontech.rest.configuration;

import com.astontech.rest.domain.Cart;
import com.astontech.rest.domain.CartItem;
import com.astontech.rest.domain.Vehicle;
import com.astontech.rest.repositories.CartRepo;
import com.astontech.rest.repositories.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
//@Profile("dev")
public class SeedData implements CommandLineRunner {

    private VehicleRepository vehicleRepository;

    @Override
    public void run(String... args) throws Exception {
        loadVehicleData();
    }

    private void loadVehicleData() {
        if (vehicleRepository.count() == 0) {
            List<Vehicle> vehicles = new ArrayList<>();
            Vehicle vehicle1 = new Vehicle("WVU-345", 1995, "W349582028475", "Maroon");
            Vehicle vehicle2 = new Vehicle("DUE-284", 2009, "U488692834765", "Red");
            Vehicle vehicle3 = new Vehicle("IJE-485", 1999, "J49929386465", "Blue");
            vehicles.add(vehicle1);
            vehicles.add(vehicle2);
            vehicles.add(vehicle3);
            vehicleRepository.saveAll(vehicles);
        }
        System.out.println(vehicleRepository.count());
        }
    }
