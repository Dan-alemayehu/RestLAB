package com.astontech.rest.exceptions;

public class VehicleModelNotFoundException extends RuntimeException {
    public VehicleModelNotFoundException(String vehicleModelId) {
        super("Vehicle Model with ID " + vehicleModelId + " not found");
    }
}