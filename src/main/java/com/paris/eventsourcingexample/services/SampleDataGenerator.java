package com.paris.eventsourcingexample.services;

import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;

public interface SampleDataGenerator {

    void insertSampleData() throws EntityNotFoundException;

    void clearOrders() throws EntityNotFoundException;

    void clearAllData() throws EntityNotFoundException;

}
