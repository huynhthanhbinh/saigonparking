package com.bht.saigonparking.appserver.entity.parkinglot;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.saigonparking.appserver.base.BaseEntity;
import com.bht.saigonparking.appserver.entity.user.CustomerEntity;

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
@Table(name = "[PARKING_LOT_SUGGESTION]")
public final class ParkingLotSuggestionEntity extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "[CUSTOMER_ID]", referencedColumnName = "[ID]", nullable = false)
    private CustomerEntity customerEntity;

    @Column(name = "[NAME]", nullable = false)
    private String parkingLotName;

    @Column(name = "[ADDRESS]", nullable = false)
    private String parkingLotAddress;

    @Column(name = "[LATITUDE]", nullable = false)
    private Double latitude;

    @Column(name = "[LONGITUDE]", nullable = false)
    private Double longitude;

    @ColumnDefault("false")
    @Column(name = "[IS_HANDLED]")
    private Boolean isHandled;

    @EqualsAndHashCode.Exclude
    @Column(name = "[LAST_UPDATED]")
    private Timestamp lastUpdated;
}