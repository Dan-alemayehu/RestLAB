package com.astontech.rest.exceptions;

public class VehicleMakeAlreadyExistsException extends RuntimeException{
    public VehicleMakeAlreadyExistsException(String vehicleMakeName) {
        super("Vehicle make with the name " + vehicleMakeName + " already exists");
    }
}
