package com.bht.saigonparking.service.booking.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
@Table(name = "[BOOKING]")
public final class BookingEntity extends BaseEntity {

    @Column(name = "[PARKING_LOT_ID]", nullable = false)
    private Long parkingLotId;

    @Column(name = "[CUSTOMER_ID]", nullable = false)
    private Long customerId;

    @Column(name = "[CUSTOMER_LICENSE_PLATE]", nullable = false)
    private String customerLicensePlate;

    @Column(name = "[IS_FINISHED]", nullable = false)
    private Boolean isFinished;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "bookingEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookingHistoryEntity> bookingHistoryEntitySet;
}