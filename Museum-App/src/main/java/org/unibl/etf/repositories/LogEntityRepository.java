package org.unibl.etf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unibl.etf.models.dto.Report;
import org.unibl.etf.models.entities.LogEntity;

import java.util.List;

@Repository
public interface LogEntityRepository extends JpaRepository<LogEntity, Integer> {

    @Query(
            value = "SELECT date(date_time) as 'date', hour(date_time) as 'hour', count(distinct(user)) as 'count' " +
                    "FROM log l WHERE l.type = 'LOGIN' and date_time > DATE_SUB(NOW()," +
                    " INTERVAL 24 HOUR) group by hour(date_time), date(date_time) order by date, hour",
          nativeQuery = true
    )
    List<Report> selectLoginsByHourFromLast24Hour();

    List<LogEntity> findByOrderByDateTimeDesc();
}
