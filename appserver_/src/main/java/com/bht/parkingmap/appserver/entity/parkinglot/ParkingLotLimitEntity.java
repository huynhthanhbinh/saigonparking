package com.bht.parkingmap.appserver.entity.parkinglot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.parkingmap.appserver.annotation.CapacityValidation;
import com.bht.parkingmap.appserver.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 *
 * @author bht
 */
@Entity
@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@CapacityValidation
@Table(name = "[PARKING_LOT_LIMIT]")
public final class ParkingLotLimitEntity extends BaseEntity {

    @ColumnDefault("0")
    @Column(name = "[AVAILABILITY]")
    private Short availableSlot;

    @ColumnDefault("0")
    @Column(name = "[CAPACITY]")
    private Short totalSlot;

    @MapsId
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(optional = false)
    @JoinColumn(name = "[ID]", unique = true)
    private ParkingLotEntity parkingLotEntity;
}