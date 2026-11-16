package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.haunted.model.Weapon;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponTest {
    private Weapon weapon;
    private final String name = "name";
    private final String description = "description";
    private final int attackBonus = 5;
    
    @BeforeEach 
    void setUp() {
        weapon = new Weapon(name, description, attackBonus);
    }

    @Test
    void shouldGetAttackBonus() {
       //Act
       int attackBonus = weapon.getAttackBonus();
       //Assert
       assertEquals(this.attackBonus, attackBonus);
    }
}
