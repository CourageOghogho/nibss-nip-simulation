package dot.ai.dotnibssmoc.controller;

import dot.ai.dotnibssmoc.dto.BankAccountResponse;
import dot.ai.dotnibssmoc.dto.NameEnquiryRequest;
import dot.ai.dotnibssmoc.dto.TransferRequest;
import dot.ai.dotnibssmoc.dto.TransferResponse;
import dot.ai.dotnibssmoc.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/transfer/status")
    public ResponseEntity<?> statusEnquiry(@PathVariable String transRef){
        return ResponseEntity.ok(transactionService.transactionStatusEnquiry(transRef));
    }
}
