package com.bht.parkingmap.dbserver.entity.user;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SelectBeforeUpdate;

import com.bht.parkingmap.dbserver.base.BaseEntity;

import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[USER]")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "[ROLE_ID]", referencedColumnName = "[ID]", nullable = false)
    protected UserRoleEntity userRoleEntity;

    @Column(name = "[USERNAME]", nullable = false, unique = true)
    protected String username;

    @Column(name = "[PASSWORD]", nullable = false)
    protected String password;

    @Column(name = "[EMAIL]", nullable = false, unique = true)
    protected String email;

    @ColumnDefault("false")
    @Column(name = "[IS_ACTIVATED]")
    protected Boolean isActivated;

    @Column(name = "[LAST_SIGN_IN]")
    protected Timestamp lastSignIn;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        UserEntity that = (UserEntity) o;

        return userRoleEntity.equals(that.userRoleEntity) &&
                username.equals(that.username) &&
                password.equals(that.password) &&
                email.equals(that.email) &&
                Objects.equals(isActivated, that.isActivated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                userRoleEntity,
                username,
                password,
                email,
                isActivated);
    }
}