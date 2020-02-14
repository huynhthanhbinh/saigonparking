package com.bht.parkingmap.entity.user;

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
@Table(name = "USER_ROLE")
@Entity(name = "USER_ROLE")
public final class UserRoleEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "ROLE", nullable = false)
    private String role;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @OneToMany(mappedBy = "userRoleEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserEntity> userEntitySet;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserRoleEntity that = (UserRoleEntity) o;
        return Objects.equals(id, that.id) &&
                role.equals(that.role) &&
                Objects.equals(version, that.version) &&
                Objects.equals(userEntitySet, that.userEntitySet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                role,
                version,
                userEntitySet);
    }
}