package com.astontech.rest.exceptions;

public class VehicleMakeNotFoundException extends RuntimeException {
    public VehicleMakeNotFoundException(String vehicleMakeId) {
        super("Vehicle Make with ID " + vehicleMakeId + " not found");
    }
}