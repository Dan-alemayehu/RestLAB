package com.astontech.rest.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data

public class Vehicle {

    //region ATTRIBUTE
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VehicleId")
    private Integer id;

    @Version
    private Integer version;

    private Integer year;
    private String licensePlate;
    private String vin;
    private String color;
    private Boolean isPurchase;
    private LocalDate purchaseDate;
    private Integer purchasePrice;
    //endregion

    //region CONSTRUCTORS
    public Vehicle() {};
    public Vehicle(String licensePlate, Integer year, String vin, String color) {
        this.licensePlate = licensePlate;
        this.year = year;
        this.vin = vin;
        this.color = color;
    }
    //endregion

}