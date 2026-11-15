package engine;
import com.example.haunted.engine.CombatEngine;
import com.example.haunted.events.CombatResult;
import com.example.haunted.model.BossMonster;
import com.example.haunted.model.Inventory;
import com.example.haunted.model.Monster;
import com.example.haunted.model.Player;
import com.example.haunted.model.Potion;
import com.example.haunted.model.Quest;
import com.example.haunted.model.Room;
import com.example.haunted.rules.DamageCalculator;
import com.example.haunted.rules.QuestTracker;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class CombatEngineTest {

	private CombatEngine combatEngine;
	private Quest quest;
	private Room room;
	private Player player;
	
	@BeforeEach
	void setup() {
		combatEngine = new CombatEngine(new DamageCalculator(), new QuestTracker());
		player = new Player("TestHero", 50, 7, 2, new Inventory(8));
		quest = new Quest("Escape", "Defeat the monster.");
		room = new Room("room1", "Chamber", "A dark chamber.");
		player.setCurrentRoom(room);
	}
	
	@Test
	void testAttackNullMonster() {
		CombatResult result = combatEngine.attack(player, quest, null);
		
		assertFalse(result.isSuccess());
		assertEquals("Monster not found.", result.getMessage());
	}
	
	@Test
	void testAttackWhenPlayerDead() {
		player.takeDamage(50);
		Monster monster = new Monster("TestMonster", 20, 5, 2, List.of());
		CombatResult result = combatEngine.attack(player, quest, monster);
		
		assertFalse(result.isSuccess());
		assertEquals("Player is defeated.", result.getMessage());
	}

	@Test
	void testAttackWhenMonsterDead() {
		Monster monster = new Monster("TestMonster", 10, 5, 2, List.of());
		monster.takeDamage(10);
		CombatResult result = combatEngine.attack(player, quest, monster);
		
		assertFalse(result.isSuccess());
		assertTrue(result.isMonsterDefeated());
		assertEquals("Monster is already defeated.", result.getMessage());
	}
	
	@Test
	void testCounterAttack() {
		Monster monster = new Monster("TestMonster", 20, 5, 2, List.of());
		CombatResult result = combatEngine.attack(player, quest, monster);
		
		assertTrue(result.isSuccess());
		assertEquals("Attacked TestMonster.", result.getMessage());
		assertFalse(result.isMonsterDefeated(), "Monster should not be defeated.");
		assertTrue(result.getDamageToMonster() > 0);
		assertTrue(result.getDamageToPlayer() > 0, "Monster should have counter attacked.");
		assertTrue(player.getHealth() < 50);
	}
	
	@Test
	void testDefeatingMonsterAndLootDrop() {
		Potion lootPotion = new Potion("TestPotion", "Restores health.", 10);
		Monster monster = new Monster("TestMonster", 7, 6, 0, List.of(lootPotion));
		CombatResult result = combatEngine.attack(player, quest, monster);
		
		assertTrue(result.isSuccess());
		assertEquals("Defeated TestMonster.", result.getMessage());
		assertTrue(result.isMonsterDefeated());
		assertEquals(1, result.getDroppedItems().size(), "Should've dropped the one potion.");
		assertEquals("TestPotion", result.getDroppedItems().get(0).getName());
		assertNotNull(room.findItem("TestPotion").orElse(null));
		assertFalse(quest.isPhantomDefeated(), "Quest shouldn't be updated after monster is defeated.");
	}
	
	@Test
	void testDefeatingBossAndLootDrop() {
		Potion lootPotion = new Potion("TestPotion", "Restores health.", 10);
		BossMonster boss = new BossMonster("Final Exam Phantom", 7, 5, 0, List.of(lootPotion), 3);
		CombatResult result = combatEngine.attack(player, quest, boss);
		
		assertTrue(result.isSuccess());
		assertEquals("Defeated Final Exam Phantom.", result.getMessage());
		assertTrue(result.isMonsterDefeated());
		assertEquals(1, result.getDroppedItems().size(), "Should've dropped the one potion.");
		assertEquals("TestPotion", result.getDroppedItems().get(0).getName());
		assertNotNull(room.findItem("TestPotion").orElse(null));
		assertTrue(quest.isPhantomDefeated(), "Quest should be updated after boss is defeated.");
	}
}
