package deuces;

import ks.client.gamefactory.GameWindow;
import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.*;
import ks.common.view.*;
import ks.launcher.Main;

/** This is an implementation of the Deuces version of the Solitaire game
 * 
 * @author Vishal Rathi (vkrathi)
 *
 */

public class Dueces extends Solitaire {
	
	// Each game has two decks
	protected MultiDeck multiDeck;
	
	// Add 8 piles to the game
	protected Pile pile1, pile2, pile3, pile4, pile5, pile6, pile7, pile8;
	
	// Add 11 columns to the game
	protected Column column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, wastePile;
	
	// Add a deck view to the game
	protected DeckView deckView;
	
	// Add pile views for the piles
	protected PileView pileView1, pileView2, pileView3, pileView4, pileView5, pileView6, pileView7, pileView8;
	
	// Add column views for the columns
	protected ColumnView columnView1, columnView2, columnView3, columnView4, columnView5, columnView6, columnView7, columnView8, columnView9, columnView10;
	
	// Add a row view for the waste pile
	protected RowView wastePileView;
	
	// Add an integer view to display the score
	protected IntegerView scoreView;
	
	// Add an integer view to display the number of cards left in the deck
	protected IntegerView numLeftView;
	
	/** To select a new deck type uncomment this method */
	@Override
	public String getDeckType() {
			return "tiny";
	}
	
	/**
	 * Prepare the controllers.
	 */
	private void initializeController() {
		// Initialize Controllers for DeckView
		deckView.setMouseAdapter(new DuecesDeckController (this, multiDeck, wastePile));
		deckView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		deckView.setUndoAdapter (new SolitaireUndoAdapter(this));

	}
	
	/** Return the name of this solitaire variation. */
	public String getName() {
		return "Deuces";
	}
	
	/**
	 * Initialize the Model for Deuces.
	 *
	 * Don't forget to create the standard Score and numberCardsLeft model elements.
	 *
	 * @param seed    used to shuffle identical decks.
	 */
	private void initializeModel(int seed) {

		// initial score is set to ZERO (every Solitaire game by default has a score) 
		// and there are 52 cards left.
		numLeft = getNumLeft();
		numLeft.setValue(104);
		score = getScore();
		score.setValue(0);
		
		// add to our model a deck 
		multiDeck = new MultiDeck(2);
		multiDeck.create(seed);
		addModelElement(multiDeck);

		pile1 = new Pile("pile1");
		pile2 = new Pile("pile2");
		pile3 = new Pile("pile3");
		pile4 = new Pile("pile4");
		pile5 = new Pile("pile5");
		pile6 = new Pile("pile6");
		pile7 = new Pile("pile7");
		pile8 = new Pile("pile8");
		
		column1 = new Column("column1");
		column2 = new Column("column2");
		column3 = new Column("column3");
		column4 = new Column("column4");
		column5 = new Column("column5");
		column6 = new Column("column6");
		column7 = new Column("column7");
		column8 = new Column("column8");
		column9 = new Column("column9");
		column10 = new Column("column10");
		wastePile = new Column("wastePile");

		// add to our model a set of 8 piles
		addModelElement(pile1);
		addModelElement(pile2);
		addModelElement(pile3);
		addModelElement(pile4);
		addModelElement(pile5);
		addModelElement(pile6);
		addModelElement(pile7);
		addModelElement(pile8);
		
		// add to our model a set of 11 columns
		addModelElement(column1);
		addModelElement(column2);
		addModelElement(column3);
		addModelElement(column4);
		addModelElement(column5);
		addModelElement(column6);
		addModelElement(column7);
		addModelElement(column8);
		addModelElement(column9);
		addModelElement(column10);
		addModelElement(wastePile);
	}
	
	/**
	 * Prepare the views
	 */
	private void initializeView() {
		// Get the card artwork to be used. This is needed for the dimensions.
		CardImages ci = getCardImages();

		// add to our view (as defined within our superclass). Similar for other widgets
		deckView = new DeckView(multiDeck);
		deckView.setBounds(20, 120 + 4 * ci.getHeight(), ci.getWidth(), ci.getHeight());
		container.addWidget(deckView);
		
		wastePileView = new RowView(wastePile);
		wastePileView.setBounds(40 + ci.getWidth(), 120 + 4 * ci.getHeight(), 100 * ci.getWidth(), ci.getHeight());
		container.addWidget(wastePileView);

		pileView1 = new PileView(pile1);
		pileView1.setBounds(40 + ci.getWidth(), 20, ci.getWidth(), ci.getHeight());
		container.addWidget(pileView1);

		pileView2 = new PileView(pile2);
		pileView2.setBounds(60 + 2 * ci.getWidth(), 20, ci.getWidth(), ci.getHeight());
		container.addWidget(pileView2);

		pileView3 = new PileView(pile3);
		pileView3.setBounds(80 + 3 * ci.getWidth(), 20, ci.getWidth(), ci.getHeight());
		container.addWidget(pileView3);

		pileView4 = new PileView(pile4);
		pileView4.setBounds(100 + 4 * ci.getWidth(), 20, ci.getWidth(), ci.getHeight());
		container.addWidget(pileView4);
		
		pileView5 = new PileView(pile5);
		pileView5.setBounds(120 + 5 * ci.getWidth(), 20, ci.getWidth(), ci.getHeight());
		container.addWidget(pileView5);
		
		pileView6 = new PileView(pile6);
		pileView6.setBounds(140 + 6 * ci.getWidth(), 20, ci.getWidth(), ci.getHeight());
		container.addWidget(pileView6);
		
		pileView7 = new PileView(pile7);
		pileView7.setBounds(160 + 7 * ci.getWidth(), 20, ci.getWidth(), ci.getHeight());
		container.addWidget(pileView7);
		
		pileView8 = new PileView(pile8);
		pileView8.setBounds(180 + 8 * ci.getWidth(), 20, ci.getWidth(), ci.getHeight());
		container.addWidget(pileView8);
		
		columnView1 = new ColumnView(column1);
		columnView1.setBounds(20, 40 + ci.getHeight(), ci.getWidth(), 2 * ci.getHeight());
		container.addWidget(columnView1);
		
		columnView2 = new ColumnView(column2);
		columnView2.setBounds(40 + ci.getWidth(), 40 + ci.getHeight(), ci.getWidth(), 2 * ci.getHeight());
		container.addWidget(columnView2);
		
		columnView3 = new ColumnView(column3);
		columnView3.setBounds(60 + 2 * ci.getWidth(), 40 + ci.getHeight(), ci.getWidth(), 2 * ci.getHeight());
		container.addWidget(columnView3);
		
		columnView4 = new ColumnView(column4);
		columnView4.setBounds(80 + 3 * ci.getWidth(), 40 + ci.getHeight(), ci.getWidth(), 2 * ci.getHeight());
		container.addWidget(columnView4);
		
		columnView5 = new ColumnView(column5);
		columnView5.setBounds(100 + 4 * ci.getWidth(), 40 + ci.getHeight(), ci.getWidth(), 2 * ci.getHeight());
		container.addWidget(columnView5);
		
		columnView6 = new ColumnView(column6);
		columnView6.setBounds(120 + 5 * ci.getWidth(), 40 + ci.getHeight(), ci.getWidth(), 2 * ci.getHeight());
		container.addWidget(columnView6);
		
		columnView7 = new ColumnView(column7);
		columnView7.setBounds(140 + 6 * ci.getWidth(), 40 + ci.getHeight(), ci.getWidth(), 2 * ci.getHeight());
		container.addWidget(columnView7);
		
		columnView8 = new ColumnView(column8);
		columnView8.setBounds(160 + 7 * ci.getWidth(), 40 + ci.getHeight(), ci.getWidth(), 2 * ci.getHeight());
		container.addWidget(columnView8);
		
		columnView9 = new ColumnView(column9);
		columnView9.setBounds(180 + 8 * ci.getWidth(), 40 + ci.getHeight(), ci.getWidth(), 2 * ci.getHeight());
		container.addWidget(columnView9);
		
		columnView10 = new ColumnView(column10);
		columnView10.setBounds(200 + 9 * ci.getWidth(), 40 + ci.getHeight(), ci.getWidth(), 2 * ci.getHeight());
		container.addWidget(columnView10);

		scoreView = new IntegerView(getScore());
		scoreView.setBounds(220 + 10 * ci.getWidth(), 20, 100, 60);
		container.addWidget(scoreView);

		numLeftView = new IntegerView(getNumLeft());
		numLeftView.setBounds(320 + 10* ci.getWidth(), 20, 100, 60);
		container.addWidget(numLeftView);
		
		/*// Finally, cover the Container for any events not handled by a widget:
		getContainer().setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
		getContainer().setMouseAdapter (new SolitaireReleasedAdapter(this));
		getContainer().setUndoAdapter (new SolitaireUndoAdapter(this));*/
	}
	
	/** Determine whether game has been won. */
	public boolean hasWon() {
		return false;
	}
	
	/** Initialize solitaire variation. */
	public void initialize() {
		// Initialize model, view, and controllers.
		initializeModel(getSeed());
		initializeView();
		initializeController();

		// Prepare game AFTER all controllers are set up.
		// each pile gets a card from the deck.
		pile1.add (multiDeck.get());
		pile2.add (multiDeck.get());
		pile3.add (multiDeck.get());
		pile4.add (multiDeck.get());
		pile5.add (multiDeck.get());
		pile6.add (multiDeck.get());
		pile7.add (multiDeck.get());
		pile8.add (multiDeck.get());

		updateScore(8);
		// we have dealt four cards.
		updateNumberCardsLeft (-18);	
	}

	public static void main(String[] args) {
		// Seed is to ensure we get the same initial cards every time.
		GameWindow gw = Main.generateWindow(new Dueces(), Deck.OrderByRank);
		gw.setVisible(true);

	}

}
