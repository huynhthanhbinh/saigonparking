package com.bht.parkingmap.entity.parkinglot;

import java.sql.Time;
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

import org.hibernate.annotations.ColumnDefault;
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
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[PARKING_LOT]")
public final class ParkingLotEntity {

    @Id
    @Column(name = "[ID]", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Version
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParkingLotEntity that = (ParkingLotEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(parkingLotTypeEntity, that.parkingLotTypeEntity) &&
                latitude.equals(that.latitude) &&
                longitude.equals(that.longitude) &&
                openingHour.equals(that.openingHour) &&
                closingHour.equals(that.closingHour) &&
                Objects.equals(isAvailable, that.isAvailable) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                parkingLotTypeEntity,
                latitude,
                longitude,
                openingHour,
                closingHour,
                isAvailable,
                version);
    }
}