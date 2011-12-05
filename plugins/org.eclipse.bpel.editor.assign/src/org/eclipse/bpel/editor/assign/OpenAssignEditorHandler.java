package org.eclipse.bpel.editor.assign;

import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.ui.BPELMultipageEditorPart;
import org.eclipse.bpel.ui.editparts.FlowEditPart;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class OpenAssignEditorHandler extends AbstractHandler implements
		IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Assign assign = getSelectedAssign();
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		BPELMultipageEditorPart editor = (BPELMultipageEditorPart)workbenchPage.getActiveEditor();
		if (editor.containsPageFor(assign)) {
			editor.showPageFor(assign);
		} else {
			try {
				IEditorReference editorRef = null;
				for (IEditorReference ref : workbenchPage.getEditorReferences()) {
					if (ref != null && ref.getEditor(false) != null && ref.getEditor(false).equals(editor)) {
						editorRef = ref;
					}
				}
				//Control page = new AssignFormPage(assign).createControl(editor.getContainer());
				editor.registerPage(assign, new AssignFormPage(assign));
				editor.showPageFor(assign);
			} catch (Exception ex) {
				return new Status(IStatus.ERROR, BPELAssignPlugin.PLUGIN_ID, ex.getMessage(), ex);
			}
		}
		return IStatus.OK;
	}

	@Override
	public boolean isEnabled() {
		return getSelectedAssign() != null;
	}

	private Assign getSelectedAssign() {
		ISelection sel = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getSelectionService().getSelection();
		if (sel != null && sel instanceof IStructuredSelection) {
			Object item = ((IStructuredSelection) sel).getFirstElement();
			if (item instanceof FlowEditPart
					&& ((FlowEditPart) item).getActivity() instanceof Assign) {
				return (Assign) ((FlowEditPart) item).getActivity();
			} else if (item instanceof Assign) {
				return (Assign) item;
			}
		}
		return null;
	}

}
