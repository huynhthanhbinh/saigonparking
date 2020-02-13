package com.vtb.parkingmap.entity.user;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "PARKING_LOT_EMPLOYEE")
@Entity(name = "PARKING_LOT_EMPLOYEE")
@PrimaryKeyJoinColumn(name = "EMPLOYEE_ID")
public final class ParkingLotEmployeeEntity extends UserEntity {

    @OneToOne
    @JoinColumn(name = "PARKING_LOT_ID", referencedColumnName = "ID", nullable = false)
    private ParkingLotEntity parkingLotEntity;
}