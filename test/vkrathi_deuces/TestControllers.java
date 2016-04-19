package vkrathi_deuces;

import java.awt.event.MouseEvent;

import deuces.Deuces;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class TestControllers extends KSTestCase {
	
	// this is the game under test.
	Deuces game;
	
	// window for game.
	GameWindow gw;
	
	protected void setUp() {
		game = new Deuces();
		
		// Because solitaire variations are expected to run within a container, we need to 
		// do this, even though the Container will never be made visible. Note that here
		// we select the "random seed" by which the deck will be shuffled. We use the 
		// special constant Deck.OrderBySuit (-2) which orders the deck from Ace of clubs
		// right to King of spades.
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
		game.wastePile.add(new Card(Card.THREE, Card.DIAMONDS));
		
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
		
//		// Check the waste number
//		int value = game.wasteNum.getValue();
//		assertEquals(1, value);
		
	}
	
	// Test movement from waste pile to tableau
	public void testWastePileTableauController(){
	
		// first get a card in the waste pile
		game.wastePile.add(new Card(Card.QUEEN, Card.DIAMONDS));
		
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
		game.column3.add(new Card(Card.QUEEN, Card.DIAMONDS));
		game.column3.add(new Card(Card.JACK, Card.DIAMONDS));
		game.column3.add(new Card(Card.TEN, Card.DIAMONDS));
		game.column3.add(new Card(Card.NINE, Card.DIAMONDS));
		game.column3.add(new Card(Card.EIGHT, Card.DIAMONDS));
		game.column3.add(new Card(Card.SEVEN, Card.DIAMONDS));
		game.column3.add(new Card(Card.SIX, Card.DIAMONDS));
		game.column3.add(new Card(Card.FIVE, Card.DIAMONDS));
		game.column3.add(new Card(Card.FOUR, Card.DIAMONDS));
		game.column3.add(new Card(Card.THREE, Card.DIAMONDS));
		
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
		game.column3.add(new Card(Card.QUEEN, Card.DIAMONDS));
		game.column3.add(new Card(Card.JACK, Card.DIAMONDS));
		game.column3.add(new Card(Card.TEN, Card.DIAMONDS));
		game.column3.add(new Card(Card.NINE, Card.DIAMONDS));
		game.column3.add(new Card(Card.EIGHT, Card.DIAMONDS));
		game.column3.add(new Card(Card.SEVEN, Card.DIAMONDS));
		game.column3.add(new Card(Card.SIX, Card.DIAMONDS));
		game.column3.add(new Card(Card.FIVE, Card.DIAMONDS));
		game.column3.add(new Card(Card.FOUR, Card.DIAMONDS));
		game.column3.add(new Card(Card.THREE, Card.DIAMONDS));
		
		// Create a mouse event by pressing on the card in the waste pile
		MouseEvent pr = createPressed(game, game.columnView3, 0, 0);
		game.columnView3.getMouseManager().handleMouseEvent(pr);
		
		// Create a mouse released event on the target tableau column
		MouseEvent rel = createReleased(game, game.pileView3, 0, 0);
		game.pileView3.getMouseManager().handleMouseEvent(rel);
		
		// Have an empty column in the tableau. Add cards to this empty column
		game.column3.add(new Card(Card.NINE, Card.DIAMONDS));
		game.column3.add(new Card(Card.EIGHT, Card.DIAMONDS));
		game.column3.add(new Card(Card.SEVEN, Card.DIAMONDS));
		
		// Add some cards to another column
		game.column6.add(new Card(Card.QUEEN, Card.DIAMONDS));
		game.column6.add(new Card(Card.JACK, Card.DIAMONDS));
		game.column6.add(new Card(Card.TEN, Card.DIAMONDS));
		
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
