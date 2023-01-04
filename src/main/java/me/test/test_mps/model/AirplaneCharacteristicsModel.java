package me.test.test_mps.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirplaneCharacteristicsModel {

    private Double maxSpeed;

    private Double maxSpeedAcceleration;

    private Double maxHeightChangeSpeed;

    private Double maxCourseChangeSpeed;

}
