package com.bankmisr.automaticirrigationsystem.model.specification;

import com.bankmisr.automaticirrigationsystem.model.entities.Sensor;
import com.bankmisr.automaticirrigationsystem.model.enums.Language;
import com.bankmisr.automaticirrigationsystem.utils.ObjectChecker;
import org.springframework.data.jpa.domain.Specification;

public class SensorSpecification {

    public static Specification<Sensor> id(Long id) {

        return (root, query, builder) -> id == null ? builder.conjunction() : builder.equal(root.get("id"), id);
    }

    public static Specification<Sensor> searchKeyword(String keyword, String language) {

        String searchableField = ObjectChecker.areEqual(language, Language.EN.name().toLowerCase()) ? "searchableTextEn" : "searchableTextAr";
        return (root, query, cb) -> {
            if (ObjectChecker.isEmptyOrNull(keyword))
                return cb.conjunction();
            else {
                return cb.like(cb.lower(root.get(searchableField)), "%" + keyword.toLowerCase().trim() + "%");
            }
        };
    }
}
