package com.vtb.parkingmap.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.SelectBeforeUpdate;

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
@Table(name = "WRONG_PARKING_REPORT")
@Entity(name = "WRONG_PARKING_REPORT")
@PrimaryKeyJoinColumn(name = "WRONG_PARKING_REPORT_ID")
public final class WrongParkingReportEntity extends ReportEntity {

    @Column(name = "REGISTRATION_PLATE", nullable = false)
    private String registrationPlate;

    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;
}