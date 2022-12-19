package com.bankmisr.automaticirrigationsystem.model.entities;

import com.bankmisr.automaticirrigationsystem.common.model.entity.AuditBaseEntity;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "sensor")
@Where(clause = "deleted <> true")
@SQLDelete(sql = "UPDATE sensor SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sensor extends AuditBaseEntity {

    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "searchable_text_ar", length = 10000000)
    private String searchableTextAr;

    @Column(name = "searchable_text_en", length = 10000000)
    private String searchableTextEn;

    @Column(name = "url")
    private String url;

    @OneToOne(mappedBy = "sensor", cascade = CascadeType.PERSIST)
    private Plot plot;

}
