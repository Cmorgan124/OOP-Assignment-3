package engine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.haunted.engine.MovementEngine;
import com.example.haunted.events.MoveResult;
import com.example.haunted.model.Direction;
import com.example.haunted.model.Inventory;
import com.example.haunted.model.Player;
import com.example.haunted.model.Room;
import com.example.haunted.model.Trap;
import com.example.haunted.model.TrapType;
import com.example.haunted.rules.TrapResolver;

class MovementEngineTest {

	MovementEngine movementEngine;
	Player player;
	Room roomA;
	Room roomB;
	
	@BeforeEach
	void setup() {
		movementEngine = new MovementEngine(new TrapResolver());
		player = new Player("MTestHero", 50, 10, 5, new Inventory(8));
		
		roomA = new Room("Room A", "Start room", "Room that MTestHero starts in.");
		roomB = new Room("Room B", "Next room", "Room that MTestHero goes to.");
		
		roomA.connect(Direction.NORTH, roomB);
		player.setCurrentRoom(roomA);
	}
	
	@Test
	void testSuccessfulMovement() {
		MoveResult result = movementEngine.move(player, Direction.NORTH);
		assertTrue(result.isSuccess(), "Movement should be successful.");
		assertEquals(result.getMessage(), "Moved to Next room.","Movement result should have a message.");
		assertEquals(roomB, player.getCurrentRoom(), "Player should be in roomB.");
		assertFalse(result.isTrapTriggered(), "Trap should not be triggered.");
	}

	@Test
	void testInvalidMovement() {
		MoveResult result = movementEngine.move(player, Direction.SOUTH);
		assertFalse(result.isSuccess(), "Movement should be invalid.");
		assertEquals("There is no room in that direction.", result.getMessage());
		assertEquals(roomA, player.getCurrentRoom(), "Player shoud be in roomA still.");
	}
	
	@Test
	void testMovementLockedRoomNoKey(){
		roomB.setLocked(true, "Test key");
		MoveResult result = movementEngine.move(player, Direction.NORTH);
		
		assertFalse(result.isSuccess(), "Movement should fail without key.");
		assertEquals("The room is locked.", result.getMessage());
		assertEquals(roomA, player.getCurrentRoom(), "Player should stay in Room A when locked.");
	}
	
	@Test
	void testMovementLockedRoomHasKey() {
		roomB.setLocked(true, "Test key");
		boolean unlocked = roomB.unlock("Test key");
		
		assertTrue(unlocked, "Should successfully unlock the room.");
		
		MoveResult result = movementEngine.move(player, Direction.NORTH);
		
		assertTrue(result.isSuccess(), "Should move to roomB.");
		assertEquals(roomB, player.getCurrentRoom(), "Should be in roomB");
	}
	
	@Test
	void testArmedTrap(){
		Trap testTrap = new Trap("testTrap", TrapType.STEAM, 8, true,true);
		roomB.setTrap(testTrap);
		int initialHealth = player.getHealth();
		MoveResult result = movementEngine.move(player, Direction.NORTH);
		
		assertTrue(result.isSuccess(), "Should still complete move when trap is triggered.");
		assertTrue(result.isTrapTriggered(), "Trap should be triggered.");
		assertEquals(result.getTrapDamage(), 8, "Trap should've done 8 damage.");
		assertEquals(initialHealth - 8, player.getHealth(), "Trap should've done 8 damage to player.");
		assertEquals(roomB, player.getCurrentRoom(), "Player should be in roomB.");
		assertFalse(testTrap.isArmed(), "Should be disarmed since it is a one-time trap.");
	}
	
	@Test
	void testDisarmedTrap() {
		Trap testTrap = new Trap("testTrap", TrapType.STEAM, 8, false,true);
		roomB.setTrap(testTrap);
		int initialHealth = player.getHealth();
		MoveResult result = movementEngine.move(player, Direction.NORTH);
		
		assertTrue(result.isSuccess(), "Should've moved rooms.");
		assertFalse(result.isTrapTriggered(), "Trap shouldn't have been triggered.");
		assertEquals(initialHealth, player.getHealth(), "Player should still be at full health.");
	}
}
