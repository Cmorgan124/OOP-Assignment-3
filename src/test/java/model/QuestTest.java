package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.haunted.model.Quest;
import com.example.haunted.model.QuestStatus;

class QuestTest {
	// ARRANGE
	private Quest quest;
	@BeforeEach
	void setup() {
		quest = new Quest("Name", "Description.");
		assertEquals("Name", quest.getName());
		assertEquals("Description.", quest.getDescription());
		assertEquals(QuestStatus.NOT_STARTED, quest.getStatus());
        assertFalse(quest.isGradebookRecovered());
        assertFalse(quest.isPhantomDefeated());
        assertFalse(quest.isComplete());
	}
	
	@Test
	void shouldThrowExceptionWhenNameIsNull() {
		assertThrows(NullPointerException.class, () -> new Quest(null, "Description."));
	}
	
	@Test
	void shouldThrowExceptionWhenDescriptionIsNull() {
		assertThrows(NullPointerException.class, () -> new Quest("Name", null));
	}
	
	@Test
	void statusShouldBeIN_PROGRESSWhenOnlyGradebookRecovered() {		
		// ACT
		quest.markGradebookRecovered();

		// ASSERT
		assertEquals(QuestStatus.IN_PROGRESS, quest.getStatus());
		assertTrue(quest.isGradebookRecovered());
		assertFalse(quest.isComplete());
	}
	
	@Test
	void statusShouldBeIN_PROGRESSWhenOnlyPhantomDefeated() {		
		// ACT
		quest.markPhantomDefeated();

		// ASSERT
		assertEquals(QuestStatus.IN_PROGRESS, quest.getStatus());
		assertTrue(quest.isPhantomDefeated());
		assertFalse(quest.isComplete());
	}
	
	@Test
	void statusShouldBeCOMPLETEDWhenBothObjectivesComplete() {
		// ACT
		quest.markGradebookRecovered();
		quest.markPhantomDefeated();
		
		// ASSERT
		assertEquals(QuestStatus.COMPLETED, quest.getStatus());
		assertTrue(quest.isComplete());
	}
	
}