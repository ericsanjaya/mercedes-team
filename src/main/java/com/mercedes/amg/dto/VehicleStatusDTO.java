package com.mercedes.amg.dto;

import java.util.StringJoiner;

public class VehicleStatusDTO {
    private String vehicleId;

    private int speed;
    private int brakeCondition;
    private int gear;

    public VehicleStatusDTO() {
    }

    public VehicleStatusDTO(String vehicleId, int speed, int brakeCondition, int gear) {
        this.vehicleId = vehicleId;
        this.speed = speed;
        this.brakeCondition = brakeCondition;
        this.gear = gear;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBrakeCondition() {
        return brakeCondition;
    }

    public void setBrakeCondition(int brakeCondition) {
        this.brakeCondition = brakeCondition;
    }

    public int getGear() {
        return gear;
    }

    public void setGear(int gear) {
        this.gear = gear;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", VehicleStatusDTO.class.getSimpleName() + "[", "]")
                .add("speed=" + speed)
                .add("brakeCondition=" + brakeCondition)
                .add("gear=" + gear)
                .toString();
    }
}
