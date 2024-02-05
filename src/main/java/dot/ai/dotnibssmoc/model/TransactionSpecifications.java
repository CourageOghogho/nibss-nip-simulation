package dot.ai.dotnibssmoc.model;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class TransactionSpecifications {
    public static Specification<FinancialTransaction> withParameters(
            Optional<String> sourceBankCode,
            Optional<String> benefactorBankCode,
            Optional<String> transRef,
            Optional<String> status,
            Optional<Long> userId,
            Optional<LocalDateTime> startDate,
            Optional<LocalDateTime> endDate) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            var predicate = criteriaBuilder.conjunction();

            sourceBankCode.ifPresent(sb -> predicate.getExpressions().add(criteriaBuilder.equal(root.get("sourceBankCode"), sb)));
            benefactorBankCode.ifPresent(bb -> predicate.getExpressions().add(criteriaBuilder.equal(root.get("benefactorBankCode"), bb)));
            transRef.ifPresent(tf -> predicate.getExpressions().add(criteriaBuilder.equal(root.get("transRef"), tf)));
            status.ifPresent(s -> predicate.getExpressions().add(criteriaBuilder.equal(root.get("status"), s)));
            userId.ifPresent(id -> predicate.getExpressions().add(criteriaBuilder.equal(root.get("userId"), id)));
            startDate.ifPresent(date -> predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), date)));
            endDate.ifPresent(date -> predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), date)));

            return predicate;
        };
    }
}
