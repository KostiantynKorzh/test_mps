package me.test.test_mps.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WayPointModel {

    private Double latitude;

    private Double longitude;

    private Double height;

    private Double speed;

}
