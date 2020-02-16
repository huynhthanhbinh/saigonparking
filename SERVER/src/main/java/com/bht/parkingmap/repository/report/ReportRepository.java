package com.bht.parkingmap.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bht.parkingmap.entity.report.ReportEntity;

/**
 *
 * @author bht
 */
@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
}