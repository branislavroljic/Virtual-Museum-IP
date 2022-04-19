package org.unibl.etf.virtual_bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.virtual_bank.models.entities.TransactionEntity;

public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, Integer> {

}
