package com.bht.saigonparking.service.parkinglot.entity;

import java.sql.Timestamp;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Range;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[PARKING_LOT_RATING]")
public final class ParkingLotRatingEntity extends BaseEntity {

    public static final Comparator<ParkingLotRatingEntity> SORT_BY_LAST_UPDATED_THEN_BY_ID =
            new SortByLastUpdated().thenComparing(new SortById());

    @ManyToOne(optional = false)
    @JoinColumn(name = "[PARKING_LOT_ID]", referencedColumnName = "[ID]", nullable = false)
    private ParkingLotEntity parkingLotEntity;

    @Range(min = 1, max = 5)
    @Column(name = "[RATING]", nullable = false)
    private Short rating;

    @Column(name = "[COMMENT]")
    private String comment;

    @Column(name = "[CUSTOMER_ID]", nullable = false)
    private Long customerId;

    @CreationTimestamp
    @EqualsAndHashCode.Exclude
    @Column(name = "[LAST_UPDATED]")
    private Timestamp lastUpdated;


    @NoArgsConstructor
    private static final class SortByLastUpdated implements Comparator<ParkingLotRatingEntity> {
        @Override
        public int compare(ParkingLotRatingEntity rating1, ParkingLotRatingEntity rating2) {
            return rating2.lastUpdated.compareTo(rating1.lastUpdated);
        }
    }

    @NoArgsConstructor
    private static final class SortById implements Comparator<ParkingLotRatingEntity> {
        @Override
        public int compare(ParkingLotRatingEntity rating1, ParkingLotRatingEntity rating2) {
            return rating2.id.compareTo(rating1.id);
        }
    }
}