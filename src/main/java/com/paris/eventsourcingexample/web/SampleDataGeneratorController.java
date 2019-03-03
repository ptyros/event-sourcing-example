package com.paris.eventsourcingexample.web;

import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;
import com.paris.eventsourcingexample.services.SampleDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sample-data-generator")
public class SampleDataGeneratorController implements SampleDataGenerator {

    private final SampleDataGenerator sampleDataGenerator;

    @Autowired
    public SampleDataGeneratorController(
            SampleDataGenerator sampleDataGenerator) {
        this.sampleDataGenerator = sampleDataGenerator;
    }

    @PostMapping
    public void insertSampleData() throws EntityNotFoundException {
        sampleDataGenerator.insertSampleData();
    }

    @DeleteMapping(value = "/orders")
    public void clearOrders() throws EntityNotFoundException {
        sampleDataGenerator.clearOrders();
    }

    @DeleteMapping
    public void clearAllData() throws EntityNotFoundException {
        sampleDataGenerator.clearAllData();
    }
}
