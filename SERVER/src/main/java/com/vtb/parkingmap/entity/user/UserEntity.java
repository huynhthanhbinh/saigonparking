package com.vtb.parkingmap.entity.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import lombok.experimental.Accessors;

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
@Table(name = "USER")
@Entity(name = "USER")
@Accessors(chain = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false)
    protected UserRoleEntity userRoleEntity;

    @Column(name = "USERNAME", nullable = false, unique = true)
    protected String username;

    @Column(name = "PASSWORD", nullable = false)
    protected String password;

    @Column(name = "EMAIL", nullable = false, unique = true)
    protected String email;

    @ColumnDefault("false")
    @Column(name = "IS_ACTIVATED", nullable = false)
    protected Boolean isActivated;

    @Column(name = "LAST_SIGN_IN")
    protected Timestamp lastSignIn;

    @Version
    @Column(name = "VERSION")
    protected Long version;
}