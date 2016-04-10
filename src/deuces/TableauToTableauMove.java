package deuces;

import ks.common.model.Column;
import ks.common.model.MutableInteger;
import ks.common.games.Solitaire;
import ks.common.model.Card;

public class TableauToTableauMove extends ks.common.model.Move {
	
	/** The deck. */
	protected Column targetTableauColumn;

	/** The wastePile. */
	protected Column sourceTableauColumn;
	
	protected Card cardBeingDragged;
	
	protected MutableInteger wasteNum;
/**
 * DealCardMove constructor.
 * @param Deck deck
 * @param Pile wastePile
 */
	public TableauToTableauMove (Column sourceTableauColumn, Card cardBeingDragged, Column targetTableauColumn, MutableInteger wasteNum) {
		super();
	
		this.targetTableauColumn = targetTableauColumn;
		this.cardBeingDragged = cardBeingDragged;
		this.sourceTableauColumn = sourceTableauColumn;
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
			targetTableauColumn.add (sourceTableauColumn.get());
		else
			targetTableauColumn.add (cardBeingDragged);

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
		sourceTableauColumn.add (targetTableauColumn.get());

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
			if (sourceTableauColumn.empty()) return false;   // NOTHING TO EXTRACT!
			c = sourceTableauColumn.peek();
		} else {
			c = cardBeingDragged;
		}
		
		// moveWasteToFoundation(waste,pile) : not foundation.empty() and not waste.empty() and 
		if ((!targetTableauColumn.empty()) && (c.getRank() == targetTableauColumn.rank() - 1) && (c.getSuit() == targetTableauColumn.suit()))
			validation = true;
		
		else if (targetTableauColumn.empty()){
			validation = true;
		}

		return validation;
	}

}
