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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.parkingmap.entity.user.CustomerEntity;

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
@Table(name = "[PARKING_LOT_SUGGESTION]")
@Entity(name = "PARKING_LOT_SUGGESTION")
public final class ParkingLotSuggestionEntity {

    @Id
    @Column(name = "[ID]", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

        ParkingLotSuggestionEntity that = (ParkingLotSuggestionEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(customerEntity, that.customerEntity) &&
                parkingLotName.equals(that.parkingLotName) &&
                parkingLotAddress.equals(that.parkingLotAddress) &&
                latitude.equals(that.latitude) &&
                longitude.equals(that.longitude) &&
                Objects.equals(isHandled, that.isHandled) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                customerEntity,
                parkingLotName,
                parkingLotAddress,
                latitude,
                longitude,
                isHandled,
                version);
    }
}