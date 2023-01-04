package me.test.test_mps.service;

import me.test.test_mps.model.AirplaneCharacteristicsModel;
import me.test.test_mps.model.TemporaryPointModel;
import me.test.test_mps.model.WayPointModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    public static final Double ONE_LONGITUDE_OR_LATITUDE_DISTANCE = 111000D;

    public List<TemporaryPointModel> calculateRoute(AirplaneCharacteristicsModel characteristics,
                                                    List<WayPointModel> wayPoints) {

        double angle = Math.toDegrees(Math.atan2(wayPoints.get(1).getLongitude() - wayPoints.get(0).getLongitude(),
                wayPoints.get(1).getLatitude() - wayPoints.get(0).getLatitude()));
        List<TemporaryPointModel> routeBetweenTwoFirstWayPoint = calculateRouteBetweenTwoWayPoints(characteristics, wayPoints.get(0), wayPoints.get(1), angle);
        List<TemporaryPointModel> temporaryPoints = new ArrayList<>(routeBetweenTwoFirstWayPoint);
        for (int i = 1; i < wayPoints.size() - 1; i++) {
            // because last point could be not exactly the way point so we are using last one from previous path route
            TemporaryPointModel lastPoint = temporaryPoints.get(temporaryPoints.size() - 1);
            WayPointModel wayPointMadeFromLastRoutePath = new WayPointModel(lastPoint.getLatitude(), lastPoint.getLongitude(), lastPoint.getCurrentHeight(), lastPoint.getCurrentSpeed());
            List<TemporaryPointModel> routeBetweenTwoWayPoint = calculateRouteBetweenTwoWayPoints(characteristics, wayPointMadeFromLastRoutePath, wayPoints.get(i + 1), lastPoint.getCourse());
            temporaryPoints.addAll(routeBetweenTwoWayPoint);
        }

        return temporaryPoints;
    }

    public List<TemporaryPointModel> calculateRouteBetweenTwoWayPoints(AirplaneCharacteristicsModel characteristics,
                                                                       WayPointModel wayPoint1, WayPointModel wayPoint2, double angle) {

        double targetAngle = calculateTargetAngle(wayPoint2.getLongitude(), wayPoint1.getLongitude(), wayPoint2.getLatitude(), wayPoint1.getLatitude());

        List<TemporaryPointModel> temporaryPoints = new ArrayList<>();
        temporaryPoints.add(new TemporaryPointModel(wayPoint1.getLatitude(), wayPoint1.getLongitude(),
                wayPoint1.getHeight(), wayPoint1.getSpeed(), angle));

        double targetDistance = calculateDistance(wayPoint2.getLongitude(), wayPoint1.getLongitude(), wayPoint2.getLatitude(), wayPoint1.getLatitude());

        Double newSpeed = getNewSpeed(wayPoint1.getSpeed(), wayPoint2.getSpeed(), characteristics.getMaxSpeedAcceleration());
        Double newHeight = getNewHeight(wayPoint1.getHeight(), wayPoint2.getHeight(), characteristics.getMaxHeightChangeSpeed());
        angle = getNewAngle(angle, targetAngle, characteristics.getMaxCourseChangeSpeed());
        Double newLat = getNewLat(wayPoint1.getLatitude(), newSpeed, angle);
        Double newLong = getNewLong(wayPoint1.getLongitude(), newSpeed, angle);


        double distanceFlown = calculateDistance(newLong, wayPoint1.getLongitude(), newLat, wayPoint1.getLatitude());

        TemporaryPointModel temporaryPoint = new TemporaryPointModel(newLat, newLong, newHeight, newSpeed, angle);
        temporaryPoints.add(temporaryPoint);

        double latDif = Double.MAX_VALUE;
        double longDif = Double.MAX_VALUE;


        while (Math.abs(Math.sqrt(latDif * latDif + longDif * longDif)) > characteristics.getMaxSpeed() / ONE_LONGITUDE_OR_LATITUDE_DISTANCE && Math.abs(targetDistance - distanceFlown) >= characteristics.getMaxSpeed() + 1) {
            targetAngle = calculateTargetAngle(wayPoint2.getLongitude(), temporaryPoint.getLongitude(), wayPoint2.getLatitude(), temporaryPoint.getLatitude());
            newSpeed = getNewSpeed(temporaryPoint.getCurrentSpeed(), wayPoint2.getSpeed(), characteristics.getMaxSpeedAcceleration());
            newHeight = getNewHeight(temporaryPoint.getCurrentHeight(), wayPoint2.getHeight(), characteristics.getMaxHeightChangeSpeed());
            angle = getNewAngle(angle, targetAngle, characteristics.getMaxCourseChangeSpeed());
            newLat = getNewLat(temporaryPoint.getLatitude(), newSpeed, angle);
            newLong = getNewLong(temporaryPoint.getLongitude(), newSpeed, angle);

            distanceFlown += calculateDistance(newLong, temporaryPoint.getLongitude(), newLat, temporaryPoint.getLatitude());

            temporaryPoint = new TemporaryPointModel(newLat, newLong, newHeight, newSpeed, angle);
            temporaryPoints.add(temporaryPoint);

            latDif = Math.abs(newLat - wayPoint2.getLatitude());
            longDif = Math.abs(newLong - wayPoint2.getLongitude());
        }

        return temporaryPoints;
    }

    private Double calculateTargetAngle(Double wayPoint2Long, Double wayPoint1Long, Double wayPoint2Lat, Double wayPoint1Lat) {
        return Math.toDegrees(Math.atan2(wayPoint2Long - wayPoint1Long,
                wayPoint2Lat - wayPoint1Lat));
    }

    private Double getNewSpeed(double prevSpeed, double targetSpeed, double maxSpeedAcceleration) {
        return updateAirplaneChars(prevSpeed, targetSpeed, maxSpeedAcceleration);
    }

    private Double getNewHeight(double prevHeight, double targetHeight, double maxHeightSpeed) {
        return updateAirplaneChars(prevHeight, targetHeight, maxHeightSpeed);
    }

    private Double getNewAngle(double prevAngle, double targetAngle, double maxCourseChangeSpeed) {
        return updateAirplaneChars(prevAngle, targetAngle, maxCourseChangeSpeed);
    }

    private Double updateAirplaneChars(double prevValue, double targetValue, double maxCharChangeSpeed) {
        Double newValue = prevValue;
        if (targetValue > prevValue) {
            double angleDif = targetValue - prevValue;
            newValue += Math.min(angleDif, maxCharChangeSpeed);
        } else if (targetValue < prevValue) {
            double angleDif = prevValue - targetValue;
            newValue -= Math.min(angleDif, maxCharChangeSpeed);
        }

        return newValue;
    }

    private Double getNewLat(double prevLat, double speed, double angle) {
        return prevLat + (speed * Math.cos(Math.toRadians(angle))) / ONE_LONGITUDE_OR_LATITUDE_DISTANCE;
    }

    private Double getNewLong(double prevLong, double speed, double angle) {
        return prevLong + (speed * Math.sin(Math.toRadians(angle))) / ONE_LONGITUDE_OR_LATITUDE_DISTANCE;
    }

    private Double calculateDistance(Double wayPoint2Long, Double wayPoint1Long, Double wayPoint2Lat, Double wayPoint1Lat) {
        return Math.sqrt(Math.pow(wayPoint2Long - wayPoint1Long, 2) +
                Math.pow(wayPoint2Lat - wayPoint1Lat, 2)) * ONE_LONGITUDE_OR_LATITUDE_DISTANCE;
    }
}
