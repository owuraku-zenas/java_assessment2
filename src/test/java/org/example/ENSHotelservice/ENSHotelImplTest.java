package org.example.ENSHotelservice;

import org.example.exception.RoomAlreadyRegisteredException;
import org.example.room.Room;
import org.example.room.RoomType;
import org.example.roompricingservice.RoomPricingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ENSHotelImplTest {
    private ENSHotelImpl hotelService;

    @Mock
    RoomPricingService roomPricingService;
    @BeforeEach
    void setUp() {
        hotelService = new ENSHotelImpl(roomPricingService);
    }

    @Test
    void testSuccessfulAddRoom() {
        Room room = new Room("A52", 3, RoomType.DOUBLE);

        assertDoesNotThrow(() ->hotelService.addRoom(room));
        assertTrue(hotelService.getAvailableRooms().contains(room));
    }

    @Test
    void testAddingSameRoomTwice() throws RoomAlreadyRegisteredException {
        Room room = new Room("A52", 3, RoomType.DOUBLE);
        hotelService.addRoom(room);

        assertTrue(hotelService.getAvailableRooms().contains(room));
        assertThrows(RoomAlreadyRegisteredException.class,() ->hotelService.addRoom(room));
    }

    @Test
    void testSuccessfulBookRoom() throws RoomAlreadyRegisteredException {
        Room room = new Room("A52", 3, RoomType.DOUBLE);
        hotelService.addRoom(room);

        hotelService.bookRoom(room, 2, true);

        assertEquals(2, room.getNumberOfGuests());
    }

    @Test
    void testSuccessfulBookRoomForBookedRoom() throws RoomAlreadyRegisteredException {
        Room room = new Room("A52", 3, RoomType.DOUBLE);
        hotelService.addRoom(room);

        hotelService.bookRoom(room, 2, true);
        int numberOfGuests = room.getNumberOfGuests();
        boolean doesReceivesBreakfast = room.doesReceivesBreakfast();

        hotelService.bookRoom(room, 1, false);

        assertNotEquals(numberOfGuests, room.getNumberOfGuests());
        assertNotEquals(doesReceivesBreakfast, room.doesReceivesBreakfast());
    }

    @Test
    void testNumberOfGuestsGreaterThanCapacity() throws RoomAlreadyRegisteredException {
        Room room = new Room("A52", 3, RoomType.DOUBLE);
        hotelService.addRoom(room);

        int numberOfGuests = room.getNumberOfGuests();
        boolean doesReceivesBreakfast = room.doesReceivesBreakfast();

        hotelService.bookRoom(room, 3, true);

        assertEquals(numberOfGuests, room.getNumberOfGuests());
        assertEquals(doesReceivesBreakfast, room.doesReceivesBreakfast());
    }

    @Test
    void testTotalGuestsForToday() {

        List<Room> rooms = List.of(
            new Room("A52", 3, RoomType.SUITE),
            new Room("A53", 3, RoomType.SUITE),
            new Room("A54", 3, RoomType.SUITE),
            new Room("A55", 3, RoomType.SUITE)
        );


        rooms.forEach((room -> {
            try {
                hotelService.addRoom(room);
            } catch (RoomAlreadyRegisteredException e) {
                throw new RuntimeException(e);
            }
            hotelService.bookRoom(room, 3, true);
        }));


        assertEquals(12, hotelService.totalGuestsForToday());

    }

    @Test
    void testTotalValueOfBookingsForToday() {

        List<Room> rooms = List.of(
                new Room("A52", 3, RoomType.TWIN),
                new Room("A53", 3, RoomType.SUITE),
                new Room("A54", 3, RoomType.SUITE),
                new Room("A55", 3, RoomType.TWIN)
        );

        rooms.forEach((room -> {
            try {
                hotelService.addRoom(room);
            } catch (RoomAlreadyRegisteredException e) {
                throw new RuntimeException(e);
            }
            hotelService.bookRoom(room, 2, true);
        }));


        when(roomPricingService.price(anyInt())).thenReturn(20.0);
        when(roomPricingService.price(anyInt(), anyBoolean())).thenReturn(20.0);

        assertEquals(80, hotelService.totalValueOfBookingsForToday());
    }
}