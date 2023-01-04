package me.test.test_mps.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemporaryPointModel {

    private Double latitude;

    private Double longitude;

    private Double currentHeight;

    private Double currentSpeed;

    private Double course;

}
