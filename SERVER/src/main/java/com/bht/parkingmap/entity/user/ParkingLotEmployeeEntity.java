package com.bht.parkingmap.entity.user;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.parkingmap.entity.parkinglot.ParkingLotEntity;

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
@Table(name = "[PARKING_LOT_EMPLOYEE]")
@Entity(name = "PARKING_LOT_EMPLOYEE")
@PrimaryKeyJoinColumn(name = "[ID]")
public final class ParkingLotEmployeeEntity extends UserEntity {

    @OneToOne
    @JoinColumn(name = "[PARKING_LOT_ID]", referencedColumnName = "[ID]", nullable = false)
    private ParkingLotEntity parkingLotEntity;

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

        private ParkingLotEntity parkingLotEntity;

        public final ParkingLotEmployeeEntity build() {
            return (ParkingLotEmployeeEntity) new ParkingLotEmployeeEntity(parkingLotEntity)
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
                .setParkingLotEntity(parkingLotEntity);
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

        ParkingLotEmployeeEntity that = (ParkingLotEmployeeEntity) o;
        return Objects.equals(parkingLotEntity, that.parkingLotEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                parkingLotEntity);
    }
}