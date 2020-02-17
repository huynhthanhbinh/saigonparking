package com.bht.parkingmap.entity.user;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.parkingmap.entity.parkinglot.ParkingLotRatingEntity;
import com.bht.parkingmap.entity.parkinglot.ParkingLotSuggestionEntity;
import com.bht.parkingmap.entity.report.ReportEntity;

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
@Table(name = "[CUSTOMER]")
@Entity(name = "CUSTOMER")
@PrimaryKeyJoinColumn(name = "[CUSTOMER_ID]")
public final class CustomerEntity extends UserEntity {

    @Column(name = "[FIRST_NAME]", nullable = false)
    private String firstName;

    @Column(name = "[LAST_NAME]", nullable = false)
    private String lastName;

    @Column(name = "[PHONE]", nullable = false)
    private String phone;

    @Column(name = "[LAST_UPDATED]")
    private Timestamp lastUpdated;

    @OneToMany(mappedBy = "customerEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ParkingLotRatingEntity> parkingLotRatingEntitySet;

    @OneToMany(mappedBy = "customerEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ParkingLotSuggestionEntity> parkingLotSuggestionEntitySet;

    @OneToMany(mappedBy = "customerEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ReportEntity> reportEntitySet;

    @Setter
    @ToString
    @NoArgsConstructor
    @Accessors(chain = true)
    public static final class Builder {
        private Long id;
        private UserRoleEntity userRoleEntity;
        private String username;
        private String password;
        private String email;
        private Boolean isActivated;
        private Timestamp lastSignIn;
        private Long version;

        private String firstName;
        private String lastName;
        private String phone;
        private Timestamp lastUpdated;
        private Set<ParkingLotRatingEntity> parkingLotRatingEntitySet;
        private Set<ParkingLotSuggestionEntity> parkingLotSuggestionEntitySet;
        private Set<ReportEntity> reportEntitySet;

        public final CustomerEntity build() {
            return (CustomerEntity) new CustomerEntity(firstName, lastName, phone, lastUpdated,
                    parkingLotRatingEntitySet, parkingLotSuggestionEntitySet, reportEntitySet)
                    .setId(id)
                    .setUserRoleEntity(userRoleEntity)
                    .setUsername(username)
                    .setPassword(password)
                    .setEmail(email)
                    .setIsActivated(isActivated)
                    .setLastSignIn(lastSignIn)
                    .setVersion(version);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return new Builder()
                .setId(id)
                .setUserRoleEntity(userRoleEntity)
                .setUsername(username)
                .setPassword(password)
                .setEmail(email)
                .setIsActivated(isActivated)
                .setLastSignIn(lastSignIn)
                .setVersion(version)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPhone(phone)
                .setLastUpdated(lastUpdated)
                .setParkingLotRatingEntitySet(parkingLotRatingEntitySet)
                .setParkingLotSuggestionEntitySet(parkingLotSuggestionEntitySet)
                .setReportEntitySet(reportEntitySet);
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

        CustomerEntity that = (CustomerEntity) o;
        return firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                phone.equals(that.phone) &&
                Objects.equals(parkingLotRatingEntitySet, that.parkingLotRatingEntitySet) &&
                Objects.equals(parkingLotSuggestionEntitySet, that.parkingLotSuggestionEntitySet) &&
                Objects.equals(reportEntitySet, that.reportEntitySet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                firstName,
                lastName,
                phone,
                parkingLotRatingEntitySet,
                parkingLotSuggestionEntitySet,
                reportEntitySet);
    }
}