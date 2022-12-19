package com.bankmisr.automaticirrigationsystem.model.entities;

import com.bankmisr.automaticirrigationsystem.common.model.entity.AuditBaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "plot")
@Where(clause = "deleted <> true")
@SQLDelete(sql = "UPDATE plot SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plot extends AuditBaseEntity{

    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "searchable_text_ar", length = 10000000)
    private String searchableTextAr;

    @Column(name = "searchable_text_en", length = 10000000)
    private String searchableTextEn;

    @JsonBackReference
    @OneToMany(mappedBy = "plot", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Slot> slots = new LinkedHashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

}