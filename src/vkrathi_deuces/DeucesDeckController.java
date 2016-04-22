package vkrathi_deuces;

import vkrathi_deuces.DealOneMove;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.MultiDeck;
import ks.common.model.MutableInteger;
import vkrathi_deuces.Deuces;
import ks.common.model.Move;
import ks.common.model.Column;

public class DeucesDeckController extends SolitaireReleasedAdapter {
	/** The game. */
	protected Deuces theGame;

	/** The WastePile of interest. */
	protected Column wastePile;

	/** The Deck of interest. */
	protected MultiDeck multiDeck;
	
	protected MutableInteger wasteNum;

	/**
	 * DeucesDeckController constructor comment.
	 */
	public DeucesDeckController(Deuces theGame, MultiDeck d, Column wastePile, MutableInteger wasteNum ) {
		super(theGame);

		this.wasteNum = wasteNum;
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
		Move m = new DealOneMove (multiDeck, wastePile, wasteNum);
		if (m.doMove(theGame)) {
			theGame.pushMove (m);     // Successful DealFour Move
			theGame.refreshWidgets(); // refresh updated widgets.
		}
	}
}