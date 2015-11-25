package main;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	@Before
	public void setUp() throws Exception {
		Game.main(null);
	}

	@Test
	public void testStartup() {
		assertEquals(Game.window.getTitle(), Game.TITLE);//test title and window initializes
		
	}

}
