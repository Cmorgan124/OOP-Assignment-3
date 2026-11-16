package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.example.haunted.model.Item;
import com.example.haunted.model.BossMonster;

public class BossMonsterTest {
	private BossMonster bossMonster;
    private final String name = "name";
    private int health = 5;
    private final int attack = 5;
    private final int defense = 5;
    private final List<Item> loot = List.of();
    private final int enragedAttackBonus = 5;
    
    @BeforeEach
    void setUp() {
        bossMonster = new BossMonster(name, health, attack, defense, loot, enragedAttackBonus);
    }

}

@Test 
void shouldThrowExceptionWhenNameIsNull() {
    //Act & Assert
    assertThrows(IllegalArgumentException.class,
            () -> new BossMonster(null, health, attack, defense, loot, enragedAttackBonus));
}

@Test
void shouldThrowExceptionWhenHealthIsNegative() {
    //Act & Assert
    assertThrows(IllegalArgumentException.class,
            () -> new BossMonster(name, -1, attack, defense, loot, enragedAttackBonus));
}

@Test
void shouldThrowExceptionWhenAttackIsNegative() {
    //Act & Assert
    assertThrows(IllegalArgumentException.class,
            () -> new BossMonster(name, health, -1, defense, loot, enragedAttackBonus));
}

@Test
void shouldThrowExceptionWhenDefenseIsNegative() {
    //Act & Assert
    assertThrows(IllegalArgumentException.class,
            () -> new BossMonster(name, health, attack, -1, loot, enragedAttackBonus));
}

@Test
void shouldThrowExceptionWhenLootIsNull() {
    //Act & Assert
    assertThrows(IllegalArgumentException.class,
            () -> new BossMonster(name, health, attack, defense, null, enragedAttackBonus));
}

@Test
void shouldThrowExceptionWhenEnragedAttackBonusIsNegative() {
    //Act & Assert
    assertThrows(IllegalArgumentException.class,
            () -> new BossMonster(name, health, attack, defense, loot, -1));
}

@Test
void shouldGetName() {
    //Act
    String name = bossMonster.getName();

    //Assert
    assertEquals(this.name, name);
}

@Test
void shouldGetHealth() {
    //Act
    int health = bossMonster.getHealth();

    //Assert
    assertEquals(this.health, health);
}

@Test
void shouldGetAttack() {
    //Act
    int attack = bossMonster.getAttack();

    //Assert
    assertEquals(this.attack, attack);
}

@Test
void shouldGetCurrentAttack() {
    //Act
    int currentAttack = bossMonster.getCurrentAttack();

    //Assert
    assertEquals(this.attack, currentAttack);
}

@Test
void shouldGetDefense() {
    //Act
    int defense = bossMonster.getDefense();

    //Assert
    assertEquals(this.defense, defense);
}

@Test
void shouldTakeDamage() {
    //Act
    bossMonster.takeDamage(attack);

    //Assert
    assertEquals(0, bossMonster.getHealth());
}

@Test
void shouldBeAlive() {
    //Act
    boolean alive = bossMonster.isAlive();

    //Assert
    assertTrue(alive);
}

@Test 
void shouldBeDead() {
    //Act
    bossMonster.takeDamage(attack);
    boolean alive = bossMonster.isAlive();

    //Assert
    assertFalse(alive);
}

@Test
void shouldGetLoot() {
    //Act
    List<Item> loot = bossMonster.getLoot();

    //Assert
    assertEquals(this.loot, loot);
}
}
