package me.test.test_mps.entity;

import lombok.Builder;
import lombok.Data;
import me.test.test_mps.model.AirplaneModel;
import me.test.test_mps.model.FlightModel;
import me.test.test_mps.model.TemporaryPointModel;

import java.util.List;

@Data
@Builder
public class TestCase {

    private AirplaneModel airplane;

    private FlightModel flight;

    private List<TemporaryPointModel> routePoints;

}
