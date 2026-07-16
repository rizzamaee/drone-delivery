package com.drone.delivery.dto;

import com.drone.delivery.enums.DroneModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DroneRequest {

    private String serialNumber;

    private DroneModel model;

    private int batteryCapacity;

}
