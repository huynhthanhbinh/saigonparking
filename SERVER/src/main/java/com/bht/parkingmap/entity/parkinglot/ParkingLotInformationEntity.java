package com.bht.parkingmap.entity.parkinglot;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
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
@ToString(exclude = "parkingLotEntity")
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[PARKING_LOT_INFORMATION]")
public final class ParkingLotInformationEntity {

    @Id
    @Column(name = "[ID]", nullable = false)
    private Long id;

    @Column(name = "[NAME]", nullable = false)
    private String name;

    @Column(name = "[ADDRESS]", nullable = false)
    private String address;

    @Column(name = "[PHONE]")
    private String phone;

    @ColumnDefault("0")
    @Column(name = "[RATING_AVERAGE]", nullable = false)
    private Double ratingAverage;

    @ColumnDefault("0")
    @Column(name = "[NUMBER_OF_RATING]", nullable = false)
    private Short nRating;

    @ColumnDefault("0")
    @Column(name = "[AVAILABILITY]", nullable = false)
    private Short availableSlot;

    @ColumnDefault("0")
    @Column(name = "[CAPACITY]", nullable = false)
    private Short totalSlot;

    @Column(name = "[PHOTO_PATH]")
    private String photoPath;

    @Version
    private Long version;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[ID]", nullable = false, unique = true)
    private ParkingLotEntity parkingLotEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParkingLotInformationEntity that = (ParkingLotInformationEntity) o;
        return Objects.equals(id, that.id) &&
                name.equals(that.name) &&
                address.equals(that.address) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(ratingAverage, that.ratingAverage) &&
                Objects.equals(nRating, that.nRating) &&
                Objects.equals(availableSlot, that.availableSlot) &&
                Objects.equals(totalSlot, that.totalSlot) &&
                Objects.equals(photoPath, that.photoPath) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                name,
                address,
                phone,
                ratingAverage,
                nRating,
                availableSlot,
                totalSlot,
                photoPath,
                version);
    }
}