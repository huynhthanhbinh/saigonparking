package com.bht.parkingmap.entity.report;

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
@Entity
@Getter
@Setter
@Builder
@ToString(exclude = "reportEntitySet")
@NoArgsConstructor
@AllArgsConstructor
@SelectBeforeUpdate
@Table(name = "[REPORT_TYPE]")
public final class ReportTypeEntity {

    @Id
    @Column(name = "[ID]", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "[TYPE]", nullable = false)
    private String type;

    @Column(name = "[DESCRIPTION]")
    private String description;

    @Version
    private Long version;

    @OneToMany(mappedBy = "reportTypeEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ReportEntity> reportEntitySet;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReportTypeEntity that = (ReportTypeEntity) o;
        return Objects.equals(id, that.id) &&
                type.equals(that.type) &&
                Objects.equals(description, that.description) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                type,
                description,
                version);
    }
}