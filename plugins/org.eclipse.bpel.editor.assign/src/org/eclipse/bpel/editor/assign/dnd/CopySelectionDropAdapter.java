/*******************************************************************************
* Copyright (c) 2011 EBM WebSourcing (PetalsLink)
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* Mickael Istria, EBM WebSourcing (PetalsLink) - initial API and implementation
*******************************************************************************/
package org.eclipse.bpel.editor.assign.dnd;

import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.Copy;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TransferData;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class CopySelectionDropAdapter extends ViewerDropAdapter implements DropTargetListener {

	private Assign assign;

	/**
	 * @param viewer
	 */
	public CopySelectionDropAdapter(Viewer viewer, Assign assign) {
		super(viewer);
		this.assign = assign;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerDropAdapter#performDrop(java.lang.Object)
	 */
	@Override
	public boolean performDrop(Object data) {
		ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
		IStructuredSelection sel = (IStructuredSelection)selection;
		for (Object item : sel.toList()) {
			Copy copy = (Copy)item;
			assign.getCopy().remove(item);
			assign.getCopy().add(assign.getCopy().indexOf(getCurrentTarget()) + toDelta(getCurrentLocation()), copy);
		}
		getViewer().refresh();
		return true;
	}

	/**
	 * @param currentLocation
	 * @return
	 */
	private int toDelta(int currentLocation) {
		if (currentLocation == LOCATION_AFTER) {
			return 1;
		} else {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerDropAdapter#validateDrop(java.lang.Object, int, org.eclipse.swt.dnd.TransferData)
	 */
	@Override
	public boolean validateDrop(Object target, int operation, TransferData transferType) {
		if (getCurrentLocation() == LOCATION_NONE || getCurrentLocation() == LOCATION_ON) {
			return false;
		}
		ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
		if (!(selection instanceof StructuredSelection)) {
			return false;
		}
		IStructuredSelection sel = (IStructuredSelection)selection;
		for (Object item : sel.toList()) {
			if (!assign.getCopy().contains(item)) {
				return false;
			}
		}
		return true;
	}

}
