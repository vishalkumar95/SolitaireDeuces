package vkrathi_deuces;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.model.ModelFactory;
import vkrathi_deuces.Deuces;
import vkrathi_deuces.TableauToFoundationMove;
import vkrathi_deuces.TableauToTableauMove;

public class TestTableauToFoundationMove extends TestCase {
	
	Deuces deuces;
	GameWindow gw;
	
	@Override
	protected void setUp() {
		deuces = new Deuces();
		gw = Main.generateWindow(deuces, Deck.OrderByRank);
	}
	
	public void testTableauToFoundationMove(){
		
		// Test that the ace can be put on a king in foundation
		
		// Add an ace in the foundation pile
		ModelFactory.init(deuces.pile1, "KD");
		ModelFactory.init(deuces.column2, "1D");
		
		// Get the column to move
		Column draggingColumn = (Column) deuces.columnView2.getModelElement();
		
		TableauToFoundationMove tfm = new TableauToFoundationMove(deuces.column2, draggingColumn, deuces.pile1);
		
		boolean value = tfm.valid(deuces);
		
		assertEquals(true, value);
		
		tfm.doMove(deuces);
		
		assertEquals(true, deuces.column2.empty());
		
		tfm.undo(deuces);
	}
}
