package com.bankmisr.automaticirrigationsystem.model.entities;

import com.bankmisr.automaticirrigationsystem.common.model.entity.AuditBaseEntity;
import com.bankmisr.automaticirrigationsystem.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "slot")
@Where(clause = "deleted <> true")
@SQLDelete(sql = "UPDATE slot SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Slot extends AuditBaseEntity {

    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "searchable_text_ar", length = 10000000)
    private String searchableTextAr;

    @Column(name = "searchable_text_en", length = 10000000)
    private String searchableTextEn;

    @Column(name = "water_required")
    private Long waterRequired;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.INACTIVE;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plot_id")
    @JsonProperty("plot")
    private Plot plot;

}
