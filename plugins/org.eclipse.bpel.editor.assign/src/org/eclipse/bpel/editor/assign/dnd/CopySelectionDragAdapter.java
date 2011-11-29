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

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.util.TransferDragSourceListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class CopySelectionDragAdapter extends DragSourceAdapter implements TransferDragSourceListener {

	private Viewer viewer;
	
	public CopySelectionDragAdapter(Viewer viewer) {
		this.viewer = viewer;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.util.TransferDragSourceListener#getTransfer()
	 */
	@Override
	public Transfer getTransfer() {
		return LocalSelectionTransfer.getTransfer();
	}
	
    /* (non-Javadoc)
     * @see org.eclipse.swt.dnd.DragSourceAdapter#dragStart(org.eclipse.swt.dnd.DragSourceEvent)
     */
    public void dragStart(DragSourceEvent event) {
        ISelection selection = viewer.getSelection();
        LocalSelectionTransfer.getTransfer().setSelection(selection);
        LocalSelectionTransfer.getTransfer().setSelectionSetTime(event.time & 0xFFFFFFFFL);
        event.doit = !selection.isEmpty();
    }
   
    /* (non-Javadoc)
     * @see org.eclipse.swt.dnd.DragSourceAdapter#dragSetData(org.eclipse.swt.dnd.DragSourceEvent)
     */
    public void dragSetData(DragSourceEvent event) {
        // For consistency set the data to the selection even though
        // the selection is provided by the LocalSelectionTransfer
        // to the drop target adapter.
        event.data = LocalSelectionTransfer.getTransfer().getSelection();
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.dnd.DragSourceAdapter#dragFinished(org.eclipse.swt.dnd.DragSourceEvent)
     */
    public void dragFinished(DragSourceEvent event) {
        LocalSelectionTransfer.getTransfer().setSelection(null);
        LocalSelectionTransfer.getTransfer().setSelectionSetTime(0);
    }  

}
