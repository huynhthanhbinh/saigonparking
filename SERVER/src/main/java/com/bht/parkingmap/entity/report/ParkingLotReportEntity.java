package com.bht.parkingmap.entity.report;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.parkingmap.entity.parkinglot.ParkingLotEntity;
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
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "PARKING_LOT_REPORT")
@Entity(name = "PARKING_LOT_REPORT")
@PrimaryKeyJoinColumn(name = "PARKING_LOT_REPORT_ID")
public final class ParkingLotReportEntity extends ReportEntity {

    @ManyToOne
    @JoinColumn(name = "PARKING_LOT_ID", referencedColumnName = "ID", nullable = false)
    private ParkingLotEntity parkingLotEntity;

    @Column(name = "VIOLATION", nullable = false)
    private String violation;

    @Setter
    @ToString
    @NoArgsConstructor
    @Accessors(chain = true)
    public static final class Builder {
        private Long id;
        private CustomerEntity customerEntity;
        private ReportTypeEntity reportTypeEntity;
        private Boolean isHandled;
        private String photoPath;
        private Timestamp lastUpdated;
        private Long version;

        private ParkingLotEntity parkingLotEntity;
        private String violation;

        public final ParkingLotReportEntity build() {
            return (ParkingLotReportEntity) new ParkingLotReportEntity(parkingLotEntity, violation)
                    .setId(id)
                    .setCustomerEntity(customerEntity)
                    .setReportTypeEntity(reportTypeEntity)
                    .setIsHandled(isHandled)
                    .setPhotoPath(photoPath)
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
                .setPhotoPath(photoPath)
                .setLastUpdated(lastUpdated)
                .setVersion(version)
                .setParkingLotEntity(parkingLotEntity)
                .setViolation(violation);
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

        ParkingLotReportEntity that = (ParkingLotReportEntity) o;
        return Objects.equals(parkingLotEntity, that.parkingLotEntity) &&
                violation.equals(that.violation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                parkingLotEntity,
                violation);
    }
}