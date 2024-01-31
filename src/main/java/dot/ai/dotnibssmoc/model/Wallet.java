package dot.ai.dotnibssmoc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Wallet extends BaseEntity{

    private BigDecimal balance;
    private BigDecimal commissionBalance;
    @OneToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;
}
