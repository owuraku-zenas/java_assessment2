package org.example.room;

import java.util.Objects;

public class Room {
    private final String roomNumber;
    private final int floor;

    private final RoomType roomType;
    private int numberOfGuests;
    private boolean receivesBreakfast;

    public Room(String roomNumber, int floor, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.roomType = roomType;
        this.receivesBreakfast = false;
        this.numberOfGuests = 0;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
    public int getRoomCapacity() {
        return roomType.getCapacity();
    }

    public boolean doesPriceIncludesBreakfast() {
        return roomType.isIncludesBreakfast();
    }

    public boolean doesReceivesBreakfast() {
        return receivesBreakfast;
    }

    public void setReceivesBreakfast(boolean receivesBreakfast) {
        this.receivesBreakfast = receivesBreakfast;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return floor == room.floor && numberOfGuests == room.numberOfGuests && receivesBreakfast == room.receivesBreakfast && roomNumber.equals(room.roomNumber) && roomType == room.roomType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, floor, roomType, numberOfGuests, receivesBreakfast);
    }
}
