package com.vtb.parkingmap.entity.user;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.SelectBeforeUpdate;

import com.vtb.parkingmap.entity.parkinglot.ParkingLotRatingEntity;
import com.vtb.parkingmap.entity.parkinglot.ParkingLotSuggestionEntity;
import com.vtb.parkingmap.entity.report.ReportEntity;

import lombok.AllArgsConstructor;
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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "CUSTOMER")
@Entity(name = "CUSTOMER")
@PrimaryKeyJoinColumn(name = "CUSTOMER_ID")
public final class CustomerEntity extends UserEntity {

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "PHONE", nullable = false)
    private String phone;

    @Column(name = "LAST_UPDATED")
    private Timestamp lastUpdated;

    @OneToMany(mappedBy = "customerEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ParkingLotRatingEntity> parkingLotRatingEntitySet;

    @OneToMany(mappedBy = "customerEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ParkingLotSuggestionEntity> parkingLotSuggestionEntitySet;

    @OneToMany(mappedBy = "customerEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ReportEntity> reportEntitySet;
}