package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.haunted.model.Trap;
import com.example.haunted.model.TrapType;

public class TrapTest {
	//Arrange
    private Trap trap;
    private TrapType type;
    private final int damage = 10;
    private final boolean oneTimeTrigger = true;
    private boolean armed = true;
    
    @BeforeEach
    void setUp() {
        trap = new Trap("name", type, damage, oneTimeTrigger, armed);
    }
    
 
    @Test
    void shouldInitializeTrap() {

        //Act
        Trap testTrap = new Trap("Poison", TrapType.Steam, 2, false, false);

        //Assert
        assertEquals("Poison", testTrap.getName());
        assertEquals(TrapType.Steam, testTrap.getType());
        assertEquals(2, testTrap.getDamage());
        assertFalse(testTrap.isOneTimeTrigger());
        assertFalse(testTrap.isArmed());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull(){

        //Assert
        assertThrows(NullPointerException.class, () -> new Trap(null, TrapType.Steam, 2, false, false));
    }

    
    @Test
    void shouldThrowExceptionWhenTypeIsNull(){

        //Assert
        assertThrows(NullPointerException.class, () -> new Trap("name", null, 2, false, false));
    }
    
    @Test
    void shouldGetName() {

        //Act
        String name = trap.getName();
        //Assert
        assertEquals("name", name);
    }
    

    @Test
    void shouldGetType() {

        //Act
        TrapType testType = trap.getType();
        //Assert
        assertEquals(type, testType);
    }
    
    @Test
    void shouldGetDamage() {

        //Act
        int actualDamage = trap.getDamage();

        //Assert
        assertEquals(damage, actualDamage);
    }
    
}
