package deuces;

import java.awt.event.MouseEvent;

import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.MutableInteger;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

import heineman.Klondike;

/**
 * Controls all actions to do with mouse events over Foundation Piles
 * <p>
 */
public class DeucesFoundationController extends java.awt.event.MouseAdapter {
	/** The Deuces Game. */
	protected Deuces theGame;

	/** The specific Foundation pileView being controlled. */
	protected PileView src;
	
	protected MutableInteger wasteNum;
	/**
	 * FoundationController constructor comment.
	 */
	public DeucesFoundationController(Deuces theGame, PileView foundation, MutableInteger wasteNum) {
		super();

		this.wasteNum = wasteNum;
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

		// must use peek() so we don't modify col prematurely
		
		if(fromWidget.getName().startsWith("RowView")){
			
			/** Must be the CardView widget being dragged. */
			CardView cardView = (CardView) draggingWidget;
			Card theCard = (Card) cardView.getModelElement();
			if (theCard == null) {
				System.err.println ("FoundationController::mouseReleased(): somehow CardView model element is null.");
				c.releaseDraggingObject();
				return;
			}

			Move m = new WasteToFoundationMove (wastePile, theCard, foundation, wasteNum);
			if (m.doMove (theGame)) {
				// Success
				theGame.pushMove (m);
				theGame.refreshWidgets();
			} else {
				fromWidget.returnWidget (draggingWidget);
			}
		}
		
		else if (fromWidget.getName().startsWith("ColumnView")){
			
			/** Must be the CardView widget being dragged. */
			ColumnView columnView = (ColumnView) draggingWidget;
			Column theColumn = (Column) columnView.getModelElement();
			if (theColumn == null) {
				System.err.println ("FoundationController::mouseReleased(): somehow CardView model element is null.");
				c.releaseDraggingObject();
				return;
			}

			
			Move m = new TableauToFoundationMove (wastePile, theColumn, foundation);
			if (m.doMove (theGame)) {
				// Success
				theGame.pushMove (m);
				theGame.refreshWidgets();
			} else {
				fromWidget.returnWidget (draggingWidget);
			}
		}

		// release the dragging object, (this will reset dragSource)
		c.releaseDraggingObject();
		
		// finally repaint
		c.repaint();
	}
}
