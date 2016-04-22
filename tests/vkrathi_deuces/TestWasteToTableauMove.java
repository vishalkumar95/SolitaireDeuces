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
		
		dom1.doMove(deuces);
	}
}
