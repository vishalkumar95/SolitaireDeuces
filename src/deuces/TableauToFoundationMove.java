package deuces;

import ks.common.model.Pile;
import ks.common.model.Column;

import java.util.Stack;

import ks.common.games.Solitaire;
import ks.common.model.Card;

public class TableauToFoundationMove extends ks.common.model.Move {
	
	/** The deck. */
	protected Pile targetFoundationPile;

	/** The wastePile. */
	protected Column sourceTableauColumn;
	
	protected Column columnBeingDragged;
	
/**
 * DealCardMove constructor.
 * @param Deck deck
 * @param Pile wastePile
 * @return 
 */
	public TableauToFoundationMove (Column sourceTableauColumn, Column columnBeingDragged, Pile targetFoundationPile) {
		super();
	
		this.targetFoundationPile = targetFoundationPile;
		this.columnBeingDragged = columnBeingDragged;
		this.sourceTableauColumn = sourceTableauColumn;
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
		if (columnBeingDragged == null)
			targetFoundationPile.add (sourceTableauColumn.get());
		
		else {
			Stack<Card> stack = new Stack<Card>();
		
			for(; columnBeingDragged.peek() != null;) {
				Card tempCard = columnBeingDragged.get();
				stack.push(tempCard);
			}
			
			while (stack.size() != 0){
				targetFoundationPile.add((Card)stack.pop());
			}
		}

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
		sourceTableauColumn.add (targetFoundationPile.get());

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
		Column c;
		c = columnBeingDragged;
		if (columnBeingDragged == null) {
			if (sourceTableauColumn.empty()) return false;   // NOTHING TO EXTRACT!
			//c = sourceTableauColumn.peek();
		} else {
			c = columnBeingDragged;
		}
		
		Stack<Card> stack = new Stack<Card>();
		
		for(; c.peek() != null;) {
			Card tempCard = c.get();
			stack.push(tempCard);
		}
		
		// moveWasteToFoundation(waste,pile) : not foundation.empty() and not waste.empty() and
		if (stack.size() == 1){
			if (!targetFoundationPile.empty() && (stack.peek().getRank() == targetFoundationPile.rank() + 1) && (stack.peek().getSuit() == targetFoundationPile.suit())){
				System.err.println(stack.peek().getRank());
				validation = true;
			}
		}
		
		// Reconstitute the column
		while (stack.size() != 0){
			columnBeingDragged.add((Card) stack.pop());
		}

		return validation;
	}

}
