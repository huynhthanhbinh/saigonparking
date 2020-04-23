package com.bht.saigonparking.service.parkinglot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.saigonparking.service.parkinglot.base.BaseEntity;

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
@Table(name = "[PARKING_LOT_INFORMATION]")
public final class ParkingLotInformationEntity extends BaseEntity {

    @Column(name = "[NAME]", nullable = false)
    private String name;

    @Column(name = "[ADDRESS]", nullable = false)
    private String address;

    @Column(name = "[PHONE]")
    private String phone;

    @ColumnDefault("0")
    @Column(name = "[RATING_AVERAGE]", insertable = false, updatable = false)
    private Double ratingAverage;

    @ColumnDefault("0")
    @Column(name = "[NUMBER_OF_RATING]", insertable = false, updatable = false)
    private Short nRating;

    @MapsId
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(optional = false)
    @JoinColumn(name = "[ID]", unique = true)
    private ParkingLotEntity parkingLotEntity;
}