package org.unibl.etf.virtual_bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.virtual_bank.models.entities.BankAccountEntity;
import org.unibl.etf.virtual_bank.models.entities.enums.CardType;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface BankAccountEntityRepository extends JpaRepository<BankAccountEntity, Integer> {
    Optional<BankAccountEntity> findByNameAndSurnameAndCardNumberAndExpirationDateAndCardTypeAndPin(String name, String surname, String cardNumber, Date expirationDate, CardType cardType, String pin);

}
