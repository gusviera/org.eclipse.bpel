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

import org.eclipse.bpel.editor.assign.contributors.CopyBound;
import org.eclipse.bpel.editor.assign.contributors.EndpointReferenceAssignCategory;
import org.eclipse.bpel.editor.assign.contributors.IAssignCategory;
import org.eclipse.bpel.editor.assign.contributors.OpaqueAssignCategory;
import org.eclipse.bpel.editor.assign.contributors.expression.ExpressionAssignCategory;
import org.eclipse.bpel.editor.assign.contributors.literal.LiteralAssignCategory;
import org.eclipse.bpel.editor.assign.contributors.variablepart.VariablePartAssignCategory;
import org.eclipse.bpel.editor.assign.contributors.variablepart.VariablePropertyAssignCategory;
import org.eclipse.bpel.editor.assign.contributors.variablepart.VariableTreeContentProvider;
import org.eclipse.bpel.editor.assign.dnd.CopySelectionDragAdapter;
import org.eclipse.bpel.editor.assign.dnd.CopySelectionDropAdapter;
import org.eclipse.bpel.editor.assign.mapper.BPELAssignSemanticTreeMappingSupport;
import org.eclipse.bpel.editor.assign.mapper.CategoriesCompoundModelTreeLabelProvider;
import org.eclipse.bpel.editor.assign.mapper.CategoriesCompoundTreeContentProvider;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.nebula.widgets.treemapper.INewMappingListener;
import org.eclipse.nebula.widgets.treemapper.TreeMapper;
import org.eclipse.nebula.widgets.treemapper.TreeMapperUIConfigProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.EditorPart;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class AssignFormPage extends EditorPart {

	private Assign assign;
	private Color defaultColor;
	private Color selectedColor;
	private TreeMapper<Copy, ITreeNode, ITreeNode> mapper;
	private FilteredTree copyList;
	private DataBindingContext dbc;
	private CategoriesCompoundTreeContentProvider leftVariableTreeContentProvider;
	private CategoriesCompoundTreeContentProvider rightVariableTreeContentProvider;
	private Form detailsSection;
	private CopyLabelProvider copyLabelProvider;
	private BPELAssignSemanticTreeMappingSupport semanticSupport;
	
	// Providers to share between VariablePart and VariableProperty
	private VariableTreeContentProvider leftVariableProvider = new VariableTreeContentProvider(true);
	private VariableTreeContentProvider rightVariableProvider = new VariableTreeContentProvider(true);
	
	protected LiteralAssignCategory literalCategory = new LiteralAssignCategory();
	IAssignCategory[] srcCategories = new IAssignCategory[] {
			literalCategory,
			new VariablePartAssignCategory(leftVariableProvider),
			new VariablePropertyAssignCategory(leftVariableProvider),
			new ExpressionAssignCategory(CopyBound.FROM),
			//new PartnerRoleAssignCategory(true),
			new EndpointReferenceAssignCategory(),
			new OpaqueAssignCategory()
	};
	IAssignCategory[] targetCategories = new IAssignCategory[] {
			new VariablePartAssignCategory(rightVariableProvider),
			new VariablePropertyAssignCategory(rightVariableProvider),
			new ExpressionAssignCategory(CopyBound.TO),
			//new PartnerRoleAssignCategory(this, false)
	};
	
	private SashForm detailsSash;
	private Section leftPlaceHolder;
	private Section rightPlaceHolder;

	/**
	 * @param editor
	 * @param id
	 * @param title
	 */
	public AssignFormPage(Assign assign) {
		this.defaultColor = new Color(Display.getDefault(), new RGB(247, 206, 206));
		this.selectedColor = new Color(Display.getDefault(), new RGB(147, 86, 111));
		this.assign = assign;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		createControl(parent);
	}

	/**
	 * @param parent
	 */
	public Control createControl(Composite parent) {
		final FormToolkit tk = new FormToolkit(Display.getDefault());
		ScrolledForm scrolled = tk.createScrolledForm(parent);
		scrolled.getForm().setText(assign.getName());
		tk.decorateFormHeading(scrolled.getForm());
		scrolled.getBody().setLayout(new FillLayout());
		
		SashForm sash = new SashForm(scrolled.getBody(), SWT.VERTICAL);
		Form overviewSection = tk.createForm(sash);
		overviewSection.setText(Messages.mappingsOverview);
		overviewSection.getBody().setLayout(new FillLayout());
		SashForm overviewSash = new SashForm(overviewSection.getBody(), SWT.HORIZONTAL);
		
		copyList = new FilteredTree(overviewSash, SWT.BORDER, new PatternFilter(), true);
		copyList.setLayoutData(new GridData(SWT.DEFAULT, SWT.FILL, false, true));
		copyLabelProvider = new CopyLabelProvider();
		copyList.getViewer().setLabelProvider(copyLabelProvider);
		copyList.getViewer().setContentProvider(new ITreeContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
			@Override
			public void dispose() {
			}
			@Override
			public boolean hasChildren(Object element) {
				return false;
			}
			@Override
			public Object getParent(Object element) {
				return null;
			}
			@Override
			public Object[] getElements(Object inputElement) {
				return ((Assign)inputElement).getCopy().toArray();
			}
			@Override
			public Object[] getChildren(Object parentElement) {
				return null;
			}
		});
		
		/* MAPPER */
		leftVariableTreeContentProvider = new CategoriesCompoundTreeContentProvider(true, false, true, srcCategories);
		rightVariableTreeContentProvider = new CategoriesCompoundTreeContentProvider(true, false, true, targetCategories);
		
		semanticSupport = new BPELAssignSemanticTreeMappingSupport(srcCategories, targetCategories);
		mapper = new TreeMapper<Copy, ITreeNode, ITreeNode>(overviewSash,
				semanticSupport,
				new TreeMapperUIConfigProvider(defaultColor, 1, selectedColor, 3));
		mapper.setContentProviders(leftVariableTreeContentProvider, rightVariableTreeContentProvider);
		mapper.setLabelProviders(new CategoriesCompoundModelTreeLabelProvider(srcCategories), new CategoriesCompoundModelTreeLabelProvider(targetCategories));
		semanticSupport.setTrees(mapper.getLeftTreeViewer(), mapper.getRightTreeViewer());
		
		mapper.getControl().setWeights(new int[] { 1, 1, 1});
		overviewSash.setWeights(new int[] { 1, 3 });

		detailsSection = tk.createForm(sash);
		tk.decorateFormHeading(detailsSection);
		detailsSection.getToolBarManager().add(new DeleteMappingAction(mapper, copyList, this, tk));
		// TODO rely on org.eclipse.ui.menus extension point and IMenuService.populateContributionManager method
		detailsSection.getToolBarManager().update(true);
		detailsSection.getBody().setLayout(new FillLayout());
		detailsSash = new SashForm(detailsSection.getBody(), SWT.HORIZONTAL);
		//detailsSection.setClient(detailsSash.get);
		
		leftPlaceHolder = tk.createSection(detailsSash, Section.TITLE_BAR | Section.EXPANDED);
		leftPlaceHolder.setText(Messages.source);
		rightPlaceHolder = tk.createSection(detailsSash, Section.TITLE_BAR | Section.EXPANDED);
		rightPlaceHolder.setText(Messages.target);
		//StackLayout rightStack = new StackLayout();
		//rightPlaceHolder.setLayout(rightStack);
		
		detailsSash.setWeights(new int[] {1, 1});
		sash.setWeights(new int[] {2, 1});
		
		// Events
		mapper.addNewMappingListener(new INewMappingListener<Copy>() {
			@Override
			public void mappingCreated(Copy mapping) {
				copyList.getViewer().refresh();
			}
		});
		copyList.getViewer().addDragSupport(DND.DROP_MOVE, new Transfer[] { LocalSelectionTransfer.getTransfer() }, new CopySelectionDragAdapter(copyList.getViewer()));
		copyList.getViewer().addDropSupport(DND.DROP_MOVE, new Transfer[] { LocalSelectionTransfer.getTransfer() }, new CopySelectionDropAdapter(copyList.getViewer(), assign));
		
		ISelectionChangedListener selectionChangedListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				refreshDetailsSection((Copy) ((IStructuredSelection)event.getSelection()).getFirstElement(), tk);
			}
		};
		mapper.addSelectionChangedListener(selectionChangedListener);
		copyList.getViewer().addSelectionChangedListener(selectionChangedListener);
		
		resetDataBinding();
		return scrolled;
	}
	
	/**
	 * @param mapping
	 */
	void refreshDetailsSection(Copy mapping, FormToolkit tk) {
		if (mapping != null) {
			leftPlaceHolder.setVisible(true);
			rightPlaceHolder.setVisible(true);
			detailsSection.setText(copyLabelProvider.getText(mapping));
			
			// LEFT
			if (leftPlaceHolder.getClient() != null) {
				leftPlaceHolder.getClient().setVisible(false);
			}
			Control newLeft = null;
			for (IAssignCategory category : srcCategories) {
				if (category.isCategoryForModel(mapping.getFrom())) {
					newLeft = category.getDetailsControl(leftPlaceHolder, tk);
					category.setInput(mapping.getFrom());
				}
			}
			if (newLeft != null) {
				leftPlaceHolder.setClient(newLeft);
				newLeft.setVisible(true);
				leftPlaceHolder.layout(true);
			}
			// RIGHT
			if (rightPlaceHolder.getClient() != null) {
				rightPlaceHolder.getClient().setVisible(false);
			}
			Control newRight = null;
			for (IAssignCategory category : targetCategories) {
				if (category.isCategoryForModel(mapping.getTo())) {
					newRight = category.getDetailsControl(rightPlaceHolder, tk);
					category.setInput(mapping.getTo());
				}
			}
			if (newRight != null) {
				rightPlaceHolder.setClient(newRight);
				newRight.setVisible(true);
				rightPlaceHolder.layout(true);
			}
			detailsSection.layout(true);
		} else {
			detailsSection.setText(Messages.selectMappingToEdit);
			if (leftPlaceHolder.getClient() != null) {
				leftPlaceHolder.getClient().setVisible(false);
			}
			if (rightPlaceHolder.getClient() != null) {
				rightPlaceHolder.getClient().setVisible(false);
			}
		}
	}

	/**
	 * 
	 */
	private void resetDataBinding() {
		if (dbc != null) {
			dbc.dispose();
		}
		mapper.setInput(assign, assign, assign.getCopy());
		copyList.getViewer().setInput(assign);
		dbc = new DataBindingContext();
		dbc.bindValue(ViewersObservables.observeSingleSelection(copyList.getViewer()), ViewersObservables.observeSingleSelection(mapper));
	}

	@Override
	public void dispose() {
		super.dispose();
		defaultColor.dispose();
		selectedColor.dispose();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isDirty()
	 */
	@Override
	public boolean isDirty() {
		// TODO TODO TODO
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
	}
	
	@Override
	public String getTitle() {
		return assign.getName();
	}
	
}
