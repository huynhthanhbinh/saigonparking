package com.vtb.parkingmap.entity.report;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.SelectBeforeUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author bht
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "PARKING_LOT_REPORT")
@Entity(name = "PARKING_LOT_REPORT")
@PrimaryKeyJoinColumn(name = "PARKING_LOT_REPORT_ID")
public final class ParkingLotReportEntity extends ReportEntity {
}