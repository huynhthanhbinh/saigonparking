package com.bht.saigonparking.service.booking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
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
@Table(name = "[PARKING_LOT_INFORMATION]")
public final class BookingStatisticEntity extends BaseEntity {

    @NaturalId
    @Column(name = "[PARKING_LOT_ID]", nullable = false, unique = true)
    private Long parkingLotId;

    @ColumnDefault("0")
    @Column(name = "[RATING_AVERAGE]", updatable = false)
    private Double ratingAverage;

    @ColumnDefault("0")
    @Column(name = "[NUMBER_OF_RATING]", updatable = false)
    private Long nRating;

    @ColumnDefault("0")
    @Column(name = "[NUMBER_OF_BOOKING]", updatable = false)
    private Long nBooking;
}