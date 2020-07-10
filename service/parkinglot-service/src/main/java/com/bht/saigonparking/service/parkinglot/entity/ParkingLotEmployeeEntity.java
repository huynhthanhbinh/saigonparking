package com.bht.saigonparking.service.parkinglot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.saigonparking.common.base.BaseEntity;

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
@NaturalIdCache
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[PARKING_LOT_EMPLOYEE]")
public final class ParkingLotEmployeeEntity extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "[PARKING_LOT_ID]", referencedColumnName = "[ID]", nullable = false, updatable = false)
    private ParkingLotEntity parkingLotEntity;

    @NaturalId
    @Column(name = "[USER_ID]", nullable = false, updatable = false, unique = true)
    private Long userId;
}