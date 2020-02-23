package com.bht.parkingmap.entity.report;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.parkingmap.entity.user.CustomerEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *
 * @author bht
 */
@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[WRONG_PARKING_REPORT]")
@PrimaryKeyJoinColumn(name = "[ID]")
public final class WrongParkingReportEntity extends ReportEntity {

    @Column(name = "[REGISTRATION_PLATE]", nullable = false)
    private String registrationPlate;

    @Column(name = "[LATITUDE]", nullable = false)
    private Double latitude;

    @Column(name = "[LONGITUDE]", nullable = false)
    private Double longitude;

    @Setter
    @ToString
    @NoArgsConstructor
    @Accessors(chain = true)
    public static final class Builder {
        private Long id;
        private CustomerEntity customerEntity;
        private ReportTypeEntity reportTypeEntity;
        private Boolean isHandled;
        private Boolean hasPhoto;
        private Timestamp lastUpdated;
        private Long version;

        private String registrationPlate;
        private Double latitude;
        private Double longitude;

        public final WrongParkingReportEntity build() {
            return (WrongParkingReportEntity) new WrongParkingReportEntity(registrationPlate, latitude, longitude)
                    .setId(id)
                    .setCustomerEntity(customerEntity)
                    .setReportTypeEntity(reportTypeEntity)
                    .setIsHandled(isHandled)
                    .setHasPhoto(hasPhoto)
                    .setLastUpdated(lastUpdated)
                    .setVersion(version);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return new Builder()
                .setId(id)
                .setCustomerEntity(customerEntity)
                .setReportTypeEntity(reportTypeEntity)
                .setIsHandled(isHandled)
                .setHasPhoto(hasPhoto)
                .setLastUpdated(lastUpdated)
                .setVersion(version)
                .setRegistrationPlate(registrationPlate)
                .setLatitude(latitude)
                .setLongitude(longitude);
    }

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

        WrongParkingReportEntity that = (WrongParkingReportEntity) o;

        return registrationPlate.equals(that.registrationPlate) &&
                latitude.equals(that.latitude) &&
                longitude.equals(that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                registrationPlate,
                latitude,
                longitude);
    }
}