package com.info.chartgenerator.repository;

import com.info.chartgenerator.model.ChartData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class ChartDataRepository {

    private EntityManager em;

    public ChartDataRepository(EntityManager em) {
        this.em = em;
    }

    public ChartData create(ChartData chartData) {
        em.persist(chartData);
        return chartData;
    }

}
