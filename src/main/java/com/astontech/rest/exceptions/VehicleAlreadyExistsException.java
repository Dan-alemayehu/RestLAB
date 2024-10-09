package com.astontech.rest.exceptions;

public class VehicleAlreadyExistsException extends RuntimeException{
    public VehicleAlreadyExistsException(String vin) {
        super("Vehicle with VIN " + vin + " already exists");
    }
}
