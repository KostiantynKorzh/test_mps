package me.test.test_mps.repository;

import me.test.test_mps.model.AirplaneModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AirplaneRepo extends MongoRepository<AirplaneModel, String> {

}
