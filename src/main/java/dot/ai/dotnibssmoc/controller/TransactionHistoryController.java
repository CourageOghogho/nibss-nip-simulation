package dot.ai.dotnibssmoc.controller;

import dot.ai.dotnibssmoc.dto.QueryParam;
import dot.ai.dotnibssmoc.model.TransactionSummary;
import dot.ai.dotnibssmoc.service.TransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transactions/history")
@RequiredArgsConstructor
public class TransactionHistoryController {
    private final TransactionHistoryService transactionHistoryService;

    @GetMapping
    public ResponseEntity<Page<TransactionSummary>> retrieve(QueryParam queryParam){
        return ResponseEntity.ok(transactionHistoryService.getTransactionSummaryByDateRange(queryParam));
    }
}
