package com.bht.parkingmap.entity.user;

import java.sql.Timestamp;
import java.util.Objects;

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
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[USER]")
@Accessors(chain = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {

    @Id
    @Column(name = "[ID]", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

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

        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userRoleEntity, that.userRoleEntity) &&
                username.equals(that.username) &&
                password.equals(that.password) &&
                email.equals(that.email) &&
                Objects.equals(isActivated, that.isActivated) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                userRoleEntity,
                username,
                password,
                email,
                isActivated,
                version);
    }
}