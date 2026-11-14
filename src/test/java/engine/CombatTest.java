package engine;
import com.example.haunted.model.Inventory;
import com.example.haunted.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CombatTest {

	private Player player;
	
	// happy path from DungeonFactory: new Player("Student Explorer", 50, 7, 2, new Inventory(8));
	@BeforeEach
	void setup() {
		player = new Player("TestHero", 50, 7, 2, new Inventory(8));
	}

}
