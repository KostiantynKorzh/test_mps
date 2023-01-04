package me.test.test_mps;

import me.test.test_mps.model.AirplaneModel;
import me.test.test_mps.model.FlightModel;
import me.test.test_mps.model.TemporaryPointModel;
import me.test.test_mps.model.WayPointModel;
import me.test.test_mps.service.AirplaneService;
import me.test.test_mps.service.RouteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class TestMpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestMpsApplication.class, args);
    }

}
