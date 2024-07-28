package com.mini.project.courseservice.repository.jpaspecification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuerySpecification<T> implements Specification<T> {

    private List<QueryCriteria<?>> searchCriteriaList;
    private Specification<T> specification;

    public QuerySpecification() {
        this.searchCriteriaList = new ArrayList<>();
    }

    public QuerySpecification(Specification<T> specification) {
        this.searchCriteriaList = new ArrayList<>();
        this.specification = specification;
    }

    public void add(QueryCriteria<?> criteria) {
        if (!Objects.isNull(criteria.getValue())) {
            searchCriteriaList.add(criteria);
        }
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        //create a new predicate list
        List<Predicate> predicates = new ArrayList<>();

        if (this.specification != null) {
            predicates.add(this.specification.toPredicate(root, query, builder));
        }

        //add add criteria to predicates
        searchCriteriaList.forEach(criteria -> criteria.getCriteriaOperation().getOperation(predicates, builder, criteria, root));

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
