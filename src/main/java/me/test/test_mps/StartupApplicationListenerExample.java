package me.test.test_mps;

import me.test.test_mps.model.AirplaneModel;
import me.test.test_mps.model.FlightModel;
import me.test.test_mps.service.AirplaneService;
import me.test.test_mps.service.FlightService;
import me.test.test_mps.service.TestService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartupApplicationListenerExample implements
        ApplicationListener<ContextRefreshedEvent> {

    private final AirplaneService airplaneService;

    private final FlightService flightService;

    private final TestService testService;

    public StartupApplicationListenerExample(AirplaneService airplaneService, FlightService flightService, TestService testService) {
        this.airplaneService = airplaneService;
        this.flightService = flightService;
        this.testService = testService;
    }

    private void createTestCase1() {
        AirplaneModel airplane = airplaneService.createTestAirplane1();
        FlightModel flight = flightService.createTestFlight1();
        airplaneService.addFlightToAirplaneAndSave(airplane, flight);
        testService.addTestCase(airplane, flight);
    }

    private void createTestCase2() {
        AirplaneModel airplane = airplaneService.createTestAirplane2();
        FlightModel flight = flightService.createTestFlight2();
        airplaneService.addFlightToAirplaneAndSave(airplane, flight);
        testService.addTestCase(airplane, flight);
    }

    private void createTestCase3() {
        AirplaneModel airplane = airplaneService.createTestAirplane3();
        FlightModel flight = flightService.createTestFlight3();
        airplaneService.addFlightToAirplaneAndSave(airplane, flight);
        testService.addTestCase(airplane, flight);
    }

    private void getInfoAboutPrevFlights() {
        List<FlightModel> flights = flightService.getAllFlightsDoneByAirplanes();
        int numberOfAllDoneFlights = flights.size();
        int durationOfAllDoneFlights = flights.stream()
                .map(FlightModel::getPassedPoints)
                .map(List::size)
                .reduce(0, Integer::sum);

        String durationInfoString = "";
        if (durationOfAllDoneFlights > 60) {
            int minutes = durationOfAllDoneFlights / 60;
            int seconds = durationOfAllDoneFlights % 60;
            durationInfoString = " ( " + minutes + " min and " + seconds + " sec )";
        }

        System.out.println("Previously were done " + numberOfAllDoneFlights
                + " flights with total duration " + durationOfAllDoneFlights + " seconds" + durationInfoString);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        getInfoAboutPrevFlights();
        createTestCase1();
        createTestCase2();
        createTestCase3();
    }
}
