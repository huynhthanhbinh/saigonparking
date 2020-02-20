package com.bht.parkingmap.entity.parkinglot;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

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
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[PARKING_LOT_RATING]")
public final class ParkingLotRatingEntity {

    @Id
    @Column(name = "[ID]", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

        ParkingLotRatingEntity that = (ParkingLotRatingEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(customerEntity, that.customerEntity) &&
                Objects.equals(parkingLotEntity, that.parkingLotEntity) &&
                rating.equals(that.rating) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                customerEntity,
                parkingLotEntity,
                rating,
                comment,
                version);
    }
}