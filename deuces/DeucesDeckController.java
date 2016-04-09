package deuces;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.games.Solitaire;
import ks.common.model.MultiDeck;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

public class DeucesDeckController extends SolitaireReleasedAdapter {
	
	/** NarcoticDeckController constructor comment. */
    public DeucesDeckController(Solitaire game) {
        super(game);
    }
    
    /**
     * A MousePressed event on the DeckView is a signal to deal the next
     * set of cards (if the deck is nonempty).
     *
     * @param me     low-level mouse event
     */
    public void mousePressed(java.awt.event.MouseEvent me) {

        // Find the deck from our model
        MultiDeck d = (MultiDeck) theGame.getModelElement("multideck");
        
        // Get the wastePile object
        Column wastePile = (Column) theGame.getModelElement("wastePile");
    
    } 

}
