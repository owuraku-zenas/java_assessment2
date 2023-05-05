package org.example.roompricingservice;

public interface RoomPricingService {
    double price(int numOfGuests);
    double price(int numOfGuests, boolean addBreakfast);
}
