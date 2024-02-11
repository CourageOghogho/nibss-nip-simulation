package dot.ai.dotnibssmoc.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
public class Bank extends BaseEntity{
    private String name;
    private String bankCode;
    private Integer userId;
    private String creditImpactCallBackUrl;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "bank")
    private Wallet wallet;

    @Override
    public String toString() {
        return "Bank{" +
                "name='" + name + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", creditImpactCallBackUrl='" + creditImpactCallBackUrl + '\'' +
                '}';
    }

}
