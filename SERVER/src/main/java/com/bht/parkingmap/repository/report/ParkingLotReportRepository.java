package com.bht.parkingmap.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.entity.report.ParkingLotReportEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ParkingLotReportRepository extends JpaRepository<ParkingLotReportEntity, Long> {
}