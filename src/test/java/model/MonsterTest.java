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
    
}
