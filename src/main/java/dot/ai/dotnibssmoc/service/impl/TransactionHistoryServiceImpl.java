package dot.ai.dotnibssmoc.service.impl;

import dot.ai.dotnibssmoc.model.TransactionHistory;
import dot.ai.dotnibssmoc.repository.TransactionRepository;
import dot.ai.dotnibssmoc.service.TransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
@Service
@RequiredArgsConstructor
public class TransactionHistoryServiceImpl implements TransactionHistoryService {
    private final TransactionHistoryRepository transactionHistoryRepository;


    @Override
    public void create(TransactionHistory history) {
        transactionHistoryRepository.save(history);

    }
}
