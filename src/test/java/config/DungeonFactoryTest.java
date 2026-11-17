package config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.haunted.config.DungeonFactory;
import com.example.haunted.engine.GameEngine;
import com.example.haunted.model.Armor;
import com.example.haunted.model.BossMonster;
import com.example.haunted.model.Direction;
import com.example.haunted.model.Monster;
import com.example.haunted.model.Player;
import com.example.haunted.model.Potion;
import com.example.haunted.model.Room;
import com.example.haunted.model.Trap;
import com.example.haunted.model.TrapType;
import com.example.haunted.model.Weapon;

class DungeonFactoryTest {

	private GameEngine game;
    private Room stairwell;
    private Room lectureHall;
    private Room labStorage;
    private Room brokenElevator;
    private Room serverCloset;
    private Room examArchive;
    private Room deanVault;
    private Room finalChamber;

    @BeforeEach
    void setUp() {
        game = DungeonFactory.createGame();
        stairwell = game.getCurrentRoom();
        lectureHall = stairwell.getExit(Direction.EAST);
        labStorage = lectureHall.getExit(Direction.EAST);
        brokenElevator = lectureHall.getExit(Direction.SOUTH);
        examArchive = lectureHall.getExit(Direction.NORTH);
        serverCloset = labStorage.getExit(Direction.NORTH);
        deanVault = examArchive.getExit(Direction.EAST);
        finalChamber = deanVault.getExit(Direction.NORTH);
    }

    @Test
    void testPlayerAndQuestInitialization() {
        Player player = game.getPlayer();
        assertNotNull(player);
        assertEquals("Student Explorer", player.getName());
        assertEquals(50, player.getMaxHealth());
        assertEquals(50, player.getHealth());
        assertEquals(7, player.getBaseAttack());
        assertEquals(2, player.getBaseDefense());
        assertEquals(8, player.getInventory().getCapacity());

        assertNotNull(game.getQuest());
        assertEquals("Escape the Basement", game.getQuest().getName());
        assertEquals("Recover the Lost Gradebook and defeat the Final Exam Phantom.", game.getQuest().getDescription());
        assertFalse(game.isGameOver());
        assertFalse(game.isGameWon());
    }

    @Test
    void testAllRoomMetadataAndConnections() {
        assertEquals("stairwell", stairwell.getId());
        assertEquals("Maintenance Stairwell", stairwell.getName());
        assertEquals("The flickering stairwell leading into the basement.", stairwell.getDescription());

        assertEquals("lectureHall", lectureHall.getId());
        assertEquals("Abandoned Lecture Hall", lectureHall.getName());
        assertEquals("Dusty desks and a broken projector.", lectureHall.getDescription());

        assertEquals("labStorage", labStorage.getId());
        assertEquals("Lab Storage", labStorage.getName());
        assertEquals("Shelves of outdated hardware and chemicals.", labStorage.getDescription());

        assertEquals("brokenElevator", brokenElevator.getId());
        assertEquals("Broken Elevator", brokenElevator.getName());
        assertEquals("The elevator doors hang over a dark shaft.", brokenElevator.getDescription());

        assertEquals("serverCloset", serverCloset.getId());
        assertEquals("Server Closet", serverCloset.getName());
        assertEquals("A warm humming room full of blinking servers.", serverCloset.getDescription());

        assertEquals("examArchive", examArchive.getId());
        assertEquals("Exam Archive", examArchive.getName());
        assertEquals("Cabinets of old exams and paperwork.", examArchive.getDescription());

        assertEquals("deanVault", deanVault.getId());
        assertEquals("Dean Vault", deanVault.getName());
        assertEquals("A hidden chamber guarded by university secrets.", deanVault.getDescription());

        assertEquals("finalChamber", finalChamber.getId());
        assertEquals("Final Chamber", finalChamber.getName());
        assertEquals("A spectral exam hall where the boss waits.", finalChamber.getDescription());

        assertEquals(stairwell, lectureHall.getExit(Direction.WEST));
        assertEquals(lectureHall, labStorage.getExit(Direction.WEST));
        assertEquals(lectureHall, brokenElevator.getExit(Direction.NORTH));
        assertEquals(labStorage, serverCloset.getExit(Direction.SOUTH));
        assertEquals(deanVault, serverCloset.getExit(Direction.EAST));
        assertEquals(lectureHall, examArchive.getExit(Direction.SOUTH));
        assertEquals(examArchive, deanVault.getExit(Direction.WEST));
        assertEquals(serverCloset, deanVault.getExit(Direction.SOUTH));
        assertEquals(deanVault, finalChamber.getExit(Direction.SOUTH));
    }

    @Test
    void testLocksAndTraps() {
    	Trap trap = brokenElevator.getTrap();
    	
        assertFalse(stairwell.isLocked());
        assertFalse(lectureHall.isLocked());
        assertTrue(examArchive.isLocked());
        assertEquals("Archive Key", examArchive.getRequiredKeyName());
        assertTrue(finalChamber.isLocked());
        assertEquals("Vault Key", finalChamber.getRequiredKeyName());

        assertNotNull(trap);
        assertEquals("Loose Wires Trap", trap.getName());
        assertEquals(TrapType.ELECTRIC, trap.getType());
        assertEquals(8, trap.getDamage());
        assertTrue(trap.isArmed());
        assertTrue(trap.isOneTimeTrigger());
    }

    @Test
    void testAllRoomItems() {
        Potion coffee = (Potion) lectureHall.findItem("Coffee Potion").orElseThrow();
        Armor shield = (Armor) labStorage.findItem("Calculator Shield").orElseThrow();
        Weapon stapler = (Weapon) serverCloset.findItem("Stapler of Justice").orElseThrow();
        
        assertEquals("A suspiciously warm cup of coffee.", coffee.getDescription());
        assertEquals(15, coffee.getHealingAmount());
        assertEquals("Opens the Exam Archive.", labStorage.findItem("Archive Key").orElseThrow().getDescription());
        assertEquals("A battered calculator used as a shield.", shield.getDescription());
        assertEquals(3, shield.getDefenseBonus());
        assertEquals("A heavy-duty stapler with righteous force.", stapler.getDescription());
        assertEquals(4, stapler.getAttackBonus());
        assertEquals("The legendary missing gradebook.", examArchive.findItem("Lost Gradebook").orElseThrow().getDescription());
        assertEquals("Opens the Final Chamber.", deanVault.findItem("Vault Key").orElseThrow().getDescription());
    }

    @Test
    void testAllRoomMonstersAndDrops() {
        Monster ta = lectureHall.findMonster("Sleep-Deprived TA").orElseThrow();
        Potion taDrop = (Potion) ta.getLoot().get(0);
        
        assertEquals(18, ta.getMaxHealth());
        assertEquals(6, ta.getAttack());
        assertEquals(1, ta.getDefense());
        assertEquals(1, ta.getLoot().size());
        assertEquals("Coffee Potion", taDrop.getName());
        assertEquals("A backup coffee drop.", taDrop.getDescription());
        assertEquals(15, taDrop.getHealingAmount());

        Monster golem = serverCloset.findMonster("Spreadsheet Golem").orElseThrow();
        Weapon golemDrop = (Weapon) golem.getLoot().get(0);
        
        assertEquals(28, golem.getMaxHealth());
        assertEquals(7, golem.getAttack());
        assertEquals(4, golem.getDefense());
        assertEquals(1, golem.getLoot().size());
        assertEquals("Dry Erase Sword", golemDrop.getName());
        assertEquals("An improvised marker tray blade.", golemDrop.getDescription());
        assertEquals(6, golemDrop.getAttackBonus());

        Monster ghost = examArchive.findMonster("Plagiarism Ghost").orElseThrow();
        
        assertEquals(22, ghost.getMaxHealth());
        assertEquals(8, ghost.getAttack());
        assertEquals(2, ghost.getDefense());
        assertTrue(ghost.getLoot().isEmpty());

        Monster wraith = deanVault.findMonster("Registrar Wraith").orElseThrow();
        Armor wraithDrop = (Armor) wraith.getLoot().get(0);
        
        assertEquals(30, wraith.getMaxHealth());
        assertEquals(9, wraith.getAttack());
        assertEquals(3, wraith.getDefense());
        assertEquals(1, wraith.getLoot().size());
        assertEquals("Graduation Gown Armor", wraithDrop.getName());
        assertEquals("Ceremonial robes with uncanny resilience.", wraithDrop.getDescription());
        assertEquals(5, wraithDrop.getDefenseBonus());

        Monster boss = finalChamber.findMonster("Final Exam Phantom").orElseThrow();
        
        assertInstanceOf(BossMonster.class, boss);
        assertEquals(40, boss.getMaxHealth());
        assertEquals(10, boss.getAttack());
        assertEquals(4, boss.getDefense());
        assertTrue(boss.getLoot().isEmpty());
        
        boss.takeDamage(20);
        assertEquals(13, ((BossMonster) boss).getCurrentAttack());
    }
}
