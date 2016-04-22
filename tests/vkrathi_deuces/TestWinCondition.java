package vkrathi_deuces;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import ks.tests.model.ModelFactory;
import vkrathi_deuces.Deuces;

public class TestWinCondition extends KSTestCase {
	
	// this is the game under test.
	Deuces deuces;
	
	// window for game.
	GameWindow gw;
	
	protected void setUp() {
		deuces = new Deuces();
		// Generate the window
		gw = Main.generateWindow(deuces, Deck.OrderByRank);	
	}
	
	// clean up properly
	protected void tearDown() {
		gw.setVisible(false);
		gw.dispose();
	}
	
	public void testWinCase() {
		
		// Get all the cards from tableau to foundation
		ModelFactory.init(deuces.column1, "1D KD QD JD 10D 9D 8D 7D 6D 5D 4D 3D");
		ModelFactory.init(deuces.column2, "1H KH QH JH 10H 9H 8H 7H 6H 5H 4H 3H");
		ModelFactory.init(deuces.column3, "1S KS QS JS 10S 9S 8S 7S 6S 5S 4S 3S");
		ModelFactory.init(deuces.column4, "1C KC QC JC 10C 9C 8C 7C 6C 5C 4C 3C");
		ModelFactory.init(deuces.column5, "1D KD QD JD 10D 9D 8D 7D 6D 5D 4D 3D");
		ModelFactory.init(deuces.column6, "1H KH QH JH 10H 9H 8H 7H 6H 5H 4H 3H");
		ModelFactory.init(deuces.column7, "1S KS QS JS 10S 9S 8S 7S 6S 5S 4S 3S");
		ModelFactory.init(deuces.column8, "1C KC QC JC 10C 9C 8C 7C 6C 5C 4C 3C");
		
		// Get 2's in the foundation piles
		ModelFactory.init(deuces.pile1, "2D");
		ModelFactory.init(deuces.pile2, "2H");
		ModelFactory.init(deuces.pile3, "2S");
		ModelFactory.init(deuces.pile4, "2C");
		ModelFactory.init(deuces.pile5, "2D");
		ModelFactory.init(deuces.pile6, "2H");
		ModelFactory.init(deuces.pile7, "2S");
		ModelFactory.init(deuces.pile8, "2C");
		
		// Create a mouse event by pressing on the card in the waste pile
		MouseEvent pr = createPressed(deuces, deuces.columnView1, 0, 0);
		deuces.columnView1.getMouseManager().handleMouseEvent(pr);
		
		// Create a mouse released event on the target tableau column
		MouseEvent rel = createReleased(deuces, deuces.pileView1, 0, 0);
		deuces.pileView1.getMouseManager().handleMouseEvent(rel);
		
		// Create a mouse event by pressing on the card in the waste pile
		MouseEvent pr1 = createPressed(deuces, deuces.columnView2, 0, 0);
		deuces.columnView2.getMouseManager().handleMouseEvent(pr1);
		
		// Create a mouse released event on the target tableau column
		MouseEvent rel1 = createReleased(deuces, deuces.pileView2, 0, 0);
		deuces.pileView2.getMouseManager().handleMouseEvent(rel1);
		
		// Create a mouse event by pressing on the card in the waste pile
		MouseEvent pr2 = createPressed(deuces, deuces.columnView3, 0, 0);
		deuces.columnView3.getMouseManager().handleMouseEvent(pr2);
		
		// Create a mouse released event on the target tableau column
		MouseEvent rel2 = createReleased(deuces, deuces.pileView3, 0, 0);
		deuces.pileView3.getMouseManager().handleMouseEvent(rel2);
		
		// Create a mouse event by pressing on the card in the waste pile
		MouseEvent pr3 = createPressed(deuces, deuces.columnView4, 0, 0);
		deuces.columnView4.getMouseManager().handleMouseEvent(pr3);
		
		// Create a mouse released event on the target tableau column
		MouseEvent rel3 = createReleased(deuces, deuces.pileView4, 0, 0);
		deuces.pileView4.getMouseManager().handleMouseEvent(rel3);
		
		// Create a mouse event by pressing on the card in the waste pile
		MouseEvent pr4 = createPressed(deuces, deuces.columnView5, 0, 0);
		deuces.columnView5.getMouseManager().handleMouseEvent(pr4);
		
		// Create a mouse released event on the target tableau column
		MouseEvent rel4 = createReleased(deuces, deuces.pileView5, 0, 0);
		deuces.pileView5.getMouseManager().handleMouseEvent(rel4);
		
		// Create a mouse event by pressing on the card in the waste pile
		MouseEvent pr5 = createPressed(deuces, deuces.columnView6, 0, 0);
		deuces.columnView6.getMouseManager().handleMouseEvent(pr5);
		
		// Create a mouse released event on the target tableau column
		MouseEvent rel5 = createReleased(deuces, deuces.pileView6, 0, 0);
		deuces.pileView6.getMouseManager().handleMouseEvent(rel5);
		
		// Create a mouse event by pressing on the card in the waste pile
		MouseEvent pr6 = createPressed(deuces, deuces.columnView7, 0, 0);
		deuces.columnView7.getMouseManager().handleMouseEvent(pr6);
		
		// Create a mouse released event on the target tableau column
		MouseEvent rel6 = createReleased(deuces, deuces.pileView7, 0, 0);
		deuces.pileView7.getMouseManager().handleMouseEvent(rel6);
		
		// Create a mouse event by pressing on the card in the waste pile
		MouseEvent pr7 = createPressed(deuces, deuces.columnView8, 0, 0);
		deuces.columnView8.getMouseManager().handleMouseEvent(pr7);
		
		// Create a mouse released event on the target tableau column
		MouseEvent rel7 = createReleased(deuces, deuces.pileView8, 0, 0);
		deuces.pileView8.getMouseManager().handleMouseEvent(rel7);
		
		assertEquals(104, deuces.getScoreValue());
		
		assertTrue(deuces.hasWon());
	}
}
