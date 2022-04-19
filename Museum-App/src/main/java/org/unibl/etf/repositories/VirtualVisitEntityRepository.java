package org.unibl.etf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.models.entities.VirtualVisitEntity;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public interface VirtualVisitEntityRepository extends JpaRepository<VirtualVisitEntity, Integer> {

    List<VirtualVisitEntity> findAllByMuseum_IdAndDateIsGreaterThanEqual(Integer museumId, Date date);
}
