package engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.haunted.engine.InteractionEngine;
import com.example.haunted.rules.QuestTracker;
import com.example.haunted.model.Player;
import com.example.haunted.model.Potion;
import com.example.haunted.model.Key;
import com.example.haunted.model.Inventory;
import com.example.haunted.model.Room;
import com.example.haunted.model.Direction;
import com.example.haunted.events.InteractionResult;
import com.example.haunted.model.Quest;
import com.example.haunted.model.Armor;
import com.example.haunted.model.Weapon;
import com.example.haunted.model.QuestItem;
import com.example.haunted.model.QuestStatus;

class InteractionEngineTest {
	// ASSERT
	private InteractionEngine iEngine;
	private Player player;
	private Room room;
	private Room room2;
	private Potion potion;
	private Quest quest;
	private Key key;
	@BeforeEach
	void setup() {
		QuestTracker questTracker = new QuestTracker();
		iEngine = new InteractionEngine(questTracker);
		Inventory inventory = new Inventory(2);
		player = new Player("Gandalf", 100, 10, 0, inventory);
		room = new Room("testRoom", "Test Room", "This is a test room for testing purposes.");
		player.setCurrentRoom(room);
		room2 = new Room("testRoom2", "Second Test Room", "This is also a test room for testing purposes.");
		key = new Key("Key", "description");
		room2.setLocked(true, "Key");
		room.connect(Direction.EAST, room2);
		potion = new Potion("Potion", "+25 health for the player.", 25);
		quest = new Quest("Name", "Description");
		room.addItem(potion);
		QuestItem lostGradebook = new QuestItem("Lost Gradebook", "description");
		room.addItem(lostGradebook);
		player.getInventory().addItem(potion);
	}
	
	@Test
	void shouldPickUpItemInRoom() {		
		// ACT
		InteractionResult result = iEngine.pickUpItem(player, quest, "Potion");
		
		// ASSERT
		assertTrue(result.isSuccess());
		assertEquals("Picked up Potion.", result.getMessage());
	}
	
	@Test
	void shouldPickUpItemInRoomAndUpdateQuest() {		
		// ACT
		InteractionResult result = iEngine.pickUpItem(player, quest, "Lost Gradebook");
		
		// ASSERT
		assertTrue(result.isSuccess());
		assertEquals("Picked up Lost Gradebook.", result.getMessage());
		assertEquals(QuestStatus.IN_PROGRESS, quest.getStatus());
	}
	
	@Test
	void shouldNotPickUpNullItem() {
		// ACT
		InteractionResult result = iEngine.pickUpItem(player, quest, null);
		
		// ASSERT
		assertFalse(result.isSuccess());
		assertEquals("Item not found in the room.", result.getMessage());
	}
	
	@Test
	void shouldNotPickUpItemWhenInventoryIsFull() {
		// ARRANGE
		player.getInventory().addItem(potion);
		assertTrue(player.getInventory().isFull());
		
		// ACT
		InteractionResult result = iEngine.pickUpItem(player, quest, "Potion");
		
		// ASSERT
		assertFalse(result.isSuccess());
		assertEquals("Inventory is full.", result.getMessage());
		assertTrue(room.findItem("Potion").isPresent());
	}
	
	@Test
	void shouldUsePresentItem() {
		// Arrange
		player.takeDamage(25);
		assertEquals(75, player.getHealth());

		// ACT
		InteractionResult result = iEngine.useItem(player, "Potion");
		
		// ASSERT
		assertTrue(result.isSuccess());
		assertEquals("Used Potion.", result.getMessage());
		assertEquals(0, player.getInventory().getItems().size());
		assertEquals(100, player.getHealth());
		}
	
	@Test
	void shouldNotUseAbsentItem() {
		// ACT
		InteractionResult result = iEngine.useItem(player, "God Potion");
		
		// ASSERT
		assertFalse(result.isSuccess());
		assertEquals("Item not found in inventory.", result.getMessage());
	}
	
	@Test
	void shouldNotUseInvalidItem() {
		// ARRANGE
		player.getInventory().addItem(key);
		assertTrue(player.getInventory().findItem("Key").isPresent());
		
		// ACT
		InteractionResult result = iEngine.useItem(player, "Key");
		
		// ASSERT
		assertFalse(result.isSuccess());
		assertEquals("That item cannot be used.", result.getMessage());
	}
	
	@Test
	void shouldNotEquipInvalidItem() {
		// ACT
		InteractionResult result = iEngine.equipItem(player, "Potion");
		
		// ASSERT
		assertFalse(result.isSuccess());
		assertEquals("That item cannot be equipped.", result.getMessage());
	}
	
	@Test
	void shouldNotEquipNullItem() {
		// ACT
		InteractionResult result = iEngine.equipItem(player, null);
		
		// ASSERT
		assertFalse(result.isSuccess());
		assertEquals("Item not found in inventory.", result.getMessage());
	}
	
	@Test
	void shouldEquipWeapon() {
		// ARRANGE 
		player.getInventory().addItem(new Weapon("Sword", "description", 5));
		
		// ACT
		InteractionResult result = iEngine.equipItem(player, "Sword");
		
		// ASSERT
		assertTrue(result.isSuccess());
		assertEquals("Equipped weapon Sword.", result.getMessage());
		assertEquals("Sword", player.getEquippedWeapon().getName());
	}
	
	@Test
	void shouldEquipArmour() {
		// ARRANGE 
		player.getInventory().addItem(new Armor("Chainmail", "description", 10));
		
		// ACT
		InteractionResult result = iEngine.equipItem(player, "Chainmail");
		
		// ASSERT
		assertTrue(result.isSuccess());
		assertEquals("Equipped armor Chainmail.", result.getMessage());
		assertEquals("Chainmail", player.getEquippedArmor().getName());
	}
	
	@Test
	void shouldUnlockRoom() {
		// ARRANGE
		player.getInventory().addItem(key);
		
		// ACT
		InteractionResult result = iEngine.unlockRoom(player, Direction.EAST);
		
		// ASSERT
		assertTrue(result.isSuccess());
		assertEquals("Unlocked Second Test Room.", result.getMessage());
		assertFalse(room2.isLocked());
	}
	
	@Test
	void shouldNotUnlockInvalidRoom() {
		// ARRANGE
		player.getInventory().addItem(key);
		
		// ACT
		InteractionResult result = iEngine.unlockRoom(player, Direction.NORTH);
		
		// ASSERT
		assertFalse(result.isSuccess());
		assertEquals("There is no room in that direction.", result.getMessage());
	}
	
	@Test
	void shouldNotUnlockUnlockedRoom() {
		// ARRANGE
		player.getInventory().addItem(key);
		iEngine.unlockRoom(player, Direction.EAST);
		
		// ACT
		InteractionResult result = iEngine.unlockRoom(player, Direction.EAST);
		
		// ASSERT
		assertTrue(result.isSuccess());
		assertEquals("The room is already unlocked.", result.getMessage());
	}
	
	@Test
	void shouldNotUnlockUnlockableRoom() {
		// ARRANGE
		Room room3 = new Room("testRoom3", "Third Test Room", "description");
		room.connect(Direction.NORTH, room3);
		room3.setLocked(true, null);
		
		// ACT
		InteractionResult result = iEngine.unlockRoom(player, Direction.NORTH);
		
		// ASSERT
		assertFalse(result.isSuccess());
		assertEquals("The room cannot be unlocked.", result.getMessage());
	}
	
	@Test
	void shouldNotUnlockRoomWithoutKey() {
		// ARRANGE
		Room room3 = new Room("testRoom3", "Third Test Room", "description");
		room.connect(Direction.NORTH, room3);
		room3.setLocked(true, "Skeleton Key");
		
		// ACT
		InteractionResult result = iEngine.unlockRoom(player, Direction.NORTH);
		
		// ASSERT
		assertFalse(result.isSuccess());
		assertEquals("You do not have the correct key.", result.getMessage());
	}
	
	@Test
	void shouldNotUnlockRoomWithoutKeyButSameName() {
		// ARRANGE
		Room room3 = new Room("testRoom3", "Third Test Room", "description");
		room.connect(Direction.NORTH, room3);
		room3.setLocked(true, "Skeleton Key");
		player.getInventory().addItem(new Potion("Skeleton Key", "Not actually a key", 1000));
		
		// ACT
		InteractionResult result = iEngine.unlockRoom(player, Direction.NORTH);
		
		// ASSERT
		assertFalse(result.isSuccess());
		assertEquals("You do not have the correct key.", result.getMessage());
	}
}