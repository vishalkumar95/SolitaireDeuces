package vkrathi_deuces;

import deuces.DealOneMove;
import deuces.Deuces;
import deuces.WasteToFoundationMove;
import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;

public class TestWasteToFoundationMove extends TestCase {
	
	Deuces deuces;
	GameWindow gw;
	
	@Override
	protected void setUp() {
		deuces = new Deuces();
		gw = Main.generateWindow(deuces, Deck.OrderByRank);
	}
	
	public void testWasteToFoundationMove() {
		
		Card topCardWastePile = deuces.wastePile.peek();
		
		WasteToFoundationMove dom = new WasteToFoundationMove(deuces.wastePile, topCardWastePile, deuces.pile2, deuces.wasteNum);
	
		assertEquals(null, topCardWastePile);
		
		boolean value1 = deuces.wastePile.empty();
		
		assertEquals(true, value1);
		
		boolean value = dom.valid(deuces);
		
		assertEquals(false, value);
		
		Card topCard = deuces.multiDeck.peek();
		
		DealOneMove dom2 = new DealOneMove(deuces.multiDeck, deuces.wastePile, deuces.wasteNum);
		
		dom2.doMove(deuces);
		
		WasteToFoundationMove dom1 = new WasteToFoundationMove(deuces.wastePile, topCard, deuces.pile2, deuces.wasteNum);
		
		deuces.pile2.get();
		
		assertEquals(topCard, deuces.wastePile.peek());
		
		boolean value2 = dom1.valid(deuces);
		
		//assertEquals(true, value2);
		
		//dom1.doMove(deuces);
		
		// 
	}
}
