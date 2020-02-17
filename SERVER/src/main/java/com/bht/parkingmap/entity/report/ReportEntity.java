package com.bht.parkingmap.entity.report;

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

import com.bht.parkingmap.entity.user.CustomerEntity;

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
@Table(name = "[REPORT]")
@Entity(name = "REPORT")
@Accessors(chain = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class ReportEntity {

    @Id
    @Column(name = "[ID]", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "[REPORTER_ID]", referencedColumnName = "[ID]", nullable = false)
    protected CustomerEntity customerEntity;

    @ManyToOne
    @JoinColumn(name = "[REPORT_TYPE_ID]", referencedColumnName = "[ID]", nullable = false)
    protected ReportTypeEntity reportTypeEntity;

    @ColumnDefault("false")
    @Column(name = "[IS_HANDLED]", nullable = false)
    protected Boolean isHandled;

    @Column(name = "[PHOTO_PATH]")
    protected String photoPath;

    @Column(name = "[LAST_UPDATED]")
    protected Timestamp lastUpdated;

    @Version
    @Column(name = "[VERSION]")
    protected Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReportEntity that = (ReportEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(customerEntity, that.customerEntity) &&
                Objects.equals(reportTypeEntity, that.reportTypeEntity) &&
                Objects.equals(isHandled, that.isHandled) &&
                Objects.equals(photoPath, that.photoPath) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                customerEntity,
                reportTypeEntity,
                isHandled,
                photoPath,
                version);
    }
}