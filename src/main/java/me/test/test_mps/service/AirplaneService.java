package me.test.test_mps.service;

import me.test.test_mps.model.AirplaneCharacteristicsModel;
import me.test.test_mps.model.AirplaneModel;
import me.test.test_mps.model.FlightModel;
import me.test.test_mps.model.TemporaryPointModel;
import me.test.test_mps.repository.AirplaneRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AirplaneService {

    private final AirplaneRepo airplaneRepo;

    private final Random random;

    public AirplaneService(AirplaneRepo airplaneRepo) {
        this.airplaneRepo = airplaneRepo;
        this.random = new Random();
    }

    public AirplaneModel addFlightToAirplaneAndSave(AirplaneModel airplane, FlightModel flight) {
        List<FlightModel> flights = airplane.getFlights();
        flights.add(flight);
        airplane.setFlights(flights);
        return airplaneRepo.save(airplane);
    }

    public AirplaneModel createTestAirplane(Double maxSpeed, Double maxSpeedAcceleration, Double maxHeightAcceleration, Double maxCourseAcceleration) {
        AirplaneCharacteristicsModel chars = AirplaneCharacteristicsModel.builder()
                .maxSpeed(maxSpeed)
                .maxSpeedAcceleration(maxSpeedAcceleration)
                .maxHeightChangeSpeed(maxHeightAcceleration)
                .maxCourseChangeSpeed(maxCourseAcceleration)
                .build();
        return airplaneRepo.save(AirplaneModel.builder()
                .id(random.nextLong(10000) * 100)
                .airplaneCharacteristics(chars)
                .flights(new ArrayList<>())
                .position(new TemporaryPointModel())
                .build());
    }

    public AirplaneModel createTestAirplane1() {
        return createTestAirplane(30D, 5D, 2D, 10D);
    }

    public AirplaneModel createTestAirplane2() {
        return createTestAirplane(35D, 10D, 4D, 20D);
    }

    public AirplaneModel createTestAirplane3() {
        return createTestAirplane(15D, 3D, 1D, 15D);
    }

    public AirplaneModel save(AirplaneModel airplane) {
        return airplaneRepo.save(airplane);
    }

}
