package com.astontech.rest.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@Data
public class VehicleModel {

    //region ATTRIBUTE
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VehicleModelId")
    private Integer id;

    @Version
    private Integer version;
    private String modelName;

    @OneToMany(mappedBy = "vehicleModel", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Vehicle> vehicles = new ArrayList<>();

    //region CONSTRUCTORS
    public VehicleModel() {};
    public VehicleModel(String modelName) {
        this.setModelName(modelName);
    }
    public VehicleModel(String modelName, List<Vehicle> vehicles) {
        this.setModelName(modelName);
        this.setVehicles(vehicles);
    }
    //endregion

}