package deuces;

import deuces.Dueces;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.MultiDeck;
import ks.common.model.Move;
import ks.common.model.Column;

public class DuecesDeckController extends SolitaireReleasedAdapter {
	/** The game. */
	protected Dueces theGame;

	/** The WastePile of interest. */
	protected Column wastePile;

	/** The Deck of interest. */
	protected MultiDeck multiDeck;

	/**
	 * KlondikeDeckController constructor comment.
	 */
	public DuecesDeckController(Dueces theGame, MultiDeck d, Column wastePile) {
		super(theGame);

		this.theGame = theGame;
		this.wastePile = wastePile;
		this.multiDeck = d;
	}

	/**
	 * Coordinate reaction to the beginning of a Drag Event. In this case,
	 * no drag is ever achieved, and we simply deal upon the pres.
	 */
	public void mousePressed (java.awt.event.MouseEvent me) {

		// Attempting a DealFourCardMove
		Move m = new DealOneMove (multiDeck, wastePile);
		if (m.doMove(theGame)) {
			theGame.pushMove (m);     // Successful DealFour Move
			theGame.refreshWidgets(); // refresh updated widgets.
		}
	}
}