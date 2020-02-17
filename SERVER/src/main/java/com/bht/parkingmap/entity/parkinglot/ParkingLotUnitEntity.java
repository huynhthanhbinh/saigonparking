package com.bht.parkingmap.entity.parkinglot;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.SelectBeforeUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author bht
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[PARKING_LOT_UNIT]")
@Entity(name = "PARKING_LOT_UNIT")
public final class ParkingLotUnitEntity {

    @Id
    @Column(name = "[ID]", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
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

    @Version
    @Column(name = "[VERSION]")
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParkingLotUnitEntity that = (ParkingLotUnitEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(parkingLotEntity, that.parkingLotEntity) &&
                lowerBoundHour.equals(that.lowerBoundHour) &&
                upperBoundHour.equals(that.upperBoundHour) &&
                unitPricePerHour.equals(that.unitPricePerHour) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                parkingLotEntity,
                lowerBoundHour,
                upperBoundHour,
                unitPricePerHour,
                version);
    }
}