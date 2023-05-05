package org.example.ENSHotelservice;

import org.example.exception.RoomAlreadyRegisteredException;
import org.example.room.Room;
import org.example.roompricingservice.RoomPricingService;

import java.util.ArrayList;
import java.util.List;

public class ENSHotelImpl implements ENSHotel{
    private final List<Room> availableRooms = new ArrayList<>();
    private final RoomPricingService roomPricingService;

    public ENSHotelImpl(RoomPricingService roomPricingService) {
        this.roomPricingService = roomPricingService;
    }

    public List<Room> getAvailableRooms() {
        return availableRooms;
    }

    @Override
    public void addRoom(Room room) throws RoomAlreadyRegisteredException {
        if (availableRooms.contains(room))
            throw new RoomAlreadyRegisteredException("Room is already available for booking");

        availableRooms.add(room);
    }

    @Override
    public void bookRoom(Room room, int numOfGuests, boolean includeBreakfast) {
        // If room is not available
        if (!availableRooms.contains(room)) return;

        // If the number of the guests are greater than the room capacity
        if (numOfGuests > room.getRoomCapacity()) return;

        // If the room available and, or already holds a number of guests
        room.setNumberOfGuests(numOfGuests);
        room.setReceivesBreakfast(includeBreakfast);

    }

    @Override
    public int totalGuestsForToday() {
        int totalGuests = 0;
        for (Room room : availableRooms) {
            totalGuests += room.getNumberOfGuests();
        }

        return totalGuests;
    }

    @Override
    public double totalValueOfBookingsForToday() {
        int totalValueOfBookings = 0;
        for (Room room :availableRooms) {
            if (room.getNumberOfGuests() == 0) continue;
            if (room.doesPriceIncludesBreakfast()) {
                totalValueOfBookings += roomPricingService.price(room.getNumberOfGuests());
                continue;
            }

            totalValueOfBookings += roomPricingService.price(room.getNumberOfGuests(), room.doesReceivesBreakfast());

        }
        return totalValueOfBookings;
    }
}
