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
    private int health = 5;
    private final int attack = 5;
    private final int defense = 5;
    private final List<Item> loot = List.of();
    
    @BeforeEach
    void setUp() {
        monster = new Monster(name, maxHealth, attack, defense, loot);
    }
    
    @Test 
    void shouldThrowExceptionWhenNameIsNull() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Monster(null, maxHealth, attack, defense, loot));
    }

    @Test
    void shouldThrowExceptionWhenMaxHealthIsNegative() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Monster(name, -1, attack, defense, loot));
    }

    @Test
    void shouldThrowExceptionWhenAttackIsNegative() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Monster(name, maxHealth, -1, defense, loot));
    }

    @Test
    void shouldThrowExceptionWhenDefenseIsNegative() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Monster(name, maxHealth, attack, -1, loot));
    }

    @Test
    void shouldThrowExceptionWhenLootIsNull() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class,
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
    
    
    
}
