package me.test.test_mps.service;

import lombok.Data;
import me.test.test_mps.model.AirplaneModel;
import me.test.test_mps.model.FlightModel;
import me.test.test_mps.model.TemporaryPointModel;
import me.test.test_mps.entity.TestCase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class TestService {

    public static final String FLIGHT_INFO_CONSOLE = """
            Current flight %s for airplane with id %s is lat: %f, long: %f, speed: %f, height: %f, course: %f
                    %n
            """;

    public static final String FLIGHT_INFO_CONSOLE_DONE = """
            Current flight %s for airplane with id %s is DONE (lat: %f, long: %f, speed: %f, height: %f, course: %f)
                    %n
            """;

    private List<TestCase> testCases = new ArrayList<>();

    private RouteService routeService;

    private FlightService flightService;

    private AirplaneService airplaneService;

    public TestService(RouteService routeService, FlightService flightService, AirplaneService airplaneService) {
        this.routeService = routeService;
        this.flightService = flightService;
        this.airplaneService = airplaneService;
    }

    public void addTestCase(AirplaneModel airplane, FlightModel flight) {
        List<TemporaryPointModel> routePoints = routeService.calculateRoute(airplane.getAirplaneCharacteristics(), flight.getWayPoints());
        testCases.add(TestCase.builder()
                .airplane(airplane)
                .flight(flight)
                .routePoints(routePoints)
                .build());
    }

    public void proceedWithAllFlights() {
        testCases.forEach(this::proceedWithFlight);
    }

    private void proceedWithFlight(TestCase testCase) {
        AirplaneModel airplane = testCase.getAirplane();
        FlightModel flight = testCase.getFlight();
        if (testCase.getRoutePoints().size() > 0) {
            List<TemporaryPointModel> passedPoint = flight.getPassedPoints();
            passedPoint.add(airplane.getPosition());
            flight.setPassedPoints(passedPoint);
            TemporaryPointModel currentPoint = testCase.getRoutePoints().remove(0);
            airplane.setPosition(currentPoint);
            flightService.save(flight);
            airplaneService.save(airplane);
            System.out.printf(FLIGHT_INFO_CONSOLE, flight.getNumber(), airplane.getId(), airplane.getPosition().getLatitude(), airplane.getPosition().getLongitude(),
                    airplane.getPosition().getCurrentSpeed(), airplane.getPosition().getCurrentHeight(), airplane.getPosition().getCourse());
        } else {
            System.out.printf(FLIGHT_INFO_CONSOLE_DONE, flight.getNumber(), airplane.getId(), airplane.getPosition().getLatitude(), airplane.getPosition().getLongitude(),
                    airplane.getPosition().getCurrentSpeed(), airplane.getPosition().getCurrentHeight(), airplane.getPosition().getCourse());
        }
    }

}
