package com.vtb.parkingmap.entity.report;

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

import com.vtb.parkingmap.entity.user.CustomerEntity;

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
@Table(name = "REPORT")
@Entity(name = "REPORT")
@Inheritance(strategy = InheritanceType.JOINED)
public class ReportEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "REPORTER_ID", referencedColumnName = "CUSTOMER_ID", nullable = false)
    private CustomerEntity customerEntity;

    @ManyToOne
    @JoinColumn(name = "REPORT_TYPE_ID", referencedColumnName = "ID", nullable = false)
    private ReportTypeEntity reportTypeEntity;

    @ColumnDefault("false")
    @Column(name = "IS_HANDLED", nullable = false)
    private Boolean isHandled;

    @Column(name = "PHOTO_PATH")
    private String photoPath;

    @Column(name = "LAST_UPDATED")
    private Timestamp lastUpdated;

    @Version
    @Column(name = "VERSION")
    private Long version;
}