package com.astontech.rest.domain;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class VehicleMake {

    //region ATTRIBUTE
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VehicleMakeId")
    private Integer id;

    @Version
    private Integer version;
    private String vehicleMakeName;
    private LocalDate createDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<VehicleModel> vehicleModelList = new ArrayList<>();
    //endregion

    //region CONTRUCTORS
    public VehicleMake() {};
    public VehicleMake(String vehicleMakeName) {
        this.setVehicleMakeName(vehicleMakeName);
    }
    public VehicleMake(String vehicleMakeName, List<VehicleModel> vehicleModelList) {
        this.setVehicleMakeName(vehicleMakeName);
        this.setVehicleModelList(vehicleModelList);
    }
    //endregion
}