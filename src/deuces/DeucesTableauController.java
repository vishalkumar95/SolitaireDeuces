package deuces;

import java.awt.event.MouseEvent;

import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.MutableInteger;
import ks.common.view.CardView;
import ks.common.view.Container;
import ks.common.view.ColumnView;
import ks.common.view.Widget;


/**
 * Controls all actions to do with mouse events over Foundation Piles
 * <p>
 * Creation date: (11/10/01 11:51:49 PM)
 * @author: George T. Heineman (heineman@cs.wpi.edu)
 */
public class DeucesTableauController extends java.awt.event.MouseAdapter {
	/** The Klondike Game. */
	protected Deuces theGame;

	/** The specific Foundation pileView being controlled. */
	protected ColumnView src;
	
	protected MutableInteger wasteNum;
	/**
	 * FoundationController constructor comment.
	 */
	public DeucesTableauController(Deuces theGame, ColumnView tableau, MutableInteger wasteNum) {
		super();

		this.wasteNum = wasteNum;
		this.theGame = theGame;
		this.src = tableau;
	}
	/**
	 * Coordinate reaction to the completion of a Drag Event.
	 * <p>
	 * A bit of a challenge to construct the appropriate move, because cards
	 * can be dragged both from the WastePile (as a CardView object) and the 
	 * BuildablePileView (as a ColumnView).
	 * @param me java.awt.event.MouseEvent
	 */
	public void mouseReleased(MouseEvent me) {
		Container c = theGame.getContainer();

		/** Return if there is no card being dragged chosen. */
		Widget draggingWidget = c.getActiveDraggingObject();
		if (draggingWidget == Container.getNothingBeingDragged()) {
			System.err.println ("FoundationController::mouseReleased() unexpectedly found nothing being dragged.");
			c.releaseDraggingObject();		
			return;
		}

		/** Recover the from BuildablePile OR waste Pile */
		Widget fromWidget = c.getDragSource();
		if (fromWidget == null) {
			System.err.println ("FoundationController::mouseReleased(): somehow no dragSource in container.");
			c.releaseDraggingObject();
			return;
		}

		// Determine the To Pile
		Column tableau = (Column) src.getModelElement();

		// Coming from the waste [number of cards being dragged must be one]
		Column wastePile = (Column) fromWidget.getModelElement();

		/** Must be the CardView widget being dragged. */
		CardView cardView = (CardView) draggingWidget;
		Card theCard = (Card) cardView.getModelElement();
		if (theCard == null) {
			System.err.println ("FoundationController::mouseReleased(): somehow CardView model element is null.");
			c.releaseDraggingObject();
			return;
		}

		if(fromWidget.getName().startsWith("RowView")){
		// must use peek() so we don't modify col prematurely
			Move m = new WasteToTableauMove (wastePile, theCard, tableau, wasteNum);
			if (m.doMove (theGame)) {
				// Success
				theGame.pushMove (m);
				theGame.refreshWidgets();
			} else {
				fromWidget.returnWidget (draggingWidget);
			}
		}
		
		else if(fromWidget.getName().startsWith("ColumnView")){
		// must use peek() so we don't modify col prematurely
			Move m = new TableauToTableauMove (wastePile, theCard, tableau, wasteNum);
			if (m.doMove (theGame)) {
				// Success
				theGame.pushMove (m);
				theGame.refreshWidgets();
			} else {
				fromWidget.returnWidget (draggingWidget);
			}
		}
		
		//TODO : Work on separating the move classes

		// Ahhhh. Instead of dealing with multiple 'instanceof' difficulty, why don't we allow
		// for multiple controllers to be set on the same widget? Each will be invoked, one
		// at a time, until someone returns TRUE (stating that they are processing the event).
		// Then we have controllers for each MOVE TYPE, not just for each entity. In this way,
		// I wouldn't have to convert the CardView from wastePile into a ColumnView. I would
		// still have to do some sort of instanceOf check, however, to validate: But if the
		// instanceof failed, the controller could safely return and say NOT ME! See! There
		// always is a way to avoid layered if statements in OO.

		// release the dragging object, (this will reset dragSource)
		c.releaseDraggingObject();
		
		// finally repaint
		c.repaint();
	}
	
	/**
	 * Coordinate reaction to the beginning of a Drag Event.
	 * <p>
	 * @param me java.awt.event.MouseEvent
	 */
	public void mousePressed(MouseEvent me) {
	 
		// The container manages several critical pieces of information; namely, it
		// is responsible for the draggingObject; in our case, this would be a CardView
		// Widget managing the card we are trying to drag between two piles.
		Container c = theGame.getContainer();
		
		/** Return if there is no card to be chosen. */
		Column tableau = (Column) src.getModelElement();
		if (tableau.count() == 0) {
			c.releaseDraggingObject();
			return;
		}
	
		// Get a card to move from PileView. Note: this returns a CardView.
		// Note that this method will alter the model for PileView if the condition is met.
		CardView cardView = src.getCardViewForTopCard (me);
		
		// an invalid selection of some sort.
		if (cardView == null) {
			c.releaseDraggingObject();
			return;
		}	
		
		// If we get here, then the user has indeed clicked on the top card in the PileView and
		// we are able to now move it on the screen at will. For smooth action, the bounds for the
		// cardView widget reflect the original card location on the screen.
		Widget w = c.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println ("WastePileController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
			return;
		}
	
		// Tell container which object is being dragged, and where in that widget the user clicked.
		c.setActiveDraggingObject (cardView, me);
		
		// Tell container which source widget initiated the drag
		c.setDragSource (src);
	
		// The only widget that could have changed is ourselves. If we called refresh, there
		// would be a flicker, because the dragged widget would not be redrawn. We simply
		// force the WastePile's image to be updated, but nothing is refreshed on the screen.
		// This is patently OK because the card has not yet been dragged away to reveal the
		// card beneath it.  A bit tricky and I like it!
		src.redraw();
	}
}
