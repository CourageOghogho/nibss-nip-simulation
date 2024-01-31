package dot.ai.dotnibssmoc.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class BankAccount {
    private Long accountNumber;
    private String name;
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private  Bank bank;

}
