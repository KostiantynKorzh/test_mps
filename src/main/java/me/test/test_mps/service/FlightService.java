package me.test.test_mps.service;

import me.test.test_mps.model.FlightModel;
import me.test.test_mps.model.WayPointModel;
import me.test.test_mps.repository.AirplaneRepo;
import me.test.test_mps.repository.FlightRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class FlightService {

    private final FlightRepo flightRepo;

    private final AirplaneRepo airplaneRepo;

    private final Random random;

    public FlightService(FlightRepo flightRepo, AirplaneRepo airplaneRepo) {
        this.flightRepo = flightRepo;
        this.airplaneRepo = airplaneRepo;
        this.random = new Random();
    }

    public FlightModel createTestFlight1() {
        FlightModel flight = new FlightModel();
        WayPointModel wayPoint1 = new WayPointModel(50D, 30D, 100D, 0D);
        WayPointModel wayPoint2 = new WayPointModel(50.005D, 30.003D, 80D, 20D);
        WayPointModel wayPoint3 = new WayPointModel(49.995D, 30.009D, 120D, 18D);

        flight.setWayPoints(new ArrayList<>(Arrays.asList(wayPoint1, wayPoint2, wayPoint3)));
        flight.setPassedPoints(new ArrayList<>());
        flight.setNumber(random.nextLong(1000) * 10);
        return flightRepo.save(flight);
    }

    public FlightModel createTestFlight2() {
        FlightModel flight = new FlightModel();
        WayPointModel wayPoint1 = new WayPointModel(30D, 20D, 40D, 10D);
        WayPointModel wayPoint2 = new WayPointModel(30.003D, 20.003D, 80D, 20D);
        WayPointModel wayPoint3 = new WayPointModel(30.006D, 19.997D, 200D, 35D);

        flight.setWayPoints(new ArrayList<>(Arrays.asList(wayPoint1, wayPoint2, wayPoint3)));
        flight.setPassedPoints(new ArrayList<>());
        flight.setNumber(random.nextLong(1000) * 10);
        return flightRepo.save(flight);
    }

    public FlightModel createTestFlight3() {
        FlightModel flight = new FlightModel();
        WayPointModel wayPoint1 = new WayPointModel(32D, 21D, 5D, 10D);
        WayPointModel wayPoint2 = new WayPointModel(31.995D, 21.003D, 80D, 20D);
        WayPointModel wayPoint3 = new WayPointModel(31.99D, 21.006D, 200D, 35D);

        flight.setWayPoints(new ArrayList<>(Arrays.asList(wayPoint1, wayPoint2, wayPoint3)));
        flight.setPassedPoints(new ArrayList<>());
        flight.setNumber(random.nextLong(1000) * 10);
        return flightRepo.save(flight);
    }

    public FlightModel save(FlightModel flight) {
        return flightRepo.save(flight);
    }
    public List<FlightModel> getAllFlightsDoneByAirplanes() {
        return airplaneRepo.findAll().stream()
                .flatMap(airplane -> airplane.getFlights().stream())
                .toList();
    }

}
