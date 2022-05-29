package com.mercedes.amg.repository;

import com.mercedes.amg.model.VehicleStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface VehicleStatusRepository extends CrudRepository<VehicleStatus, Integer> {
    @Query("select v from VehicleStatus v where v.dateTime > ?1 and upper(v.vehicleId) = upper(?2)")
    List<VehicleStatus> findByDateTimeIsAfter(Date dateTime, String vehicleId, Pageable pageable);

    @Query("select v from VehicleStatus v where v.vehicleId = ?1 order by v.dateTime DESC")
    List<VehicleStatus> findByVehicleId(String vehicleId, Pageable pageable);

}
