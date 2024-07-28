package com.mini.project.courseservice.repository.jpaspecification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
public class QueryCriteria<T> implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;
    private List<String> keyList;
    private T value;
    private CriteriaOperation criteriaOperation;

    public QueryCriteria(String key, T value, CriteriaOperation criteriaOperation) {
        this.value = value;
        this.criteriaOperation = criteriaOperation;
        String[] temps = key.split("\\.");
        keyList = Arrays.asList(temps);
    }

    private Expression<String> buildKeyName(QueryCriteria<?> criteria, Root<?> root) {
        var path = root.get(criteria.keyList.get(0));
        for(int i=1; i<criteria.keyList.size(); i++) {
            path = path.get(criteria.keyList.get(i));
        }
        return path.as(String.class);
    }

    public enum CriteriaOperation {
        GREATER_THAN {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, QueryCriteria<?> criteria, Root<?> root) {
                predicates.add(
                        builder.greaterThan(
                                criteria.buildKeyName(criteria, root),
                                criteria.getValue().toString()
                        )
                );
            }
        },
        LESS_THAN {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, QueryCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.lessThan(
                        criteria.buildKeyName(criteria, root),
                        criteria.getValue().toString()));
            }
        },
        GREATER_THAN_EQUAL {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, QueryCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.greaterThanOrEqualTo(
                        criteria.buildKeyName(criteria, root),
                        criteria.getValue().toString()));
            }
        },
        LESS_THAN_EQUAL {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, QueryCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.lessThanOrEqualTo(
                        criteria.buildKeyName(criteria, root),
                        criteria.getValue().toString()));
            }
        },
        NOT_EQUAL {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, QueryCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.notEqual(
                        criteria.buildKeyName(criteria, root),
                        criteria.getValue().toString()));
            }
        },
        EQUAL {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, QueryCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.equal(
                        criteria.buildKeyName(criteria, root),
                        criteria.getValue().toString()));
            }
        },
        MATCH {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, QueryCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.like(
                        builder.lower(criteria.buildKeyName(criteria, root)),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"));
            }
        },
        MATCH_END {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, QueryCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.like(
                        builder.lower(criteria.buildKeyName(criteria, root) ),
                        criteria.getValue().toString().toLowerCase() + "%"));
            }
        },
        EQUAL_IGNORE_CASE {
            public void getOperation(List<Predicate> predicates, CriteriaBuilder builder, QueryCriteria<?> criteria, Root<?> root) {
                predicates.add(builder.equal(
                        builder.lower(criteria.buildKeyName(criteria, root)),
                        criteria.getValue().toString().toLowerCase()));
            }
        };

        public abstract void getOperation(List<Predicate> predicates, CriteriaBuilder builder, QueryCriteria<?> criteria, Root<?> root);
    }
}
