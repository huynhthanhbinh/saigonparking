package com.bht.parkingmap.dbserver.entity.parkinglot;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SelectBeforeUpdate;

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
@Table(name = "[PARKING_LOT_UNIT]")
public final class ParkingLotUnitEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[PARKING_LOT_ID]", referencedColumnName = "[ID]", nullable = false)
    private ParkingLotEntity parkingLotEntity;

    @Column(name = "[LOWER_BOUND_HOUR]", nullable = false)
    private Short lowerBoundHour;

    @Column(name = "[UPPER_BOUND_HOUR]", nullable = false)
    private Short upperBoundHour;

    @Column(name = "[UNIT_PRICE_PER_HOUR]", nullable = false)
    private Integer unitPricePerHour;

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

        ParkingLotUnitEntity that = (ParkingLotUnitEntity) o;

        return parkingLotEntity.equals(that.parkingLotEntity) &&
                lowerBoundHour.equals(that.lowerBoundHour) &&
                upperBoundHour.equals(that.upperBoundHour) &&
                unitPricePerHour.equals(that.unitPricePerHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                parkingLotEntity,
                lowerBoundHour,
                upperBoundHour,
                unitPricePerHour);
    }
}