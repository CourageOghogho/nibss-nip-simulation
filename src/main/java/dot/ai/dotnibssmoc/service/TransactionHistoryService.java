package dot.ai.dotnibssmoc.service;

import dot.ai.dotnibssmoc.model.TransactionHistory;

import java.util.concurrent.CompletableFuture;

public interface TransactionHistoryService {
    void create(TransactionHistory history);
}
