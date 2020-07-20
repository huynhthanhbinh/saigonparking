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
@Table(name = "[BOOKING_STATUS]")
public final class BookingStatusEntity extends BaseEntity {

    @Column(name = "[STATUS]", nullable = false, unique = true, insertable = false, updatable = false)
    private String status;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "bookingStatusEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookingEntity> bookingEntitySet;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "bookingStatusEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookingHistoryEntity> bookingHistoryEntitySet;
}