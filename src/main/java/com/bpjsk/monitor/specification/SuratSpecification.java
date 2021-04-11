package com.bpjsk.monitor.specification;

import com.bpjsk.monitor.model.Surat;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SuratSpecification implements Specification<Surat> {

    private QueryBuilderCriteria criteria;

    public SuratSpecification(String field, String operator, String value){
        QueryBuilderCriteria queryBuilderCriteria = new QueryBuilderCriteria();
        queryBuilderCriteria.setField(field);
        queryBuilderCriteria.setOperator(operator);
        queryBuilderCriteria.setValue(value);
        this.criteria=queryBuilderCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Surat> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            final String dateField = "updatedAt,createdAt,tanggalSurat";
            if (dateField.contains(criteria.getField())) {
                date = formatter.parse(criteria.getValue());
                if (criteria.getOperator().equalsIgnoreCase(">")) {
                    return criteriaBuilder.greaterThan(root.get(criteria.getField()), date);
                } else if (criteria.getOperator().equalsIgnoreCase(">=")) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getField()), date);
                } else if (criteria.getOperator().equalsIgnoreCase("<")) {
                    return criteriaBuilder.lessThan(root.get(criteria.getField()), date);
                } else if (criteria.getOperator().equalsIgnoreCase("<=")) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getField()), date);
                } else if (criteria.getOperator().equalsIgnoreCase("!=")) {
                    return criteriaBuilder.notEqual(root.get(criteria.getField()), date);
                } else if (criteria.getOperator().equalsIgnoreCase("=")) {
                    return criteriaBuilder.equal(root.get(criteria.getField()), date);
                }
            } else {
                if (criteria.getOperator().equalsIgnoreCase(">")) {
                    return criteriaBuilder.greaterThan(root.get(criteria.getField()), criteria.getValue());
                } else if (criteria.getOperator().equalsIgnoreCase(">=")) {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getField()), criteria.getValue());
                } else if (criteria.getOperator().equalsIgnoreCase("<")) {
                    return criteriaBuilder.lessThan(root.get(criteria.getField()), criteria.getValue());
                } else if (criteria.getOperator().equalsIgnoreCase("<=")) {
                    return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getField()), criteria.getValue());
                } else if (criteria.getOperator().equalsIgnoreCase("!=")) {
                    return criteriaBuilder.notEqual(root.get(criteria.getField()), criteria.getValue());
                } else if (criteria.getOperator().equalsIgnoreCase("=")) {
                    return criteriaBuilder.equal(root.get(criteria.getField()), criteria.getValue());
                } else if (criteria.getOperator().equalsIgnoreCase("like")) {
                    return criteriaBuilder.like(criteriaBuilder.upper(root.get(criteria.getField())),
                            "%" + criteria.getValue().toUpperCase() + "%");
                } else if (criteria.getOperator().equalsIgnoreCase("!like")) {
                    return criteriaBuilder.notLike(criteriaBuilder.upper(root.get(criteria.getField())),
                            "%" + criteria.getValue().toUpperCase() + "%");
                }else if (criteria.getOperator().equalsIgnoreCase("start")) {
                    return criteriaBuilder.like(root.get(criteria.getField()),  criteria.getValue() + "%");
                } else if (criteria.getOperator().equalsIgnoreCase("end")) {
                    return criteriaBuilder.like(root.get(criteria.getField()),  "%" + criteria.getValue());
                } else if (criteria.getOperator().equalsIgnoreCase("!start")) {
                    return criteriaBuilder.notLike(root.get(criteria.getField()),  criteria.getValue() + "%");
                } else if (criteria.getOperator().equalsIgnoreCase("!end")) {
                    return criteriaBuilder.notLike(root.get(criteria.getField()),  "%" + criteria.getValue());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
