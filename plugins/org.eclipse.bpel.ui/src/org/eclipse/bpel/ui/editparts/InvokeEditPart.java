/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.ui.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.common.ui.layouts.AlignedFlowLayout;
import org.eclipse.bpel.model.CompensationHandler;
import org.eclipse.bpel.model.FaultHandler;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.adapters.ICompensationHandlerHolder;
import org.eclipse.bpel.ui.adapters.IFaultHandlerHolder;
import org.eclipse.bpel.ui.adapters.ILabeledElement;
import org.eclipse.bpel.ui.editparts.borders.LeafBorder;
import org.eclipse.bpel.ui.editparts.figures.GradientFigure;
import org.eclipse.bpel.ui.figures.CenteredConnectionAnchor;
import org.eclipse.bpel.ui.util.BPELDragEditPartsTracker;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.marker.BPELEditPartMarkerDecorator;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;


/**
 * There is a lot of code here copied from ScopeEditPart, but there was
 * no obvious way to share this code in a meaningful way.
 */
public class InvokeEditPart extends LeafEditPart {
	// Whether to show each of the handlers.
	// TODO: Initialize these from the preferences store
	private boolean showFH = false, showCH = false;
	
	// The figure which holds contentFigure and auxilaryFigure as children
	private IFigure parentFigure;
	
	// The figure which holds fault handler, compensation handler and event handler
	private IFigure auxilaryFigure;
	
	/**
	 * Override getDragTracker to supply double-click behaviour to expand
	 * the fault handler if one exists
	 */
	public DragTracker getDragTracker(Request request) {
		return new BPELDragEditPartsTracker(this) {
			protected boolean handleButtonDown(int button) {
				Point point = getLocation();
				if (border.isPointInFaultImage(point.x, point.y)) {
					setShowFaultHandler(!getShowFaultHandler());
				}
				if (border.isPointInCompensationImage(point.x, point.y)) {
					setShowCompensationHandler(!getShowCompensationHandler());
				}
				return super.handleButtonDown(button);
			}
		};
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	public void refreshVisuals() {
		super.refreshVisuals();
		border.setShowFault(getFaultHandler() != null);
		border.setShowCompensation(getCompensationHandler() != null);
		getFigure().repaint();
	}

	// here's the one from scope edit part
	protected IFigure createFigure() {
		if (image == null) {
			ILabeledElement element = (ILabeledElement)BPELUtil.adapt(getActivity(), ILabeledElement.class);
			image = element.getSmallImage(getActivity());
		}
		this.imageLabel = new Label(image);
		this.nameLabel = new Label(getLabel());

		editPartMarkerDecorator = new BPELEditPartMarkerDecorator((EObject)getModel(), new LeafDecorationLayout());
		editPartMarkerDecorator.addMarkerMotionListener(getMarkerMotionListener());
		
		this.parentFigure = new Figure();
		AlignedFlowLayout layout = new AlignedFlowLayout();
		layout.setHorizontal(true);
		layout.setHorizontalSpacing(0);
		layout.setVerticalSpacing(0);
		parentFigure.setLayoutManager(layout);
		
		IFigure gradient = new GradientFigure(getModel());
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setMinorAlignment(FlowLayout.ALIGN_CENTER);
		gradient.setLayoutManager(flowLayout);
		gradient.setForegroundColor(BPELUIPlugin.getPlugin().getColorRegistry().get(IBPELUIConstants.COLOR_BLACK));
		gradient.add(imageLabel);
		gradient.add(nameLabel);
		gradient.addMouseMotionListener(getMouseMotionListener());
		this.contentFigure = gradient;
		parentFigure.add(contentFigure);
		
		this.auxilaryFigure = new Figure();
		layout = new AlignedFlowLayout();
		layout.setHorizontal(true);
		auxilaryFigure.setLayoutManager(layout);
		parentFigure.add(auxilaryFigure);
		
		this.border = new LeafBorder(gradient);
		border.setShowFault(getFaultHandler() != null);
		border.setShowCompensation(getCompensationHandler() != null);
		gradient.setBorder(border);
		
		return editPartMarkerDecorator.createFigure(parentFigure);
	}
	
	public void setShowFaultHandler(boolean showFaultHandler) {
		this.showFH = showFaultHandler;
		// Call refresh so that both refreshVisuals and refreshChildren will be called.
		refresh();
	}

	public void setShowCompensationHandler(boolean showCompensationHandler) {
		this.showCH = showCompensationHandler;
		// Call refresh so that both refreshVisuals and refreshChildren will be called.
		refresh();
	}
		
	public boolean getShowFaultHandler() {
		return showFH;
	}

	public boolean getShowCompensationHandler() {
		return showCH;
	}
	
	public FaultHandler getFaultHandler() {
		IFaultHandlerHolder holder = (IFaultHandlerHolder)BPELUtil.adapt(getActivity(), IFaultHandlerHolder.class);
		if (holder != null) {
			return holder.getFaultHandler(getActivity());
		}
		return null;
	}
	
	public CompensationHandler getCompensationHandler() {
		ICompensationHandlerHolder holder = (ICompensationHandlerHolder)BPELUtil.adapt(getActivity(), ICompensationHandlerHolder.class);
		if (holder != null) {
			return holder.getCompensationHandler(getActivity());
		}
		return null;
	}

	/**
	 * The top inner implicit connection needs an offset of eight to compensate for
	 * the expansion icon. All other connections are centered on the contentFigure
	 * with an offset of 0.
	 */
	public ConnectionAnchor getConnectionAnchor(int location) {
		if (location == CenteredConnectionAnchor.TOP_INNER) {
			return new CenteredConnectionAnchor(contentFigure, location, 8);			
		}
		return new CenteredConnectionAnchor(contentFigure, location, 0);
	}	
	
	/**
	 * Override addChildVisual so that it adds the childEditPart to the correct
	 * figure. FH/EH/CH go in a different figure than the activity does.
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		IFigure child = ((GraphicalEditPart)childEditPart).getFigure();
		IFigure content = getContentPane(childEditPart);
		if (content != null)
			content.add(child);
	}	

	/**
	 * Override removeChildVisual so that it removes the childEditPart from
	 * the correct figure. FH/EH/CH live in a different figure than the
	 * activity does.
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart)childEditPart).getFigure();
		getContentPane(childEditPart).remove(child);
	}
	
	/**
	 * Also overridden to call getContentPane(child) in the appropriate place.
	 */
	protected void reorderChild(EditPart child, int index) {
		// Save the constraint of the child so that it does not
		// get lost during the remove and re-add.
		IFigure childFigure = ((GraphicalEditPart) child).getFigure();
		LayoutManager layout = getContentPane(child).getLayoutManager();
		Object constraint = null;
		if (layout != null)
			constraint = layout.getConstraint(childFigure);

		removeChildVisual(child);
		List children = getChildren();
		children.remove(child);
		children.add(index, child);
		addChildVisual(child, index);
		
		setLayoutConstraint(child, childFigure, constraint);
	}

	/**
	 * Yet Another Overridden Method.
	 */
	public void setLayoutConstraint(EditPart child, IFigure childFigure, Object constraint) {
		getContentPane(child).setConstraint(childFigure, constraint);
	}

	/**
	 * This method hopefully shouldn't be called, in favour of getContentPane(EditPart).
	 */
	public IFigure getContentPane() {
		return contentFigure;
	}

	/**
	 * Return the appropriate content pane into which the given child
	 * should be inserted. For faultHandler, compensationHandler and
	 * eventHandler, the answer is auxilaryFigure; for activities it
	 * is the contentFigure.
	 */
	protected IFigure getContentPane(EditPart childEditPart) {
		Object model = childEditPart.getModel();
		if (model instanceof FaultHandler) {
			return auxilaryFigure;
		} else if (model instanceof CompensationHandler) {
			return auxilaryFigure;
		}
		return contentFigure;
	}
	
	/**
	 * Return a list of the model children that should be displayed. 
	 * This includes fault handlers and compensation handlers.
	 */
	protected List getModelChildren() {
    	ArrayList children = new ArrayList();

    	if (showFH) {
			FaultHandler faultHandler = this.getFaultHandler();
	    	if (faultHandler != null) children.add(children.size(), faultHandler);
    	}

    	if (showCH) {
	    	CompensationHandler compensationHandler = this.getCompensationHandler();
	    	if (compensationHandler != null) children.add(children.size(), compensationHandler);
    	}

    	return children;
    }
	
	public IFigure getMainActivityFigure() {
		return contentFigure;
	}
}