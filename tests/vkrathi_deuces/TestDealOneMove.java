package vkrathi_deuces;

import java.util.Random;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.launcher.Main;
import vkrathi_deuces.DealOneMove;
import vkrathi_deuces.Deuces;

public class TestDealOneMove extends TestCase {
	
	Deuces deuces;
	GameWindow gw;
	
	@Override
	protected void setUp() {
		deuces = new Deuces();
		gw = Main.generateWindow(deuces, new Random().nextInt());
	}
	
	public void testDealOneMove() {
		
		// The following test cases are for a valid game
		
		Card topCard = deuces.multiDeck.peek();
		
		DealOneMove dom = new DealOneMove(deuces.multiDeck, deuces.wastePile, deuces.wasteNum);
	
		assertTrue(dom.valid(deuces));	
		
		dom.doMove(deuces);
		
		assertEquals(85, deuces.multiDeck.count());
		assertEquals(topCard, deuces.wastePile.peek());
		
		int value = deuces.getNumLeft().getValue();
		assertEquals(85, value);
	
		int wasteValue = deuces.wasteNum.getValue();
		assertEquals(1, wasteValue);
		
		dom.undo(deuces);
		assertEquals(86, deuces.multiDeck.count());
		
		// The following test cases for not a valid game
		
		// Empty the multi deck
		for (int i = 0; i < 86; i++){
			Card card = deuces.multiDeck.get();
		}
				
		DealOneMove dom1 = new DealOneMove(deuces.multiDeck, deuces.wastePile, deuces.wasteNum);
		
		boolean value1 = dom1.valid(deuces);
		assertEquals(false, value1);
		
		// Create a move
		boolean value2  = dom1.doMove(deuces);
		assertEquals(false, value2);
		
		// Do an undo
		boolean value3 = dom1.undo(deuces);
		assertEquals(false, value3);
		
		// See we have not won
		assertEquals(false, deuces.hasWon());
		
	}
}
