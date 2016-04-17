package deuces;

import ks.common.model.Column;
import ks.common.model.MutableInteger;

import java.util.Stack;

import ks.common.games.Solitaire;
import ks.common.model.Card;

public class TableauToTableauMove extends ks.common.model.Move {
	
	/** The deck. */
	protected Column targetTableauColumn;

	/** The wastePile. */
	protected Column sourceTableauColumn;
	
	protected Column columnBeingDragged;
	
	protected MutableInteger wasteNum;
	
	int numSource;
/**
 * DealCardMove constructor.
 * @param Deck deck
 * @param Pile wastePile
 */
	public TableauToTableauMove (Column sourceTableauColumn, Column columnBeingDragged, Column targetTableauColumn, MutableInteger wasteNum) {
		super();
	
		this.targetTableauColumn = targetTableauColumn;
		this.columnBeingDragged = columnBeingDragged;
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
		if (columnBeingDragged == null)
			targetTableauColumn.add (sourceTableauColumn.get());
		else {
			Stack<Card> stack = new Stack<Card>();
			
			for(; columnBeingDragged.peek() != null;) {
				Card tempCard = columnBeingDragged.get();
				stack.push(tempCard);
			}
			
			numSource = stack.size();
			
			while (stack.size() != 0){
				targetTableauColumn.add((Card)stack.pop());
			}
		}

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
		
		Stack<Card> stack = new Stack<Card>();
		
		for(int i = 0; i < numSource; i++) {
			Card tempCard = targetTableauColumn.get();
			stack.push(tempCard);
		}

		// EXECUTE:
		// remove card and move to waste
		while (stack.size() != 0){
			sourceTableauColumn.add ((Card)stack.pop());
		}

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
		
		for (; c.peek() != null ;){
			Card tempCard = c.get();
			stack.push(tempCard);
		}
		
		// moveWasteToFoundation(waste,pile) : not foundation.empty() and not waste.empty() and 
		if ((!targetTableauColumn.empty()) && (stack.peek().getRank() == targetTableauColumn.rank() - 1) && (stack.peek().getSuit() == targetTableauColumn.suit()))
			validation = true;
		
		else if (targetTableauColumn.empty()){
			validation = true;
		}
		
		// Reconstitute the column
		while (stack.size() != 0){
			columnBeingDragged.add((Card) stack.pop());
		}

		return validation;
	}

}
