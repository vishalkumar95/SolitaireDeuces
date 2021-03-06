package vkrathi_deuces;

import ks.common.model.Column;
import ks.common.model.MutableInteger;
import ks.common.games.Solitaire;
import ks.common.model.Card;

public class WasteToTableauMove extends ks.common.model.Move {
	
	/** The deck. */
	protected Column targetTableauColumn;

	/** The wastePile. */
	protected Column wastePile;
	
	protected Card cardBeingDragged;
	
	protected MutableInteger wasteNum;
/**
 * DealCardMove constructor.
 * @param Deck deck
 * @param Pile wastePile
 */
	public WasteToTableauMove (Column wastePile, Card cardBeingDragged, Column targetTableauColumn, MutableInteger wasteNum) {
		super();
	
		this.targetTableauColumn = targetTableauColumn;
		this.cardBeingDragged = cardBeingDragged;
		this.wastePile = wastePile;
		this.wasteNum = wasteNum;
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
			targetTableauColumn.add (wastePile.get());
		else
			targetTableauColumn.add (cardBeingDragged);
		
		wasteNum.increment(-1);

		return true;
	}
	/**
	 * To undo this move, we move the cards from the wastePile back to the Deck.
	 * @param theGame ks.games.Solitaire 
	 */
	@Override
	public boolean undo(ks.common.games.Solitaire game) {

		// VALIDATE:
		if (targetTableauColumn.empty()) return false;

		// EXECUTE:
		// remove card and move to waste.
		wastePile.add (targetTableauColumn.get());
		
		wasteNum.increment(1);

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
		if ((!targetTableauColumn.empty()) && (c.getRank() == targetTableauColumn.rank() - 1) && (c.getSuit() == targetTableauColumn.suit()))
			validation = true;
		
		// If the card in the tableau column is aces then it can be nuilt upon by king
		if ((!targetTableauColumn.empty()) && (targetTableauColumn.rank() == 1) && (c.getRank() == 13) && (c.getSuit() == targetTableauColumn.suit()))
			validation = true;
		
		else if (targetTableauColumn.empty()){
			validation = true;
		}

		return validation;
	}

}
