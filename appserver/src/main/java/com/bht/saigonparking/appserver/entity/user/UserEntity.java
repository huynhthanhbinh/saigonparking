package com.bht.saigonparking.appserver.entity.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.saigonparking.appserver.annotation.EmailValidation;
import com.bht.saigonparking.appserver.base.BaseEntity;

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
@NaturalIdCache
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[USER]")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "[ROLE_ID]", referencedColumnName = "[ID]", nullable = false, updatable = false)
    protected UserRoleEntity userRoleEntity;

    @NaturalId
    @Column(name = "[USERNAME]", nullable = false, unique = true, updatable = false)
    protected String username;

    @Column(name = "[PASSWORD]", nullable = false)
    protected String password;

    @EmailValidation
    @Column(name = "[EMAIL]", nullable = false, unique = true)
    protected String email;

    @ColumnDefault("false")
    @Column(name = "[IS_ACTIVATED]")
    protected Boolean isActivated;

    @EqualsAndHashCode.Exclude
    @Column(name = "[LAST_SIGN_IN]")
    protected Timestamp lastSignIn;
}