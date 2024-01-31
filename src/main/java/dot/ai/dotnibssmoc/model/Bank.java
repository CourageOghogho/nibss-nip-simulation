package dot.ai.dotnibssmoc.model;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Bank extends BaseEntity{
    private String name;
    private String bankCode;
    private String creditImpactCallBackUrl;

    @OneToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

}
