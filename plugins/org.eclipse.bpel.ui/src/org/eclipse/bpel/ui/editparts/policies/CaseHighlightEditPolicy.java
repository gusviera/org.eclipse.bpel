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
package org.eclipse.bpel.ui.editparts.policies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.bpel.ui.figures.CaseResizeHandle;
import org.eclipse.bpel.ui.util.BPELDragEditPartsTracker;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.SharedCursors;


public class CaseHighlightEditPolicy extends ContainerHighlightEditPolicy {
	
	public CaseHighlightEditPolicy(boolean resizable, boolean movable) {
		super(resizable, movable);
	}
	
	protected List createSelectionHandles() {
		// HACK: Avoid creating selection handles for objects that have already been deleted
		// (this can occur in some tricky notification scenarios)
		if (((EObject)getHost().getModel()).eResource() == null) { return Collections.EMPTY_LIST; }
		
		List list = new ArrayList();
		addCaseCornerHandles((GraphicalEditPart)getHost(), list);
		return list;
	}
	static void addCaseCornerHandles(GraphicalEditPart part, List handles) {
		handles.add(createCaseHandle(part, PositionConstants.SOUTH_EAST));
		handles.add(createCaseHandle(part, PositionConstants.SOUTH_WEST));
		handles.add(createCaseHandle(part, PositionConstants.NORTH_WEST));
		handles.add(createCaseHandle(part, PositionConstants.NORTH_EAST));
	}
	static Handle createCaseHandle(GraphicalEditPart owner, int direction) {
		CaseResizeHandle handle = new CaseResizeHandle(owner, direction);
		handle.setCursor(SharedCursors.SIZEALL);
		handle.setDragTracker(new BPELDragEditPartsTracker(owner));
		return handle;
	}
}