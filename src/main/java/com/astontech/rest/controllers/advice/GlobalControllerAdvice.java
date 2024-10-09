package com.astontech.rest.controllers.advice;

import com.astontech.rest.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalControllerAdvice {

    //Field Doesn't Exist
    @ExceptionHandler(FieldNotFoundException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String fieldNotFoundHandler(FieldNotFoundException fEx) {
        return fEx.getLocalizedMessage();
    }

    //Unauthorized Access
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String unAuthorizedHandler(UnauthorizedException uEx) {
        return uEx.getLocalizedMessage();
    }

    //Vehicle Make Not Found
    @ExceptionHandler(VehicleMakeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String vehicleMakeNotFoundHandler(VehicleMakeNotFoundException ex) {
        return ex.getLocalizedMessage();
    }

    //Vehicle Model Not Found
    @ExceptionHandler(VehicleModelNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String vehicleModelNotFoundHandler(VehicleModelNotFoundException ex) {
        return ex.getLocalizedMessage();
    }

    //Vehicle Not Found
    @ExceptionHandler(VehicleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String vehicleNotFoundHandler(VehicleNotFoundException ex) {
        return ex.getLocalizedMessage();
    }

    //Duplicate VIN
    @ExceptionHandler(VehicleAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String VehicleAlreadyExistsHandler(VehicleAlreadyExistsException ex) {
        return ex.getLocalizedMessage();
    }

    //Duplicate Vehicle Make
    @ExceptionHandler(VehicleMakeAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String vehicleMakeAlreadyExistsHandler(VehicleMakeAlreadyExistsException ex) {
        return ex.getLocalizedMessage();
    }

    //Duplicate Vehicle Model
    @ExceptionHandler(VehicleModelAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String vehicleModelAlreadyExistsHandler(VehicleModelAlreadyExistsException ex) {
        return ex.getLocalizedMessage();
    }

    //General Exception Catch
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String generalExceptionHandler(Exception ex) {
        return "general server error";
    }

}