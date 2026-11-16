package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.haunted.model.Armor;
import com.example.haunted.model.Inventory;
import com.example.haunted.model.Weapon;
import com.example.haunted.model.Room;
import com.example.haunted.model.Player;

public class PlayerTest {
	
    private final String name = "name";
    private final int maxHealth = 100;
    private final int baseAttack = 10;
    private final int baseDefense = 5;
    private int health = maxHealth;
    private final Inventory inventory = new Inventory(5);
    private Player player;
    private Weapon equippedWeapon;
    private Armor equippedArmor;
    private Room currentRoom;
    
    @BeforeEach
    void setUp() {
        // Initialize the player object before each test
        player = new Player(name, maxHealth, baseAttack, baseDefense, inventory);
    }
    
    @Test 
    void shouldGetName() {
        //Act 
        String name = player.getName();
        //Assert
        assertEquals(this.name, name);
    }

    @Test 
    void shouldGetHealth() {
        //Act
        int health = player.getHealth();
        //Assert
        assertEquals(this.health, health);
    }

    @Test
    void shouldGetMaxHealth() {
        //Act
        int maxHealth = player.getMaxHealth();
        //Assert
        assertEquals(this.maxHealth, maxHealth);
    }

    @Test
    void shouldGetBaseAttack() {
        //Act
        int baseAttack = player.getBaseAttack();
        //Assert
        assertEquals(this.baseAttack, baseAttack);
    }

    @Test
    void shouldGetBaseDefense() {
        //Act
        int baseDefense = player.getBaseDefense();
        //Assert
        assertEquals(this.baseDefense, baseDefense);
    }

    @Test 
    void shouldGetInventory() {
        //Act
        Inventory inventory = player.getInventory();
        //Assert
        assertEquals(this.inventory, inventory);
    }

    @Test 
    void shouldGetEquippedWeapon() {
        //Act
        Weapon equippedWeapon = player.getEquippedWeapon();
        //Assert
        assertEquals(this.equippedWeapon, equippedWeapon);
    }

    @Test 
    void shouldGetEquippedArmor() {
        //Act
        Armor equippedArmor = player.getEquippedArmor();
        //Assert
        assertEquals(this.equippedArmor, equippedArmor);
    }

    @Test
    void shouldGetCurrentRoom() {
        //Act
        Room currentRoom = player.getCurrentRoom();
        //Assert
        assertEquals(this.currentRoom, currentRoom);
    }
    
    @Test 
    void shouldSetCurrentRoom() {
        //Arrange
        Room newRoom = new Room("id", "roomName", "roomDescription");
        //Act
        player.setCurrentRoom(newRoom);
        //Assert
        assertEquals(newRoom, player.getCurrentRoom());
    }

    @Test 
    void shouldThrowExceptionWhenSettingCurrentRoomToNull() {
        assertThrows(NullPointerException.class, () -> player.setCurrentRoom(null));
    }
    
    @Test 
    void shouldHealPlayer() {
        //Arrange
        int damage = 20;
        player.takeDamage(damage);
        //Act
        player.heal(10);
        //Assert
        assertEquals(maxHealth - damage + 10, player.getHealth());
    }
    
    @Test
    void shouldBeAlive() {
        //Act
        boolean isAlive = player.isAlive();
        //Assert
        assertTrue(isAlive);
    }

    @Test 
    void shouldBeDead() {
        //Arrange
        player.takeDamage(maxHealth);
        //Act
        boolean isAlive = player.isAlive();
        //Assert
        assertFalse(isAlive);
    }
    
    @Test 
    void shouldGetAttackPower() {
        //Arrange
        Weapon weapon = new Weapon("weaponName", "weaponDescription", 5);
        player.getInventory().addItem(weapon);
        player.equipWeapon(weapon);
        //Act
        int attackPower = player.getAttackPower();
        //Assert
        assertEquals(baseAttack + weapon.getAttackBonus(), attackPower);
    }

    @Test
    void shouldGetDefensePower() {
        //Arrange
        Armor armor = new Armor("armorName", "armorDescription", 5);
        player.getInventory().addItem(armor);
        player.equipArmor(armor);
        //Act
        int defensePower = player.getDefensePower();
        //Assert
        assertEquals(baseDefense + armor.getDefenseBonus(), defensePower);
    }
    
    @Test 
    void shouldEquipWeapon() {
        //Arrange
        Weapon weapon = new Weapon("weaponName", "weaponDescription", 5);
        player.getInventory().addItem(weapon);
        //Act
        player.equipWeapon(weapon);
        //Assert
        assertEquals(weapon, player.getEquippedWeapon());
    }

    @Test
    void shouldEquipArmor() {
        //Arrange
        Armor armor = new Armor("armorName", "armorDescription", 5);
        player.getInventory().addItem(armor);
        //Act
        player.equipArmor(armor);
        //Assert
        assertEquals(armor, player.getEquippedArmor());
    }


}
