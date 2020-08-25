package com.bht.saigonparking.service.booking.entity;

import java.sql.Timestamp;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;
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
public final class BookingRatingEntity extends BaseEntity {

    public static final Comparator<BookingRatingEntity> SORT_BY_LAST_UPDATED_THEN_BY_ID =
            new SortByLastUpdated().thenComparing(new SortById());

    @Range(min = 1, max = 5)
    @Column(name = "[RATING]", nullable = false)
    private Short rating;

    @Column(name = "[COMMENT]")
    private String comment;

    @UpdateTimestamp
    @EqualsAndHashCode.Exclude
    @Column(name = "[LAST_UPDATED]")
    private Timestamp lastUpdated;

    @MapsId
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(optional = false)
    @JoinColumn(name = "[ID]", unique = true)
    private BookingEntity bookingEntity;

    @NoArgsConstructor
    private static final class SortByLastUpdated implements Comparator<BookingRatingEntity> {
        @Override
        public int compare(BookingRatingEntity rating1, BookingRatingEntity rating2) {
            return rating2.lastUpdated.compareTo(rating1.lastUpdated);
        }
    }

    @NoArgsConstructor
    private static final class SortById implements Comparator<BookingRatingEntity> {
        @Override
        public int compare(BookingRatingEntity rating1, BookingRatingEntity rating2) {
            return rating2.id.compareTo(rating1.id);
        }
    }
}