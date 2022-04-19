package org.unibl.etf.virtual_bank.services.impl;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;
import org.unibl.etf.virtual_bank.exceptions.BlockedException;
import org.unibl.etf.virtual_bank.exceptions.ExpiredException;
import org.unibl.etf.virtual_bank.exceptions.NotEnoughMoneyException;
import org.unibl.etf.virtual_bank.exceptions.NotFoundException;
import org.unibl.etf.virtual_bank.models.entities.BankAccountEntity;
import org.unibl.etf.virtual_bank.models.entities.TransactionEntity;
import org.unibl.etf.virtual_bank.models.requests.PurchaseRequest;
import org.unibl.etf.virtual_bank.repositories.BankAccountEntityRepository;
import org.unibl.etf.virtual_bank.repositories.TransactionEntityRepository;
import org.unibl.etf.virtual_bank.services.BankAccountService;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private static final String TICKET_PAYMENT_DESCRIPTION = "Ticket for museum.";

    private final BankAccountEntityRepository bankAccountEntityRepository;
    private final TransactionEntityRepository transactionEntityRepository;

    public BankAccountServiceImpl(BankAccountEntityRepository bankAccountEntityRepository, TransactionEntityRepository transactionEntityRepository) {
        this.bankAccountEntityRepository = bankAccountEntityRepository;
        this.transactionEntityRepository = transactionEntityRepository;
    }


    @Override
    public void withdrawMoneyFromAccount(PurchaseRequest request) {
        String pinSha256 = Hashing.sha256().hashString(request.getPin(), StandardCharsets.UTF_8).toString();
        BankAccountEntity bankAccount = bankAccountEntityRepository
                .findByNameAndSurnameAndCardNumberAndExpirationDateAndCardTypeAndPin(request.getName(),
                        request.getSurname(), request.getCardNumber(), request.getExpirationDate(),
                        request.getCardType(), pinSha256)
                .orElseThrow(() -> new NotFoundException("Account not found!"));
        if(bankAccount.isBlocked())
            throw new BlockedException("Account is blocked!");
        else if (bankAccount.getExpirationDate().toLocalDate().isBefore(LocalDate.now()))
            throw new ExpiredException("The card has expired!");
        if(bankAccount.getBalance() >= request.getRequestedAmount()){
            bankAccount.setBalance(bankAccount.getBalance() - request.getRequestedAmount());
            bankAccountEntityRepository.save(bankAccount);

            TransactionEntity transaction = new TransactionEntity(new Date(new java.util.Date().getTime()), request.getRequestedAmount(), TICKET_PAYMENT_DESCRIPTION, bankAccount);
            transactionEntityRepository.save(transaction);
        }else throw new NotEnoughMoneyException("You do not have enough money in your account!");
    }
}
