package deuces;

import ks.common.model.MultiDeck;
import ks.common.model.MutableInteger;
import ks.common.model.Column;

public class DealOneMove extends ks.common.model.Move {
	
	/** The deck. */
	protected MultiDeck multiDeck;

	/** The wastePile. */
	protected Column wastePile;
	
	protected MutableInteger wasteNum;
/**
 * DealCardMove constructor.
 * @param MultiDeck multiDeck
 * @param Pile wastePile
 * @param MutableInteger wasteNum
 */
	public DealOneMove (MultiDeck multiDeck,  Column wastePile, MutableInteger wasteNum) {
		super();
	
		this.multiDeck = multiDeck;
		this.wastePile = wastePile;
		this.wasteNum = wasteNum;
	}
	
	/**
	 * Do Move
	 * @param theGame ks.games.Solitaire 
	 */
	@Override
	public boolean doMove (ks.common.games.Solitaire game) {
		// VALIDATE
		if (valid(game) == false)
			return false;
			
		// EXECUTE:
		// Get card from deck
		wastePile.add (multiDeck.get());
		
		game.updateNumberCardsLeft (-1);
		wasteNum.increment(1);
		
		return true;	
	}
	/**
	 * To undo this move, we move the cards from the wastePile back to the Deck.
	 * @param theGame ks.games.Solitaire 
	 */
	@Override
	public boolean undo(ks.common.games.Solitaire game) {

		// VALIDATE:
		if (wastePile.empty()) return false;
			
		// UNDO:
		multiDeck.add (wastePile.get());

		// update the number of cards to go.
		game.updateNumberCardsLeft (+1);
		wasteNum.increment(-1);
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

		//    dealCard(deck,wastePile) : not deck.empty()
		if (!multiDeck.empty()) 
			validation = true;

		return validation;
	}

}
