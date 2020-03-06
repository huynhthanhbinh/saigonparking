package com.bht.parkingmap.dbserver.entity.parkinglot;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.parkingmap.dbserver.base.BaseEntity;
import com.bht.parkingmap.dbserver.entity.user.CustomerEntity;

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
@Table(name = "[PARKING_LOT_RATING]")
public final class ParkingLotRatingEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "[CUSTOMER_ID]", referencedColumnName = "[ID]", nullable = false)
    private CustomerEntity customerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[PARKING_LOT_ID]", referencedColumnName = "[ID]", nullable = false)
    private ParkingLotEntity parkingLotEntity;

    @Column(name = "[RATING]", nullable = false)
    private Short rating;

    @Column(name = "[COMMENT]")
    private String comment;

    @Column(name = "[LAST_UPDATED]")
    private Timestamp lastUpdated;

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

        ParkingLotRatingEntity that = (ParkingLotRatingEntity) o;

        return customerEntity.equals(that.customerEntity) &&
                parkingLotEntity.equals(that.parkingLotEntity) &&
                rating.equals(that.rating) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                customerEntity,
                parkingLotEntity,
                rating,
                comment);
    }
}