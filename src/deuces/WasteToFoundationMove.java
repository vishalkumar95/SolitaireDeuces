package deuces;

import ks.common.model.Pile;
import ks.common.model.Column;
import ks.common.games.Solitaire;
import ks.common.model.Card;

public class WasteToFoundationMove extends ks.common.model.Move {
	
	/** The deck. */
	protected Pile targetFoundationPile;

	/** The wastePile. */
	protected Column wastePile;
	
	protected Card cardBeingDragged;
/**
 * DealCardMove constructor.
 * @param Deck deck
 * @param Pile wastePile
 */
	public WasteToFoundationMove (Column wastePile, Card cardBeingDragged, Pile targetFoundationPile) {
		super();
	
		this.targetFoundationPile = targetFoundationPile;
		this.cardBeingDragged = cardBeingDragged;
		this.wastePile = wastePile;
	}
	
	/**
	 * Do Move
	 * @param theGame ks.games.Solitaire 
	 */
	@Override
	public boolean doMove (Solitaire theGame) {
		// Verify we can do the move.
		if (valid (theGame) == false)
			return false;

		// EXECUTE:
		// Deal with both situations
		if (cardBeingDragged == null)
			targetFoundationPile.add (wastePile.get());
		else
			targetFoundationPile.add (cardBeingDragged);

		// advance score
		theGame.updateScore (1);
		return true;
	}
	/**
	 * To undo this move, we move the cards from the wastePile back to the Deck.
	 * @param theGame ks.games.Solitaire 
	 */
	@Override
	public boolean undo(ks.common.games.Solitaire game) {

		// VALIDATE:
		if (targetFoundationPile.empty()) return false;

		// EXECUTE:
		// remove card and move to waste.
		wastePile.add (targetFoundationPile.get());

		// reverse score advance
		game.updateScore (-1);
		return true;
	}
	/**
	 * Action for Deuces: Deal card from deck to wastePile.
	 * @param d ks.common.games.Solitaire
	 */
	@Override
	public boolean valid(ks.common.games.Solitaire game) {
		// VALIDATION:
		boolean validation = false;

		// If draggingCard is null, then no action has yet taken place.
		Card c;
		if (cardBeingDragged == null) {
			if (wastePile.empty()) return false;   // NOTHING TO EXTRACT!
			c = wastePile.peek();
		} else {
			c = cardBeingDragged;
		}
		
		// moveWasteToFoundation(waste,pile) : not foundation.empty() and not waste.empty() and 
		if (!targetFoundationPile.empty() && (c.getRank() == targetFoundationPile.rank() + 1) && (c.getSuit() == targetFoundationPile.suit()))
			validation = true;

		return validation;
	}

}
