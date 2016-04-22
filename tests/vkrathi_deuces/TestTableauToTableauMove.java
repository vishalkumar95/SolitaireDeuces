package vkrathi_deuces;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.model.ModelFactory;
import vkrathi_deuces.Deuces;
import vkrathi_deuces.TableauToTableauMove;

public class TestTableauToTableauMove extends TestCase {
	
	Deuces deuces;
	GameWindow gw;
	
	@Override
	protected void setUp() {
		deuces = new Deuces();
		gw = Main.generateWindow(deuces, Deck.OrderByRank);
	}
	
	public void testTableauToTableauMove(){
		
		// Test that the ace can be built upon by kings
		
		// Add an ace in one of the tableau columns
		ModelFactory.init(deuces.column1, "1D");
		ModelFactory.init(deuces.column2, "KD QD JD");
		
		// Get the column to move
		Column draggingColumn = (Column) deuces.columnView2.getModelElement();
		
		TableauToTableauMove ttm = new TableauToTableauMove(deuces.column2, draggingColumn, deuces.column1, deuces.wasteNum);
		
		boolean value = ttm.valid(deuces);
		
		assertEquals(true, value);
		
		ttm.doMove(deuces);
		
		assertEquals(true, deuces.column2.empty());
		
		ttm.undo(deuces);
	}
	
	public void testTableauToTableauMoveMore(){
		
		// Test that the ace can be built upon by kings
		
		// Add an ace in one of the tableau columns
		ModelFactory.init(deuces.column1, "");
		ModelFactory.init(deuces.column2, "KD QD JD");
		
		// Get the column to move
		Column draggingColumn = (Column) deuces.columnView2.getModelElement();
		
		TableauToTableauMove ttm = new TableauToTableauMove(deuces.column2, draggingColumn, deuces.column1, deuces.wasteNum);
		
		boolean value = ttm.valid(deuces);
		
		assertEquals(true, value);
		
		ttm.doMove(deuces);
		
		assertEquals(true, deuces.column2.empty());
		
		ttm.undo(deuces);
	}
}
