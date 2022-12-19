package com.bankmisr.automaticirrigationsystem.graphql.filter;

import com.bankmisr.automaticirrigationsystem.common.validator.ValueOfEnum;
import com.bankmisr.automaticirrigationsystem.model.enums.Language;
import com.bankmisr.automaticirrigationsystem.model.enums.SortByEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Setter
@Getter
public class SlotFilter {

    private int offset;

    private int limit;

    private Long id;

    private String language = Language.EN.name().toLowerCase();

    private String keyword;

    @ValueOfEnum(enumClass = SortByEnum.class, message = "must be any of these Values {modifiedDate, createdDate, id}")
    private String sortBy = "modifiedDate";

    @ValueOfEnum(enumClass = Sort.Direction.class, message = "must be any of these Values {DESC ,ASC}")
    private String sortDirection = "DESC";
}
