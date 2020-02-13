package com.vtb.parkingmap.entity.parkinglot;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SelectBeforeUpdate;

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
@Table(name = "PARKING_LOT_INFORMATION")
@Entity(name = "PARKING_LOT_INFORMATION")
public final class ParkingLotInformationEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "PARKING_LOT_ID", referencedColumnName = "ID", nullable = false)
    private ParkingLotEntity parkingLotEntity;

    @Column(name = "OPENING_HOUR", nullable = false)
    private Time openingHour;

    @Column(name = "CLOSING_HOUR", nullable = false)
    private Time closingHour;

    @ColumnDefault("0")
    @Column(name = "RATING_AVERAGE", nullable = false)
    private Double ratingAverage;

    @ColumnDefault("0")
    @Column(name = "NUMBER_OF_RATING", nullable = false)
    private Short nRating;

    @ColumnDefault("0")
    @Column(name = "AVAILABILITY", nullable = false)
    private Short availableSlot;

    @ColumnDefault("0")
    @Column(name = "CAPACITY", nullable = false)
    private Short totalSlot;

    @Column(name = "PHOTO_PATH")
    private String photoPath;

    @Version
    @Column(name = "VERSION")
    private Long version;
}