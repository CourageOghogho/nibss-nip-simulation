package dot.ai.dotnibssmoc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Wallet extends BaseEntity{

    private BigDecimal balance;
    private BigDecimal commissionBalance;
    @OneToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @Override
    public String toString() {
        return "Wallet{" +
                "balance=" + balance +
                ", commissionBalance=" + commissionBalance +
                ", bank=" + bank +
                ", id=" + id +
                ", createdAt=" + createdAt +
                '}';
    }
}
