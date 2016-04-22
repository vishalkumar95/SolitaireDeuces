package vkrathi_deuces;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.model.ModelFactory;
import vkrathi_deuces.DealOneMove;
import vkrathi_deuces.Deuces;
import vkrathi_deuces.WasteToFoundationMove;

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
		
		assertEquals(false, value2);
		
	}
	
	public void testWasteToFoundationMoveMore(){
		
		// Add an ace in one of the tableau columns
		ModelFactory.init(deuces.wastePile, "1D");
		ModelFactory.init(deuces.pile1, "KD");
		
		Card topCardTableauColumn = deuces.wastePile.peek();
		
		WasteToFoundationMove wtm = new WasteToFoundationMove(deuces.wastePile, topCardTableauColumn, deuces.pile1, deuces.wasteNum);
		
		boolean value = wtm.valid(deuces);
		
		assertEquals(true, value);
		
		wtm.doMove(deuces);
		
		assertEquals(topCardTableauColumn, deuces.pile1.peek());
		
		wtm.undo(deuces);
	}
	
	public void testWasteToFoundationMoveMore1(){
		
		// Empty wastePile
		ModelFactory.init(deuces.wastePile, "");
		ModelFactory.init(deuces.pile1, "KD");
		
		Card topCardTableauColumn = deuces.wastePile.peek();
		
		WasteToFoundationMove wtm = new WasteToFoundationMove(deuces.wastePile, topCardTableauColumn, deuces.pile1, deuces.wasteNum);
		
		boolean value = wtm.valid(deuces);
		
		assertEquals(false, value);
		
		boolean value1 = wtm.doMove(deuces);
		
		assertEquals(false, value1);
		
		wtm.undo(deuces);
	}
	
	public void testWasteToFoundationMoveMore2(){
		
		// Empty wastePile
		ModelFactory.init(deuces.wastePile, "1D");
		ModelFactory.init(deuces.pile1, "");
		
		Card topCardTableauColumn = deuces.wastePile.peek();
		
		WasteToFoundationMove wtm = new WasteToFoundationMove(deuces.wastePile, topCardTableauColumn, deuces.pile1, deuces.wasteNum);
		
		boolean value = wtm.valid(deuces);
		
		assertEquals(false, value);
		
		boolean value1 = wtm.doMove(deuces);
		
		assertEquals(false, value1);
		
		boolean value3 = wtm.undo(deuces);
		
		assertEquals(false, value3);
	}
}
