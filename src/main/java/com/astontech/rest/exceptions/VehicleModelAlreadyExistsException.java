package com.astontech.rest.exceptions;

public class VehicleModelAlreadyExistsException extends RuntimeException{
    public VehicleModelAlreadyExistsException(String vehicleModelName) {
        super("Vehicle Model " + vehicleModelName + " already exists");
    }
}
