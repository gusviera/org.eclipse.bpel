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
package org.eclipse.bpel.editor.assign;

import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.ui.editparts.FlowEditPart;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class OpenAssignEditorWizardHandler extends AbstractHandler {

	/**
	 * @author Mickael Istria (PetalsLink)
	 *
	 */
	private static final class AssignEditorDialog extends Dialog {
		/**
		 * 
		 */
		private final Assign assign;

		/**
		 * @param parentShell
		 * @param assign
		 */
		private AssignEditorDialog(Shell parentShell, Assign assign) {
			super(parentShell);
			setShellStyle(SWT.RESIZE);
			this.assign = assign;
		}

		@Override
		public Control createDialogArea(Composite parent) {
			Control res = new AssignFormPage(assign).createControl(parent);
			res.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			return res;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Assign assign = getSelectedAssign();
		Dialog dialog = new AssignEditorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), assign);
		dialog.open();
		return null;
	}
	
	
	private Assign getSelectedAssign() {
		ISelection sel = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		if (sel != null && sel instanceof IStructuredSelection) {
			Object item = ((IStructuredSelection)sel).getFirstElement();
			if (item instanceof FlowEditPart 
				&& ((FlowEditPart)item).getActivity() instanceof Assign) {
				return (Assign) ((FlowEditPart)item).getActivity();
			} else if (item instanceof Assign) {
				return (Assign)item;
			}
		}
		return null;
	}
	
	@Override
	public boolean isEnabled() {
		return getSelectedAssign() != null;
	}

}
