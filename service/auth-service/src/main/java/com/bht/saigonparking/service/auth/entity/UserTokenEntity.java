package com.bht.saigonparking.service.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@NaturalIdCache
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[USER_TOKEN]")
public final class UserTokenEntity {

    @Id
    @Column(name = "[USER_ID]")
    private Long userId;

    @Length(max = 40)
    @NaturalId(mutable = true)
    @Column(name = "[TOKEN_ID]", unique = true, nullable = false)
    private String tokenId;

    @Version
    @EqualsAndHashCode.Exclude
    private Long version;
}