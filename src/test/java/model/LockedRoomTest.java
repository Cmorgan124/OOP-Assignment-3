package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import com.example.haunted.model.Room;
import com.example.haunted.model.Key;

class LockedRoomTest {
	// ARRANGE
	private Key key;
	private Room lockedRoom;
	@BeforeEach
	void setup() {
		key = new Key("Key", "Just a normal, ordinary key.");
		lockedRoom = new Room("1", "Locked Room", "This room is locked, you need a key to unlock it.");
		assertEquals("1", lockedRoom.getId());
		assertEquals("Locked Room", lockedRoom.getName());
		assertEquals("This room is locked, you need a key to unlock it.", lockedRoom.getDescription());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"key", "KEY", "kEy"})
	void shouldUnlockAndIgnoreCase(String keyName) {
		// ARRANGE
		lockedRoom.setLocked(true, key.getName());
		assertTrue(lockedRoom.isLocked());
		
		// ACT
		boolean result = lockedRoom.unlock(keyName);
		
		// ASSERT
		assertTrue(result);
		assertFalse(lockedRoom.isLocked());
	}
	
	@Test
	void shouldUnlock() {
		// ACT
		boolean result = lockedRoom.unlock(null);
		
		// ASSERT
		assertTrue(result);
		assertFalse(lockedRoom.isLocked());
	}
	
	@Test
	void shouldNotUnlock() {
		// ARRANGE
		lockedRoom.setLocked(true, key.getName());
		assertTrue(lockedRoom.isLocked());
		
		// ACT
		boolean result = lockedRoom.unlock(null);
		
		// ASSERT
		assertFalse(result);
		assertTrue(lockedRoom.isLocked());
	}
	
	@Test
	void shouldNotUnlockRoomWithNullKey() {
		// ARRANGE
		lockedRoom.setLocked(true, null);
		assertTrue(lockedRoom.isLocked());
		assertEquals(null, lockedRoom.getRequiredKeyName());
		
		// ACT
		boolean result = lockedRoom.unlock(null);
		
		// ASSERT
		assertFalse(result);
		assertTrue(lockedRoom.isLocked());
	}
	
	@Test
	void throwsExceptionWhenIdIsNull() {
		assertThrows(NullPointerException.class, () -> new Room(null, "Name", "Description"));
	}
	
	@Test
	void throwsExceptionWhenNameIsNull() {
		assertThrows(NullPointerException.class, () -> new Room("Id", null, "Description"));
	}

	@Test
	void throwsExceptionWhenDescriptionIsNull() {
		assertThrows(NullPointerException.class, () -> new Room("Id", "Name", null));
	}
}