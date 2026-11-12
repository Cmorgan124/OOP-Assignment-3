package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.example.haunted.model.Inventory;
import com.example.haunted.model.Item;
import com.example.haunted.model.Potion;

class InventoryTest {
	// ARRANGE - Creates an empty inventory before each test to isolate test environments, defines max inventory space as 3
	private Inventory inventory;
	private Potion potion;
	private final int CAPACITY = 3;
	
	@BeforeEach
	void setup() {
		inventory = new Inventory(CAPACITY);
		potion = new Potion("Potion", "+25 health for the player.", 25);
	}
	
	@Test
	void shouldAddItemWhenNotFull() {
		// ACT
		boolean added = inventory.addItem(potion);
		
		// ASSERT
		assertTrue(added);
		assertEquals(1, inventory.getItems().size());
		assertFalse(inventory.isFull());
	}
	
	@Test
	void shouldNotAddItemWhenFull() {
		// ARRANGE - Fill 
		for (int i = 0; i<CAPACITY; i++) {
			inventory.addItem(potion);
		}
		assertTrue(inventory.isFull());
		
		// ACT
		boolean added = inventory.addItem(potion);
		
		// ASSERT
		assertFalse(added);
		assertEquals(3, inventory.getItems().size());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Potion", "potion", "POTION", "pOTIOn"})
	void shouldFindItemInInventoryAndIgnoreCase(String searchName) {
		// Arrange
		inventory.addItem(potion);
		
		// ACT
		Optional<Item> found = inventory.findItem(searchName);
		
		// ASSERT
		assertTrue(found.isPresent());
		assertEquals("Potion", found.get().getName());
		assertTrue(inventory.contains(searchName));
	}
	
	@Test
	void shouldReturnEmptyOptionalWhenItemNotFound() {
		// ACT
		Optional<Item> found = inventory.findItem("Potion");
		
		// ASSERT
		assertTrue(found.isEmpty());
		assertFalse(inventory.contains("Potion"));
	}
	
	@Test
	void shouldRemoveItemWhenPresent() {
		// ARRANGE
		inventory.addItem(potion);
		
		// ACT
		Item removed = inventory.removeItem("Potion");
		
		// ASSERT
		assertEquals("Potion", removed.getName());
		assertEquals(0, inventory.getItems().size());
	}
	
	@Test
	void shouldReturnCorrectCapacity() {
		assertEquals(CAPACITY, inventory.getCapacity());
	}
}