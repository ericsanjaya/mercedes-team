package com.mercedes.amg.repository;

import com.mercedes.amg.model.VehicleStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface VehicleStatusRepository extends CrudRepository<VehicleStatus, Integer> {
    @Query(value = "WITH firstDt AS (" +
            "SELECT * FROM mercedesteam.vehicle_status ORDER BY date_time DESC limit 1) " +
            "SELECT vehicle_status.id, " +
            "vehicle_status.brake_condition, " +
            "vehicle_status.date_time, " +
            "vehicle_status.gear, " +
            "vehicle_status.speed, " +
            "vehicle_status.vehicle_id " +
            "FROM mercedesteam.vehicle_status, firstDt " +
            "WHERE mercedesteam.vehicle_status.date_time > firstDt.date_time - INTERVAL '5 min'" +
            "AND mercedesteam.vehicle_status.date_time > current_timestamp - INTERVAL '2 hour'" +
            "ORDER BY mercedesteam.vehicle_status.date_time DESC",
            nativeQuery = true)
    List<VehicleStatus> findByDateTimeIsAfter(String vehicleId, Pageable pageable);

    @Query("select v from VehicleStatus v where v.vehicleId = ?1 order by v.dateTime DESC")
    List<VehicleStatus> findByVehicleId(String vehicleId, Pageable pageable);

}
