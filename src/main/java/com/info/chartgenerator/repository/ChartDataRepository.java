package com.info.chartgenerator.repository;

import com.info.chartgenerator.model.ChartData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartDataRepository extends MongoRepository<ChartData, String> {
}
