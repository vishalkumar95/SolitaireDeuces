package vkrathi_deuces;

import java.awt.event.MouseEvent;

import deuces.Deuces;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import ks.tests.model.ModelFactory;

public class TestControllers extends KSTestCase {
	
	// this is the game under test.
	Deuces game;
	
	// window for game.
	GameWindow gw;
	
	protected void setUp() {
		game = new Deuces();
		// Generate the window
		gw = Main.generateWindow(game, Deck.OrderByRank);	
	}
	
	// clean up properly
	protected void tearDown() {
		gw.setVisible(false);
		gw.dispose();
	}
	
	public void testDeckController() {
		
		// first create a mouse event
		MouseEvent pr = createPressed (game, game.deckView, 0, 0);
		game.deckView.getMouseManager().handleMouseEvent(pr);
		
		assertEquals (new Card(Card.JACK, Card.DIAMONDS), game.wastePile.peek());
		assertTrue (game.undoMove());
		assertTrue (game.wastePile.empty());
	}
	
	// Test movement from waste pile to foundation
	public void testWastePileFoundationController(){
		
		// first get a card in the waste pile
		ModelFactory.init(game.wastePile, "3D");
		
		// Create a mouse event by pressing on the card in the waste pile
		MouseEvent pr = createPressed(game, game.wastePileView, 0, 0);
		game.wastePileView.getMouseManager().handleMouseEvent(pr);
		
		// Create a mouse released event on the target foundation pile
		MouseEvent rel = createReleased(game, game.pileView3, 0, 0);
		game.pileView3.getMouseManager().handleMouseEvent(rel);
		
		// Check if the card was moved
		assertEquals(new Card(Card.THREE, Card.DIAMONDS), game.pile3.peek());
		assertTrue(game.wastePile.empty());
		
		// Check the score
		assertEquals(9, game.getScoreValue());
		
		// Undo the move
		assertTrue(game.undoMove());
	}
	
	// Test movement from waste pile to tableau
	public void testWastePileTableauController(){
	
		// first get a card in the waste pile
		ModelFactory.init(game.wastePile, "QD");
		
		// Create a mouse event by pressing on the card in the waste pile
		MouseEvent pr = createPressed(game, game.wastePileView, 0, 0);
		game.wastePileView.getMouseManager().handleMouseEvent(pr);
		
		// Create a mouse released event on the target tableau column
		MouseEvent rel = createReleased(game, game.columnView3, 0, 0);
		game.columnView3.getMouseManager().handleMouseEvent(rel);
		
		// Check if the card moved was equal
		assertEquals(new Card(Card.QUEEN, Card.DIAMONDS), game.column3.peek());
		
		// Check the score
		assertEquals(8, game.getScoreValue());
		
		// Undo the move
		assertTrue(game.undoMove());
	}
	
	// Test movement from waste pile to tableau
	public void testTableauToFoundationController(){
	
		// first create a column of cards in the tableau column
		ModelFactory.init(game.column3, "KD QD JD 10D 9D 8D 7D 6D 5D 4D 3D");
		
		// Create a mouse event by pressing on the card in the waste pile
		MouseEvent pr = createPressed(game, game.columnView3, 0, 0);
		game.columnView3.getMouseManager().handleMouseEvent(pr);
		
		// Create a mouse released event on the target tableau column
		MouseEvent rel = createReleased(game, game.pileView3, 0, 0);
		game.pileView3.getMouseManager().handleMouseEvent(rel);
		
		// Check if the card moved was equal
		assertEquals(new Card(Card.KING, Card.DIAMONDS), game.pile3.peek());
		
		// Check the score
		assertEquals(19, game.getScoreValue());
		
		// Undo the move
		assertTrue(game.undoMove());
	}
	
	// Test movement from waste pile to tableau
	public void testTableauToTableauController(){
	
		// first create a column of cards in the tableau column
		ModelFactory.init(game.column3, "KD QD JD 10D 9D 8D 7D 6D 5D 4D 3D");
		
		// Create a mouse event by pressing on the card in the waste pile
		MouseEvent pr = createPressed(game, game.columnView3, 0, 0);
		game.columnView3.getMouseManager().handleMouseEvent(pr);
		
		// Create a mouse released event on the target tableau column
		MouseEvent rel = createReleased(game, game.pileView3, 0, 0);
		game.pileView3.getMouseManager().handleMouseEvent(rel);
		
		// Have an empty column in the tableau. Add cards to this empty column
		ModelFactory.init(game.column3, "9D 8D 7D");
		
		// Add some cards to another column
		ModelFactory.init(game.column6, "QD JD 10D");
		
		// Create a mouse event by pressing on the card in the waste pile
		MouseEvent pr1 = createPressed(game, game.columnView3, 0, 0);
		game.columnView3.getMouseManager().handleMouseEvent(pr1);
		
		// Create a mouse released event on the target tableau column
		MouseEvent rel1 = createReleased(game, game.columnView6, 0, 0);
		game.columnView6.getMouseManager().handleMouseEvent(rel1);
		
		// Check if the card moved was equal
		assertEquals(new Card(Card.SEVEN, Card.DIAMONDS), game.column6.peek());
		
		// Undo the move
		assertTrue(game.undoMove());
	}
}
