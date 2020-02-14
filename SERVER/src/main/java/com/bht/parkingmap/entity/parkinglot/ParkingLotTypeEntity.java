package com.bht.parkingmap.entity.parkinglot;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

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
@Table(name = "PARKING_LOT_TYPE")
@Entity(name = "PARKING_LOT_TYPE")
public final class ParkingLotTypeEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "TYPE", nullable = false)
    private String type;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @OneToMany(mappedBy = "parkingLotTypeEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ParkingLotEntity> parkingLotEntitySet;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParkingLotTypeEntity that = (ParkingLotTypeEntity) o;
        return Objects.equals(id, that.id) &&
                type.equals(that.type) &&
                Objects.equals(version, that.version) &&
                Objects.equals(parkingLotEntitySet, that.parkingLotEntitySet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                type,
                version,
                parkingLotEntitySet);
    }
}