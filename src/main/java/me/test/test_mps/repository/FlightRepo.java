package me.test.test_mps.repository;

import me.test.test_mps.model.FlightModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightRepo extends MongoRepository<FlightModel, String> {
}
