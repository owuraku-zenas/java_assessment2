package org.example.ENSHotelservice;

import org.example.exception.RoomAlreadyRegisteredException;
import org.example.room.Room;

/**
 * Interface used to audit bookings that have been made
 * at the hotel. Rooms can be booked against the number of people
 * staying in a given room.
 * The API is capable of retrieving the total number of guests who have
 * stayed in the hotel for the day, and the total value of all bookings
 * for the day.
 *
 * Every day, the bookings are cleared out and a new audit trail starts.
 */
public interface ENSHotel {

    /**
     * Adds rooms to the system that are available for bookings today.
     * No room should be available to book more than once.
     * @param room add a room available for booking.
     * @throws RoomAlreadyRegisteredException thrown when a room is already available to book.
     */
    void addRoom(Room room) throws RoomAlreadyRegisteredException;

    /**
     * Creates a booking against a room for a single day, today.  If the room is not available
     * to book (it has not been added as a room), no booking is stored. If the room already
     * holds a number of guests (i.e. is already booked for today), then the new booking replaces
     * the old booking. If the number of guests is greater than the room capacity, no booking is
     * stored (or the old booking remains).
     * @param room the room the booking is being made for
     * @param numOfGuests the number of people staying in the room.
     * @param includeBreakfast should this booking include breakfast for all guests
     */
    void bookRoom(Room room, int numOfGuests, boolean includeBreakfast);

    /**
     * Calculates the total number of guests staying in the hotel
     * today so far.
     * @return the total number of guests
     */
    int totalGuestsForToday();

    /**
     * Calculates the total value of all the bookings for today only.
     * This is done by finding rooms that have a guest count of > 0 and
     * requesting a price from one of the vendors. If a room has a guest
     * count of zero, no one has booked the room today.
     *
     * @return the total value of all room bookings for today only.
     */
    double totalValueOfBookingsForToday();
}