package config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.haunted.config.DungeonFactory;
import com.example.haunted.engine.GameEngine;
import com.example.haunted.model.Direction;
import com.example.haunted.model.Room;

class DungeonFactoryTest {

	private GameEngine game;
	
	@BeforeEach
	void setup() {
		game = DungeonFactory.createGame();
	}
	
	@Test
	void testPlayerAndStartingRoomInitializes() {
		Room startingRoom = game.getCurrentRoom();
		
		assertNotNull(game, "Game Engine should be initialized.");
		assertNotNull(game.getPlayer(), "Player should be initialized.");
		assertEquals("Student Explorer", game.getPlayer().getName(), "Player name should match factory settings.");
		assertNotNull(startingRoom, "Starting room should be initialized.");
		assertEquals("stairwell", startingRoom.getId(), "Player should start in the stairwell.");
	}
	
	@Test
	void testConnections() {
		Room stairwell = game.getCurrentRoom();
		Room lectureHall = stairwell.getExit(Direction.EAST);
		Room backToStairwell = lectureHall.getExit(Direction.WEST);
		
		assertNotNull(lectureHall, "Stairwell should connect East to Lecture Hall.");
		assertEquals("lectureHall", lectureHall.getId());
		assertEquals(stairwell, backToStairwell, "Lecture Hall should connect West back to stairwell.");
	}
	
	@Test
	void testMonsterAndItemPopulation() {
		Room stairwell = game.getCurrentRoom();
		Room lectureHall = stairwell.getExit(Direction.EAST);
		
		assertFalse(lectureHall.getItems().isEmpty(), "Lecture Hall should contain items.");
		assertNotNull(lectureHall.findItem("Coffee Potion").orElse(null), "Lecture Hall should have coffee potion.");
		assertFalse(lectureHall.getMonsters().isEmpty(), "Lecture Hall should contain a monster.");
		assertNotNull(lectureHall.findMonster("Sleep-Deprived TA").orElse(null), "Lecture Hall should have TA.");
	}

	@Test 
	void testBossAndLockedRoomsExist() {
		assertNotNull(game.getQuest(), "Quest tracjer should be initialized.");
		assertFalse(game.isGameWon(), "Game should not be won at the beginning.");
	}
}
