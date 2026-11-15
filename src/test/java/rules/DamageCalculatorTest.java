package rules;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.haunted.model.BossMonster;
import com.example.haunted.model.Inventory;
import com.example.haunted.model.Monster;
import com.example.haunted.model.Player;
import com.example.haunted.rules.DamageCalculator;
import java.util.List;

class DamageCalculatorTest {

	private DamageCalculator damageCalculator;
	
	@BeforeEach
	void setUp() {
		damageCalculator = new DamageCalculator();
	}
	
	@Test
	void testCalculateStandardPlayerDamage() {
		Player player = new Player("TestHero", 50, 10, 2, new Inventory(8));
		Monster monster = new Monster("Test Monster", 20, 5, 3, List.of());
		int damage = damageCalculator.calculatePlayerDamage(player, monster);
		
		assertEquals(7, damage, "Damage should be 7 after defense reduction.");
	}
	
	@Test
	void testCalculateMinimumOnePlayerDamage() {
		Player player = new Player("TestHero", 50, 2, 2, new Inventory(8));
		Monster monster = new Monster("Test Monster", 20, 5, 10, List.of());
		int damage = damageCalculator.calculatePlayerDamage(player, monster);
		
		assertEquals(1, damage, "Since defense results in -8 damage, damage should be enforced to 1.");
	}
	
	@Test
	void testCalculateStandardMonsterDamage() {
		Player player = new Player("TestHero", 50, 10, 2, new Inventory(8));
		Monster monster = new Monster("Test Monster", 20, 5, 3, List.of());
		int damage = damageCalculator.calculateMonsterDamage(monster, player);
		
		assertEquals(3, damage, "Monster should deal 3 damage after defense reduction.");
	}
	
	@Test 
	void testCalculateStandardBossMonsterDamage() {
		BossMonster boss = new BossMonster("Boss", 40, 12, 4, List.of(), 5);
		Player player = new Player("TestHero", 50, 2, 2, new Inventory(8));
		int damage = damageCalculator.calculateMonsterDamage(boss, player);
		
		assertEquals(10, damage, "Should do 10 damage after defense reduction");
	}
	
	@Test
	void testCalculateEnragedBossMonsterDamage() {
		BossMonster boss = new BossMonster("Boss", 40, 12, 4, List.of(), 5);
		boss.takeDamage(25); //enrages it
		Player player = new Player("TestHero", 50, 10, 2, new Inventory(8));
		int damage = damageCalculator.calculateMonsterDamage(boss, player);
		
		assertEquals(15, damage, "Should do 15 after enraged bonus and defense reduction.");
	}
}
