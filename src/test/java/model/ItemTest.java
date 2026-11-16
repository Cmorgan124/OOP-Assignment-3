package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import com.example.haunted.model.Item;

import static org.junit.jupiter.api.Assertions.*;


public class ItemTest {

	private Item item;
    private final String name = "name";
    private final String description = "description";

    @BeforeEach 
    void setUp() {
        item = new Item(name, description) { };
    }
    
    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(NullPointerException.class, () -> new Item(null, description) { });
    }

    @Test 
    void shouldThrowExceptionWhenDescriptionIsNull() {
        assertThrows(NullPointerException.class, () -> new Item(name, null) { });
    }
    
    @Test 
    void shouldGetName() {
        //Act 
        String name = item.getName();
        //Assert
        assertEquals(this.name, name);
    }
    
    @Test 
    void shouldGetDescription() {
        //Act
        String description = item.getDescription();
        //Assert
        assertEquals(this.description, description);
    }
    
    @Test
    void toStringShouldReturnName() {
        //Act
        String result = item.toString();
        //Assert
        assertEquals(this.name, result);
    }
    
}
