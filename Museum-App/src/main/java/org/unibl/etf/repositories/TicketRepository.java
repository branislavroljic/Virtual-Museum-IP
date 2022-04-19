package org.unibl.etf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.models.entities.TicketEntity;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
    boolean existsTicketEntityByVirtualVisitIdAndUserIdAndNumber(Integer visitId, Integer userId, String number);
}
