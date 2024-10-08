package com.astontech.rest.controllers.advice;

import com.astontech.rest.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String productNotFoundHandler(ProductNotFoundException pEx) {

        return pEx.getLocalizedMessage();
    }

    @ExceptionHandler(FieldNotFoundException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String fieldNotFoundHandler(FieldNotFoundException fEx) {
        return fEx.getLocalizedMessage();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String unAuthorizedHandler(UnauthorizedException uEx) {
        return uEx.getLocalizedMessage();
    }

    @ExceptionHandler(CartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String cartNotFoundHandler(CartNotFoundException cEx) {
        return cEx.getLocalizedMessage();
    }

    @ExceptionHandler(ItemNotInCartException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String itemNotInCartHandler(ItemNotInCartException iEx) {
        return iEx.getLocalizedMessage();
    }

    @ExceptionHandler(OutOfStockException.class)
    @ResponseStatus(HttpStatus.OK)
    String outOfStockHandler(OutOfStockException oEx) {
        return oEx.getLocalizedMessage();
    }

    //Vehicle Exception Handlers
    @ExceptionHandler(VehicleMakeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String vehicleMakeNotFoundHandler(VehicleMakeNotFoundException ex) {
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(VehicleModelNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String vehicleModelNotFoundHandler(VehicleModelNotFoundException ex) {
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String vehicleNotFoundHandler(VehicleNotFoundException ex) {
        return ex.getLocalizedMessage();
    }



    //General Exception Catch
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String generalExceptionHandler(Exception ex) {
        return "general server error";
    }



}
