package org.example.room;

public enum RoomType {
    DOUBLE(2,false),
     SUITE(3, true),
    TWIN(2, false);


    private final int capacity;
    private final boolean includesBreakfast;

    RoomType(int capacity, boolean includesBreakfast) {
        this.capacity = capacity;
        this.includesBreakfast = includesBreakfast;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isIncludesBreakfast() {
        return includesBreakfast;
    }
}
