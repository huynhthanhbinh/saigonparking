package com.bht.parkingmap.appserver.entity.user;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.parkingmap.appserver.entity.parkinglot.ParkingLotRatingEntity;
import com.bht.parkingmap.appserver.entity.parkinglot.ParkingLotSuggestionEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[CUSTOMER]")
@PrimaryKeyJoinColumn(name = "[ID]")
public final class CustomerEntity extends UserEntity {

    @Column(name = "[FIRST_NAME]", nullable = false)
    private String firstName;

    @Column(name = "[LAST_NAME]", nullable = false)
    private String lastName;

    @Column(name = "[PHONE]", nullable = false)
    private String phone;

    @EqualsAndHashCode.Exclude
    @Column(name = "[LAST_UPDATED]")
    private Timestamp lastUpdated;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "customerEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ParkingLotSuggestionEntity> parkingLotSuggestionEntitySet;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "customerEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ParkingLotRatingEntity> parkingLotRatingEntitySet;
}