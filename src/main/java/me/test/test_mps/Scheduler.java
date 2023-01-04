package me.test.test_mps;

import me.test.test_mps.service.TestService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    private final TestService testService;

    public Scheduler(TestService testService) {
        this.testService = testService;
    }

    @Scheduled(fixedRate = 1000)
    public void scheduleFlights() {
        testService.proceedWithAllFlights();
    }

}
