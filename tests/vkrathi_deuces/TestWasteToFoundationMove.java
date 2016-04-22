package vkrathi_deuces;

import deuces.DealOneMove;
import deuces.Deuces;
import deuces.WasteToFoundationMove;
import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.model.ModelFactory;

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
		
		// Test the ACE and then king in foundation move
	}
	
	public void testWasteToFoundationMoveMore(){
		
		// Add an ace in one of the tableau columns
		ModelFactory.init(deuces.column1, "1D");
		ModelFactory.init(deuces.pile1, "KD");
		
		Card topCardTableauColumn = deuces.column1.peek();
		
		WasteToFoundationMove wtm = new WasteToFoundationMove(deuces.wastePile, topCardTableauColumn, deuces.pile1, deuces.wasteNum);
		
		boolean value = wtm.valid(deuces);
		
		assertEquals(true, value);
		
		wtm.doMove(deuces);
		
		assertEquals(topCardTableauColumn, deuces.pile1.peek());
		
		wtm.undo(deuces);
	}
}
