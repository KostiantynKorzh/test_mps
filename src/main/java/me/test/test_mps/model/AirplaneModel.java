package me.test.test_mps.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("airplanes")
public class AirplaneModel {

    @Id
    private Long id;

    private AirplaneCharacteristicsModel airplaneCharacteristics;

    private TemporaryPointModel position;

    private List<FlightModel> flights;

}
