package dot.ai.dotnibssmoc.utils.setup;

import dot.ai.dotnibssmoc.model.Bank;
import dot.ai.dotnibssmoc.model.Wallet;
import dot.ai.dotnibssmoc.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DBInitLoader implements CommandLineRunner {
    private final BankRepository bankRepository;

    @Override
    public void run(String... args) throws Exception {

        if (bankRepository.findAll().isEmpty()){
            Bank bank1 =new Bank();
            bank1.setBankCode("PAGA");
            bank1.setName("Paga Microfinance");
            bank1.setUserId(1);
            bank1.setCreditImpactCallBackUrl("paga.com/credit/impact");
            Wallet wallet1=new Wallet();
            wallet1.setCommissionBalance(BigDecimal.ZERO);
            wallet1.setBalance(BigDecimal.valueOf(123000000));
            bank1.setWallet(wallet1);
            wallet1.setBank(bank1);

            Bank bank2=new Bank();
            bank2.setBankCode("ACCESS");
            bank2.setName("Access Bank Plc");
            bank2.setUserId(2);
            bank2.setCreditImpactCallBackUrl("accessbank.com/credit/impact");
            Wallet wallet2=new Wallet();
            wallet2.setCommissionBalance(BigDecimal.ZERO);
            wallet2.setBalance(BigDecimal.valueOf(503000000));
            bank2.setWallet(wallet2);
            wallet2.setBank(bank2);

            List<Bank> banks=new ArrayList<>();

            banks.add(bank1);banks.add(bank2);

            bankRepository.saveAll(banks);
        }


    }
}
