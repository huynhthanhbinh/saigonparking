package com.bht.saigonparking.common.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 *
 * Saigon Parking Project Base Entity
 *
 * Mutual fields of each entity is declared in this abstract class, include:
 *      + id: unique id, primary key of each entity
 *      + version: version of each entity, for concurrent control
 *
 * Remember to annotate this abstract class with <code>@MappedSuperclass</code>
 * in order to inherit all of these mutual fields
 * into each child entity, which is annotated with <code>@Entity</code>
 *
 * Also remember to call super on toString(), equals(), hashCode() on each child !!!!!
 *
 * All mutual fields will be compare as equals() and hashCode(), except id and version
 * because id and version is managed by Hibernate, furthermore id is generated value !
 *
 * @author bht
 */
@Getter
@Setter
@ToString
@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "[ID]")
    @EqualsAndHashCode.Exclude
    protected Long id;

    @Version
    @EqualsAndHashCode.Exclude
    protected Long version;
}