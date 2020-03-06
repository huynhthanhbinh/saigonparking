package com.bht.parkingmap.dbserver.entity.parkinglot;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
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
@Table(name = "[PARKING_LOT_INFORMATION]")
public final class ParkingLotInformationEntity extends BaseEntity {

    @Column(name = "[NAME]", nullable = false)
    private String name;

    @Column(name = "[ADDRESS]", nullable = false)
    private String address;

    @Column(name = "[PHONE]")
    private String phone;

    @ColumnDefault("0")
    @Column(name = "[RATING_AVERAGE]")
    private Double ratingAverage;

    @ColumnDefault("0")
    @Column(name = "[NUMBER_OF_RATING]")
    private Short nRating;

    @ColumnDefault("0")
    @Column(name = "[AVAILABILITY]")
    private Short availableSlot;

    @ColumnDefault("0")
    @Column(name = "[CAPACITY]")
    private Short totalSlot;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[ID]", unique = true)
    private ParkingLotEntity parkingLotEntity;

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

        ParkingLotInformationEntity that = (ParkingLotInformationEntity) o;

        return name.equals(that.name) &&
                address.equals(that.address) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(ratingAverage, that.ratingAverage) &&
                Objects.equals(nRating, that.nRating) &&
                Objects.equals(availableSlot, that.availableSlot) &&
                Objects.equals(totalSlot, that.totalSlot) &&
                Objects.equals(parkingLotEntity, that.parkingLotEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                name,
                address,
                phone,
                ratingAverage,
                nRating,
                availableSlot,
                totalSlot,
                parkingLotEntity);
    }
}