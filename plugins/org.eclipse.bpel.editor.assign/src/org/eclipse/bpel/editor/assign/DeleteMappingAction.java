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
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.pics.Pics;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.nebula.widgets.treemapper.TreeMapper;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class DeleteMappingAction extends Action implements IAction {

	private TreeMapper<Copy, ITreeNode, ITreeNode> mapper;
	private Shell shell;
	private FilteredTree list;
	private FormToolkit tk;
	private AssignFormPage editor;

	public DeleteMappingAction(TreeMapper<Copy, ITreeNode, ITreeNode> mapper, FilteredTree list, AssignFormPage editor, FormToolkit tk) {
		super(Messages.deleteMapping, new ImageDescriptor() {
			@Override
			public ImageData getImageData() {
				return Pics.getDelete().getImageData();
			}
		});
		this.mapper = mapper;
		this.list = list;
		this.editor = editor;
		this.tk = tk;
		//this.shell = editor.getSite().getShell();
	}
	
	@Override
	public void run() {
		if (MessageDialog.openConfirm(shell, Messages.confirmDeletationOfMapping_title, Messages.confirmDeletationOfMapping_desc)) {
			for (Object item : ((IStructuredSelection)mapper.getSelection()).toList()) {
				if (item instanceof Copy) {
					Copy copy = (Copy)item;
					((Assign)copy.eContainer()).getCopy().remove(copy);
				}
			}
		}
		editor.refreshDetailsSection(null, tk);
		mapper.refresh();
		list.getViewer().refresh();
	}
}
