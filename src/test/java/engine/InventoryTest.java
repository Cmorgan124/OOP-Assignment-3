package engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import com.example.haunted.model.Player;
import com.example.haunted.model.Inventory;
import com.example.haunted.model.Potion;

class InventoryTest {
	// ARRANGE - Creates a new player with an empty inventory before each test to isolate test environments
	private Player player;
	private Potion potion;
	@BeforeEach
	void setup() {
		Inventory playerInventory = new Inventory(25);
		player = new Player("Sam", 100, 5, 0, playerInventory);
		potion = new Potion("Healing Potion", "Gives +25 health to the player.", 25);
	}
	
	// Happy Path Tests
	@Test
	void shouldAddItemToInventory() {
		// ACT
		boolean result = player.getInventory().addItem(potion);
		
		//ASSERT
		assertTrue(result);
	}
	
	// Edge Case Tests
}