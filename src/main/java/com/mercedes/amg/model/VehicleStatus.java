package com.mercedes.amg.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "VehicleStatus")
public class VehicleStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @CreationTimestamp
    private Date dateTime;
    private String vehicleId;
    private int speed;
    private int brakeCondition;
    private int gear;

    public VehicleStatus() {
    }

    public VehicleStatus(String vehicleId, int speed, int brakeCondition, int gear) {
        this.vehicleId = vehicleId;
        this.speed = speed;
        this.brakeCondition = brakeCondition;
        this.gear = gear;
    }

    public int getId() {
        return id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public int getSpeed() {
        return speed;
    }

    public int getBrakeCondition() {
        return brakeCondition;
    }

    public int getGear() {
        return gear;
    }
}
