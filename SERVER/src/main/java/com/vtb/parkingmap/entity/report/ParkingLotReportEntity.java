package com.vtb.parkingmap.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.SelectBeforeUpdate;

import com.vtb.parkingmap.entity.parkinglot.ParkingLotEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author bht
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "PARKING_LOT_REPORT")
@Entity(name = "PARKING_LOT_REPORT")
@PrimaryKeyJoinColumn(name = "PARKING_LOT_REPORT_ID")
public final class ParkingLotReportEntity extends ReportEntity {

    @ManyToOne
    @JoinColumn(name = "PARKING_LOT_ID", referencedColumnName = "ID", nullable = false)
    private ParkingLotEntity parkingLotEntity;

    @Column(name = "VIOLATION", nullable = false)
    private String violation;
}