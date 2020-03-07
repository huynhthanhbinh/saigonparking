package com.bht.parkingmap.dbserver.entity.parkinglot;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.parkingmap.dbserver.annotation.TimeFlowValidation;
import com.bht.parkingmap.dbserver.base.BaseEntity;

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
@TimeFlowValidation
@Table(name = "[PARKING_LOT]")
public final class ParkingLotEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "[PARKING_LOT_TYPE_ID]", referencedColumnName = "[ID]", nullable = false)
    private ParkingLotTypeEntity parkingLotTypeEntity;

    @Column(name = "[LATITUDE]", nullable = false)
    private Double latitude;

    @Column(name = "[LONGITUDE]", nullable = false)
    private Double longitude;

    @Column(name = "[OPENING_HOUR]", nullable = false)
    private Time openingHour;

    @Column(name = "[CLOSING_HOUR]", nullable = false)
    private Time closingHour;

    @ColumnDefault("true")
    @Column(name = "[IS_AVAILABLE]")
    private Boolean isAvailable;

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

        ParkingLotEntity that = (ParkingLotEntity) o;

        return Objects.equals(parkingLotTypeEntity, that.parkingLotTypeEntity) &&
                latitude.equals(that.latitude) &&
                longitude.equals(that.longitude) &&
                openingHour.equals(that.openingHour) &&
                closingHour.equals(that.closingHour) &&
                Objects.equals(isAvailable, that.isAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                parkingLotTypeEntity,
                latitude,
                longitude,
                openingHour,
                closingHour,
                isAvailable);
    }
}