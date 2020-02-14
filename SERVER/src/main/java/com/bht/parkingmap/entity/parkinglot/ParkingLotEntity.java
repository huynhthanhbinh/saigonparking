package com.bht.parkingmap.entity.parkinglot;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.parkingmap.entity.report.ParkingLotReportEntity;
import com.bht.parkingmap.entity.user.ParkingLotEmployeeEntity;

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
@Table(name = "PARKING_LOT")
@Entity(name = "PARKING_LOT")
public final class ParkingLotEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "PARKING_LOT_TYPE_ID", referencedColumnName = "ID", nullable = false)
    private ParkingLotTypeEntity parkingLotTypeEntity;

    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "PHONE")
    private String phone;

    @ColumnDefault("true")
    @Column(name = "IS_AVAILABLE", nullable = false)
    private Boolean isAvailable;

    @Column(name = "LAST_UPDATED")
    private Timestamp lastUpdated;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @OneToOne(mappedBy = "parkingLotEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ParkingLotEmployeeEntity parkingLotEmployeeEntity;

    @OneToOne(mappedBy = "parkingLotEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ParkingLotInformationEntity parkingLotInformationEntity;

    @OneToMany(mappedBy = "parkingLotEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ParkingLotUnitEntity> parkingLotUnitEntitySet;

    @OneToMany(mappedBy = "parkingLotEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ParkingLotRatingEntity> parkingLotRatingEntitySet;

    @OneToMany(mappedBy = "parkingLotEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ParkingLotReportEntity> parkingLotReportEntitySet;

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
                name.equals(that.name) &&
                parkingLotTypeEntity.equals(that.parkingLotTypeEntity) &&
                latitude.equals(that.latitude) &&
                longitude.equals(that.longitude) &&
                address.equals(that.address) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(isAvailable, that.isAvailable) &&
                Objects.equals(version, that.version) &&
                Objects.equals(parkingLotEmployeeEntity, that.parkingLotEmployeeEntity) &&
                Objects.equals(parkingLotInformationEntity, that.parkingLotInformationEntity) &&
                Objects.equals(parkingLotUnitEntitySet, that.parkingLotUnitEntitySet) &&
                Objects.equals(parkingLotRatingEntitySet, that.parkingLotRatingEntitySet) &&
                Objects.equals(parkingLotReportEntitySet, that.parkingLotReportEntitySet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                name,
                parkingLotTypeEntity,
                latitude,
                longitude,
                address,
                phone,
                isAvailable,
                version,
                parkingLotEmployeeEntity,
                parkingLotInformationEntity,
                parkingLotUnitEntitySet,
                parkingLotRatingEntitySet,
                parkingLotReportEntitySet);
    }
}