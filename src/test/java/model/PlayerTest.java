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

}
