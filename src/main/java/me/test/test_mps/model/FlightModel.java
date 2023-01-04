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
@Document("flights")
public class FlightModel {

    @Id
    private Long number;

    private List<WayPointModel> wayPoints;

    private List<TemporaryPointModel> passedPoints;

}
