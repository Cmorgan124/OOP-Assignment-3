package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.haunted.model.Armor;

import static org.junit.jupiter.api.Assertions.*;

public class ArmorTest {
	
    private Armor armor;
    private final String name = "name";
    private final String description = "description";
    private final int defenseBonus = 5;
    
    @BeforeEach 
    void setUp() {
        armor = new Armor(name, description, defenseBonus);
    }
    
    @Test
    void shouldGetDefenseBonus() {
        //Act
        int defenseBonus = armor.getDefenseBonus();
        //Assert
        assertEquals(this.defenseBonus, defenseBonus);
    }

}
