package dot.ai.dotnibssmoc.controller;

import dot.ai.dotnibssmoc.dto.*;
import dot.ai.dotnibssmoc.model.FinancialTransaction;
import dot.ai.dotnibssmoc.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private  final TransactionService transactionService;

    @GetMapping("/name-query")
    public ResponseEntity<BankAccountResponse> nameEnquiry(@RequestBody NameEnquiryRequest request){
        return ResponseEntity.ok(transactionService.nameEnquiry(request));
    }
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transact(@RequestBody TransferRequest request){
        return ResponseEntity.ok(transactionService.acceptTransfer(request));
    }

    @GetMapping("/transfer/status/{ref}")
    public ResponseEntity<?> statusEnquiry(@PathVariable String ref){
        return ResponseEntity.ok(transactionService.transactionStatusEnquiry(ref));
    }

    @GetMapping()
    public ResponseEntity<Page<FinancialTransaction>> getTransactions(TransactionSearchParam queryParam) {
        Page<FinancialTransaction> transactions = transactionService.getTransactions(queryParam);
        return ResponseEntity.ok(transactions);
    }
}
