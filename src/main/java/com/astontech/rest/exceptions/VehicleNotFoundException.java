package com.astontech.rest.exceptions;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String vehicleId) {
        super("Vehicle with ID " + vehicleId + " not found");
    }
}
