package deuces;

import java.awt.event.MouseEvent;

import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

import heineman.Klondike;

/**
 * Controls all actions to do with mouse events over Foundation Piles
 * <p>
 * Creation date: (11/10/01 11:51:49 PM)
 * @author: George T. Heineman (heineman@cs.wpi.edu)
 */
public class DeucesFoundationController extends java.awt.event.MouseAdapter {
	/** The Klondike Game. */
	protected Deuces theGame;

	/** The specific Foundation pileView being controlled. */
	protected PileView src;
	/**
	 * FoundationController constructor comment.
	 */
	public DeucesFoundationController(Deuces theGame, PileView foundation) {
		super();

		this.theGame = theGame;
		this.src = foundation;
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
		Pile foundation = (Pile) src.getModelElement();

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

		// must use peek() so we don't modify col prematurely
		Move m = new WasteToFoundationMove (wastePile, theCard, foundation);
		if (m.doMove (theGame)) {
			// Success
			theGame.pushMove (m);
		} else {
			fromWidget.returnWidget (draggingWidget);
		}

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
}
