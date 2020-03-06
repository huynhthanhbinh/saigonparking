package com.bht.parkingmap.dbserver.entity.parkinglot;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.parkingmap.dbserver.base.BaseEntity;
import com.bht.parkingmap.dbserver.entity.user.CustomerEntity;

import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[PARKING_LOT_SUGGESTION]")
public final class ParkingLotSuggestionEntity extends BaseEntity {

    @ManyToOne
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

    @Column(name = "[LAST_UPDATED]")
    private Timestamp lastUpdated;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        ParkingLotSuggestionEntity that = (ParkingLotSuggestionEntity) o;

        return customerEntity.equals(that.customerEntity) &&
                parkingLotName.equals(that.parkingLotName) &&
                parkingLotAddress.equals(that.parkingLotAddress) &&
                latitude.equals(that.latitude) &&
                longitude.equals(that.longitude) &&
                Objects.equals(isHandled, that.isHandled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                customerEntity,
                parkingLotName,
                parkingLotAddress,
                latitude,
                longitude,
                isHandled);
    }
}