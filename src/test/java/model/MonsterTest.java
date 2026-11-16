package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.example.haunted.model.Item;
import com.example.haunted.model.Monster;

public class MonsterTest {

	private Monster monster;
    private final String name = "name";
    private final int maxHealth = 10;
    private int health = 10;
    private final int attack = 5;
    private final int defense = 5;
    private final List<Item> loot = List.of();
    
    @BeforeEach
    void setUp() {
        monster = new Monster(name, health, attack, defense, loot);
    }
    
    @Test 
    void shouldThrowExceptionWhenNameIsNull() {
        //Act & Assert
        assertThrows(NullPointerException.class,
                () -> new Monster(null, maxHealth, attack, defense, loot));
    }

    @Test
    void shouldThrowExceptionWhenLootIsNull() {
        //Act & Assert
        assertThrows(NullPointerException.class,
                () -> new Monster(name, maxHealth, attack, defense, null));
    }
    
    @Test
    void shouldGetName() {
        //Act
        String name = monster.getName();

        //Assert
        assertEquals(this.name, name);
    }
    
    @Test
    void shouldGetHealth() {
        //Act
        int health = monster.getHealth();

        //Assert
        assertEquals(this.health, health);
    }
    
    @Test
    void shouldGetMaxHealth() {
        //Act
        int maxHealth = monster.getMaxHealth();

        //Assert
        assertEquals(this.maxHealth, maxHealth);
    }
    
    @Test
    void shouldGetAttack() {
        //Act
        int attack = monster.getAttack();

        //Assert
        assertEquals(this.attack, attack);
    }
    
    @Test
    void shouldGetDefense() {
        //Act
        int defense = monster.getDefense();

        //Assert
        assertEquals(this.defense, defense);
    }
    
    @Test
    void shouldTakeDamage() {
        //Act
        monster.takeDamage(5);

        //Assert
        assertEquals(5, monster.getHealth());
    }
    
    @Test
    void shouldBeAlive() {
        //Act
        boolean alive = monster.isAlive();

        //Assert
        assertTrue(alive);
    }
    
    @Test 
    void shouldBeDead() {
        //Act
    	List<Item> testloot = List.of();
        Monster testMonster = new Monster(name, 0, 5, 5, testloot);
        boolean alive = testMonster.isAlive();

        //Assert
        assertFalse(alive);
    }
    
    @Test
    void shouldGetLoot() {
        //Act
        List<Item> loot = monster.getLoot();

        //Assert
        assertEquals(this.loot, loot);
    }
    
}
