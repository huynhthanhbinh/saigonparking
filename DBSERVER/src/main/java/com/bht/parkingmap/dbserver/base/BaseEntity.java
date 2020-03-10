package com.bht.parkingmap.dbserver.base;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Parking Map Project Base Entity
 *
 * Mutual fields of each entity is declared in this abstract class, include:
 *      + id: unique id, primary key of each entity
 *      + version: version of each entity, for concurrent control
 *
 * Remember to annotate this abstract class with <code>@MappedSuperclass</code>
 * in order to inherit all of these mutual fields
 * into each child entity, which is annotated with <code>@Entity</code>
 * Also remember to call super on toString(), equals(), hashCode() on each child
 *
 * @author bht
 */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "[ID]")
    protected Long id;

    @Version
    protected Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseEntity that = (BaseEntity) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id);
    }
}