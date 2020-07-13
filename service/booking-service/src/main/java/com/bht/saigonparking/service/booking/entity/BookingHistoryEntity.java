package com.bht.saigonparking.service.booking.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "[BOOKING_ID]", referencedColumnName = "[ID]", nullable = false, updatable = false)
    private BookingEntity bookingEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "[STATUS_ID]", referencedColumnName = "[ID]", nullable = false, updatable = false)
    private BookingStatusEntity bookingStatusEntity;

    @Column(name = "[NOTE]")
    private String note;

    @EqualsAndHashCode.Exclude
    @Column(name = "[LAST_UPDATED]", nullable = false)
    private Timestamp lastUpdated;
}