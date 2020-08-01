package com.bht.saigonparking.service.booking.entity;

import java.sql.Timestamp;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[BOOKING_HISTORY]")
public final class BookingHistoryEntity extends BaseEntity {

    public static final Comparator<BookingHistoryEntity> SORT_BY_LAST_UPDATED_THEN_BY_ID =
            new SortByLastUpdated().thenComparing(new SortById());

    @ManyToOne(optional = false)
    @JoinColumn(name = "[BOOKING_ID]", referencedColumnName = "[ID]", nullable = false, updatable = false)
    private BookingEntity bookingEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "[STATUS_ID]", referencedColumnName = "[ID]", nullable = false, updatable = false)
    private BookingStatusEntity bookingStatusEntity;

    @Column(name = "[NOTE]")
    private String note;

    @CreationTimestamp
    @EqualsAndHashCode.Exclude
    @Column(name = "[LAST_UPDATED]", nullable = false, updatable = false)
    private Timestamp lastUpdated;

    @NoArgsConstructor
    private static final class SortByLastUpdated implements Comparator<BookingHistoryEntity> {
        @Override
        public int compare(BookingHistoryEntity history1, BookingHistoryEntity history2) {
            return history2.lastUpdated.compareTo(history1.lastUpdated);
        }
    }

    @NoArgsConstructor
    private static final class SortById implements Comparator<BookingHistoryEntity> {
        @Override
        public int compare(BookingHistoryEntity history1, BookingHistoryEntity history2) {
            return history2.id.compareTo(history1.id);
        }
    }
}