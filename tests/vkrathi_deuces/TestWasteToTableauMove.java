package vkrathi_deuces;

import deuces.DealOneMove;
import deuces.Deuces;
import deuces.WasteToTableauMove;
import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;

public class TestWasteToTableauMove extends TestCase {
	
	Deuces deuces;
	GameWindow gw;
	
	@Override
	protected void setUp() {
		deuces = new Deuces();
		gw = Main.generateWindow(deuces, Deck.OrderByRank);
	}
	
	public void testWasteToTableauMove() {
		
		// The following test cases are for a valid game
		
		//Card topCard = deuces.multiDeck.peek();
		
		//System.out.println(deuces.wastePile.count());
		
		//System.out.println(topCard.getRank());
		//System.out.println(topCard.getSuit());
		
		//Card getCard = deuces.multiDeck.get();
		
		//System.out.println(deuces.column1.get().getRank());
		//System.out.println(deuces.column2.get().getRank());
		//System.out.println(deuces.column3.get().getRank());
		//System.out.println(deuces.column4.get().getRank());
		
		Card topCardWastePile = deuces.wastePile.peek();
		
		WasteToTableauMove dom = new WasteToTableauMove(deuces.wastePile, topCardWastePile, deuces.column2, deuces.wasteNum);
	
		assertEquals(null, topCardWastePile);
		
		boolean value1 = deuces.wastePile.empty();
		
		assertEquals(true, value1);
		
		boolean value = dom.valid(deuces);
		
		assertEquals(false, value);
		
		Card topCard = deuces.multiDeck.peek();
		
		DealOneMove dom2 = new DealOneMove(deuces.multiDeck, deuces.wastePile, deuces.wasteNum);
		
		dom2.doMove(deuces);
		
		WasteToTableauMove dom1 = new WasteToTableauMove(deuces.wastePile, topCard, deuces.column2, deuces.wasteNum);
		
		deuces.column2.get();
		
		assertEquals(topCard, deuces.wastePile.peek());
		
		boolean value2 = dom1.valid(deuces);
		
		assertEquals(true, value2);
		
		// Check the valid condition
		//if ((!deuces.column2.empty() && ))
		
		dom1.doMove(deuces);
	
		//assertTrue(dom.valid(deuces));	
		
//		dom.doMove(deuces);
//		
//		assertEquals(85, deuces.multiDeck.count());
//		assertEquals(topCard, deuces.wastePile.peek());
//		
//		int value = deuces.getNumLeft().getValue();
//		assertEquals(85, value);
//	
//		int wasteValue = deuces.wasteNum.getValue();
//		assertEquals(1, wasteValue);
//		
//		dom.undo(deuces);
//		assertEquals(86, deuces.multiDeck.count());
		
		// The following test cases for not a valid game
		
		// Empty the multi deck
//		for (int i = 0; i < 86; i++){
//			Card card = deuces.multiDeck.get();
//		}
//				
//		DealOneMove dom1 = new DealOneMove(deuces.multiDeck, deuces.wastePile, deuces.wasteNum);
//		
//		boolean value1 = dom1.valid(deuces);
//		assertEquals(false, value1);
//		
//		// Create a move
//		boolean value2  = dom1.doMove(deuces);
//		assertEquals(false, value2);
//		
//		// Do an undo
//		boolean value3 = dom1.undo(deuces);
//		assertEquals(false, value3);
		
	}
}
