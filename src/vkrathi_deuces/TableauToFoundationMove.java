package vkrathi_deuces;

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
	
	int numSource;
	
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
		
		int numScoreUpdate = 0;

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
			
			// Update the integer values to be used later
			numScoreUpdate = stack.size();
			numSource = stack.size();
			
			// Add cards to the destination
			while (stack.size() != 0){
				targetFoundationPile.add((Card)stack.pop());
			}
		}

		// advance score
		theGame.updateScore (numScoreUpdate);
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
		
		Stack<Card> stack = new Stack<Card>();
		
		for(int i = 0; i < numSource; i++) {
			Card tempCard = targetFoundationPile.get();
			stack.push(tempCard);
		}
		
		// Create a new stack and store the cards in reverse order
		Stack<Card> stacktemp = new Stack<Card>();
		while (stack.size() != 0){
			Card tempCardReverse = stack.pop();
			stacktemp.push(tempCardReverse);
		}
		
		// Move cards back from destination to source
		while (stacktemp.size() != 0){
			sourceTableauColumn.add ((Card)stacktemp.pop());
		}
		
		// reverse score advance
		game.updateScore (-numSource);
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
		
		// Create a stack to store the cards from the columnBeingDragged
		Stack<Card> stack = new Stack<Card>();
		
		for(; c.peek() != null;) {
			Card tempCard = c.get();
			stack.push(tempCard);
		}
		
		// Create a new stack and store the cards in reverse order
		Stack<Card> stacktemp = new Stack<Card>();
		while (stack.size() != 0){
			Card tempCardReverse = stack.pop();
			stacktemp.push(tempCardReverse);
		}
		
		// moveWasteToFoundation(waste,pile) : not foundation.empty() and not waste.empty() and
		if (!targetFoundationPile.empty() && (stacktemp.peek().getRank() == targetFoundationPile.rank() + 1) && (stacktemp.peek().getSuit() == targetFoundationPile.suit())){
			validation = true;
		}
		
		// Chack if it is an ace.
		else if (!targetFoundationPile.empty() && (targetFoundationPile.rank() == 13 ) && (stacktemp.peek().getRank() == 1) && (stacktemp.peek().getSuit() == targetFoundationPile.suit())){
			validation = true;
		}
		
		// Reconstitute the column
		while (stacktemp.size() != 0){
			columnBeingDragged.add((Card) stacktemp.pop());
		}
		
		return validation;		
	}
}
