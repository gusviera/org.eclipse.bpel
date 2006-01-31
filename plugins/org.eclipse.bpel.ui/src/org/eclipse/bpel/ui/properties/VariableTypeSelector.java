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
package org.eclipse.bpel.ui.properties;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.details.viewers.CComboViewer;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.common.ui.flatui.FlatFormLayout;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.details.providers.AddNullFilter;
import org.eclipse.bpel.ui.details.providers.AddSelectedObjectFilter;
import org.eclipse.bpel.ui.details.providers.FaultContentProvider;
import org.eclipse.bpel.ui.details.providers.ModelLabelProvider;
import org.eclipse.bpel.ui.details.providers.ModelViewerSorter;
import org.eclipse.bpel.ui.details.providers.OperationContentProvider;
import org.eclipse.bpel.ui.details.providers.PortTypeContentProvider;
import org.eclipse.bpel.ui.uiextensionmodel.VariableExtension;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.BrowseUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.XSDUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.wst.common.ui.properties.internal.provisional.TabbedPropertySheetWidgetFactory;
import org.eclipse.wst.wsdl.Fault;
import org.eclipse.wst.wsdl.Input;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Output;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;


/**
 * A composite for selecting either a "Data Type" (XSD type, or XSD element containing
 * an anonymous XSD type) an "Interface" type (actually a WSDL message, specified by
 * choosing then Interface+Operation+direction/fault)
 */
public class VariableTypeSelector extends Composite {

	public static final int KIND_UNKNOWN = VariableExtension.KIND_UNKNOWN;
	public static final int KIND_INTERFACE = VariableExtension.KIND_INTERFACE;
	public static final int KIND_DATATYPE = VariableExtension.KIND_DATATYPE;
	
	protected static final int DATATYPE_RADIO_CONTEXT = 1;
	protected static final int INTERFACE_RADIO_CONTEXT = 2;
	
	protected static final int OPERATION_INPUTRADIO_CONTEXT = 3;
	protected static final int OPERATION_OUTPUTRADIO_CONTEXT = 4;
	protected static final int OPERATION_FAULTRADIO_CONTEXT = 5;
	protected static final int OPERATION_COMBO_CONTEXT = 6;
	protected static final int INTERFACE_COMBO_CONTEXT = 7;
	protected static final int INTERFACE_BROWSE_CONTEXT = 8;
	protected static final int DATATYPE_BROWSE_CONTEXT = 9;
	protected static final int FAULT_COMBO_CONTEXT = 10;

	protected int lastChangeContext = -1;
	
	protected boolean inUpdate = false;

	protected int kindHint = KIND_UNKNOWN;
	
	public static final int STANDARD_LABEL_WIDTH_SM = 85;
	public static final int STANDARD_LABEL_WIDTH_AVG = STANDARD_LABEL_WIDTH_SM * 5/4;

	protected EObject variableType;

	protected Button dataTypeRadio, interfaceRadio;
	protected Composite dataTypeComposite, interfaceComposite;

	protected TabbedPropertySheetWidgetFactory wf;
	
	protected CComboViewer interfaceViewer;
	protected Button interfaceBrowseButton;
	protected CComboViewer operationViewer;
	protected Button operationInputRadio, operationOutputRadio, operationFaultRadio;
	protected CComboViewer faultViewer;
	
	protected Label interfaceLabel, operationLabel, faultLabel;
	
	protected AddSelectedObjectFilter interfaceAddSelectedObjectFilter;
	protected AddSelectedObjectFilter operationAddSelectedObjectFilter;
	protected AddSelectedObjectFilter faultAddSelectedObjectFilter;
	
	protected Button dataTypeBrowseButton;
	protected Hyperlink dataTypeNameText;

	protected BPELEditor bpelEditor;
	protected Callback callback;
	protected Shell shell;
	protected boolean allowElements = false;
	protected boolean nullFilterAdded = false;
	
	public VariableTypeSelector(Composite parent, int style, BPELEditor bpelEditor,
		TabbedPropertySheetWidgetFactory wf, Callback callback, boolean allowElements)
	{
		super(parent, style);
		this.bpelEditor = bpelEditor;
		this.shell = bpelEditor.getSite().getShell();
		this.wf = wf;
		this.callback = callback;
		this.allowElements = allowElements;
		
		Composite parentComposite = createComposite(this);
		this.setLayout(new FillLayout());
		
		FlatFormLayout formLayout = new FlatFormLayout();
		formLayout.marginWidth = formLayout.marginHeight = 0;
		parentComposite.setLayout(formLayout);

		createRadioButtonWidgets(parentComposite);
		createDataTypeWidgets(parentComposite);
		createInterfaceWidgets(parentComposite);
	}

	/**
	 * Refresh the given CComboViewer, and also make sure selectedObject is selected in it.
	 */
	protected void refreshCCombo(CComboViewer viewer, Object selectedObject) {
		viewer.refresh();
		if (selectedObject == null) {
			// work-around the null check in StructuredSelection(Object) ctor.
			viewer.setSelectionNoNotify(new StructuredSelection(Collections.singletonList(null)), false);
		} else {
			viewer.setSelectionNoNotify(new StructuredSelection(selectedObject), false);
		}
	}

	protected void selectComposite(int n) {
		interfaceRadio.setSelection(n == Callback.KIND_INTERFACE);
		interfaceComposite.setVisible(n == Callback.KIND_INTERFACE);
		dataTypeRadio.setSelection(n == Callback.KIND_DATATYPE);
		dataTypeComposite.setVisible(n == Callback.KIND_DATATYPE);
	}

	/**
	 * Expects either a WSDL message, an XSD type, or an XSD element.
	 */
	public void setVariableType(EObject variableType) {
		//System.out.println("setVariableType: "+variableType);
		this.variableType = variableType;
		refresh();
	}

	/**
	 * This method is preferred over the EObject method if the caller has a BPELVariable
	 * model object, because this method supports hints stored in the VariableExtension.
	 */
	public void setVariable(Variable variable) {
		kindHint = KIND_UNKNOWN;
		if (variable != null) {  
			if (variable.getMessageType() != null) {
				setVariableType(variable.getMessageType()); return;
			}
			if (variable.getType() != null) {
				setVariableType(variable.getType()); return;
			}
			if (variable.getXSDElement() != null) {
				setVariableType(variable.getXSDElement()); return;
			}
			VariableExtension varExt = (VariableExtension)ModelHelper.getExtension(variable);
			kindHint = varExt.getVariableKind();
		}
		setVariableType(null);
	}

	protected void updateDataTypeWidgets() {
		XSDTypeDefinition type = null;
		if (variableType instanceof XSDTypeDefinition) {
			type = (XSDTypeDefinition)variableType;
		}
		XSDElementDeclaration element = null;
		if (variableType instanceof XSDElementDeclaration) {
			element = (XSDElementDeclaration)variableType;
		}
		String name = (type != null)? XSDUtils.getDisplayNameFromXSDType(type, true) :
			(element != null)? element.getName() : null;
		
		if (name == null) {
			dataTypeNameText.setText(Messages.VariableTypeSelector_None_1); 
			dataTypeNameText.setEnabled(false);
			//dataTypeViewer.setInput(null);
		} else {
			dataTypeNameText.setText(name);
			if (type instanceof XSDComplexTypeDefinition) {
				dataTypeNameText.setEnabled(true);
				//dataTypeViewer.setInput(type);
			} else if (type instanceof XSDSimpleTypeDefinition) {
				dataTypeNameText.setEnabled(false);
				//dataTypeViewer.setInput(null);
			} else if (element != null) {
				dataTypeNameText.setEnabled(true);
				//dataTypeViewer.setInput(element);
			}
		}
	}

	protected void updateInterfaceWidgets()  {
		PortType portType = null;
		Operation operation = null;
		Message message = null;
		if (variableType instanceof Message) {
			message = (Message)variableType;
		}
		if (message != null) operation = BPELUtil.getOperationFromMessage(message);
		if (operation != null) portType = (PortType)operation.eContainer();
		
		interfaceViewer.setInput(bpelEditor.getProcess());
		interfaceAddSelectedObjectFilter.setSelectedObject(portType);
		inUpdate = true;
		try {
			refreshCCombo(interfaceViewer, portType);
	
			if (operation == null) {
				if (!nullFilterAdded) {
					operationViewer.addFilter(AddNullFilter.getInstance());
					nullFilterAdded = true;
				}
			} else {
				if (nullFilterAdded) {
					operationViewer.removeFilter(AddNullFilter.getInstance());
					nullFilterAdded = false;
				}
			}
			operationViewer.setInput(portType);
			operationAddSelectedObjectFilter.setSelectedObject(operation);
			refreshCCombo(operationViewer, operation);
		} finally {
			inUpdate = false;
		}
		updateFaultRadio(message, operation);
	}

	protected void updateCompositeSelection() {
		int kind = kindHint;
		if (variableType instanceof Message) {
			kind = KIND_INTERFACE;
		} else if (variableType instanceof XSDTypeDefinition) {
			kind = KIND_DATATYPE;
		} else if (variableType instanceof XSDElementDeclaration) {
			kind = KIND_DATATYPE;
		}
		if (kind == KIND_UNKNOWN) kind = KIND_DATATYPE;
		selectComposite(kind);
		doChildLayout();
	}
	
	protected void doChildLayout() {
		dataTypeComposite.layout(true);
	}

	public void refresh() {
		updateInterfaceWidgets();
		updateDataTypeWidgets();
		updateCompositeSelection();
	}

	/**
	 * Returns either a WSDL message, an XSD type, an XSD element, or null.
	 */
	public EObject getVariableType() { return variableType; }
	
	protected Composite createFlatFormComposite(Composite parent) {
		Composite result = createComposite(parent);
		FlatFormLayout formLayout = new FlatFormLayout();
		formLayout.marginWidth = formLayout.marginHeight = 0;
		result.setLayout(formLayout);
		return result;
		
	}

	/**
	 * If the widgets are set for faults and the new operation doesn't have any,
	 * this won't work - use computeMessageFromOperation instead.
	 */
	protected Message getStoreMessageFromOperation(Operation operation) {
		// TODO: dialog box if there are no operations?
		Message message = null;
		if (operation != null) {
			if (operationFaultRadio.getSelection()) {
				StructuredSelection sel = (StructuredSelection)faultViewer.getSelection();
				Fault fault = (Fault)sel.getFirstElement();
				if (fault != null) message = fault.getEMessage();
				return message;
			} else if (operationOutputRadio.getSelection()) {
				Output output = operation.getEOutput();
				if (output != null) message = output.getEMessage();
				return message;
			} else {
				Input input = operation.getEInput();
				if (input != null) message = input.getEMessage();
				return message;
			}
		}
		return message;
	}
	
	class RadioListener implements SelectionListener {
		int index;
		public RadioListener(int index) { this.index = index; }
		public void widgetSelected(SelectionEvent e) {
			if (!((Button)e.widget).getSelection()) return;
			lastChangeContext = index;
			callback.selectRadioButton(index);
		}
		public void widgetDefaultSelected(SelectionEvent e) { }
	}
	
	protected void createRadioButtonWidgets(Composite composite) {
		FlatFormData data;

		dataTypeRadio = createButton(composite, Messages.VariableTypeSelector_Data_Type_1, SWT.RADIO); 
		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, 0);
		data.left = new FlatFormAttachment(0, 0);
		dataTypeRadio.setLayoutData(data);

		interfaceRadio = createButton(composite, Messages.VariableTypeSelector_Interface_1, SWT.RADIO); 
		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, 0);
		data.left = new FlatFormAttachment(dataTypeRadio, IDetailsAreaConstants.HSPACE);
		interfaceRadio.setLayoutData(data);

		interfaceRadio.addSelectionListener(new RadioListener(Callback.KIND_INTERFACE));
		dataTypeRadio.addSelectionListener(new RadioListener(Callback.KIND_DATATYPE));
	}
	
	protected void createInterfaceWidgets(Composite parent) {
		FlatFormData data;

		Composite composite = interfaceComposite = createFlatFormComposite(parent);
		
		data = new FlatFormData();
		data.top = new FlatFormAttachment(interfaceRadio, IDetailsAreaConstants.VMARGIN);
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.bottom = new FlatFormAttachment(100, 0);
		interfaceComposite.setLayoutData(data);
		
		interfaceBrowseButton = createButton(composite, Messages.VariableTypeSelector_Browse_1, SWT.PUSH); 

		interfaceLabel = createLabel(composite, Messages.VariableTypeSelector_Interface_2); 
		CCombo interfaceCombo = createCCombo(composite);
		interfaceViewer = new CComboViewer(interfaceCombo);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(interfaceCombo, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(interfaceCombo, 0, SWT.CENTER);
		interfaceLabel.setLayoutData(data);

		interfaceAddSelectedObjectFilter = new AddSelectedObjectFilter();
		interfaceViewer.addFilter(AddNullFilter.getInstance());
		interfaceViewer.addFilter(interfaceAddSelectedObjectFilter);
		interfaceViewer.setLabelProvider(new ModelLabelProvider());
		interfaceViewer.setContentProvider(new PortTypeContentProvider());
		interfaceViewer.setSorter(ModelViewerSorter.getInstance());
		interfaceViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection)event.getSelection();
				PortType portType = (PortType)selection.getFirstElement();
				Operation operation = null;
				if (portType != null && !portType.getEOperations().isEmpty()) {
					operation = (Operation)portType.getEOperations().get(0);
				}
				lastChangeContext = INTERFACE_COMBO_CONTEXT;
				
				Message message = computeMessageFromOperation(operation);
				callback.selectMessageType(message);
				updateFaultRadio(message, operation);				
			}
		});
		
		interfaceBrowseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
            	PortType portType = BrowseUtil.browseForPortType(bpelEditor.getResourceSet(), shell);
            	if (portType != null) {
            		Operation operation = null;
					if (!portType.getEOperations().isEmpty()) {
						operation = (Operation)portType.getEOperations().get(0);
					}
					lastChangeContext = INTERFACE_BROWSE_CONTEXT;
					
					Message message = computeMessageFromOperation(operation);
					callback.selectMessageType(message);
					updateFaultRadio(message, operation);				
			    }
			}
		});
		
		operationLabel = createLabel(composite, Messages.VariableTypeSelector_Operation_1); 
		CCombo operationCombo = createCCombo(composite);
		operationViewer = new CComboViewer(operationCombo);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(operationCombo, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(operationCombo, 0, SWT.CENTER);
		operationLabel.setLayoutData(data);

		operationAddSelectedObjectFilter = new AddSelectedObjectFilter();
		//operationViewer.addFilter(AddNullFilter.getInstance());			
		operationViewer.addFilter(operationAddSelectedObjectFilter);			
		operationViewer.setLabelProvider(new ModelLabelProvider());
		operationViewer.setContentProvider(new OperationContentProvider());
		operationViewer.setSorter(ModelViewerSorter.getInstance());
		operationViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection)event.getSelection();
				Operation operation = (Operation)selection.getFirstElement();
				lastChangeContext = OPERATION_COMBO_CONTEXT;
				Message message = computeMessageFromOperation(operation);
				callback.selectMessageType(message);
				updateFaultRadio(message, operation);				
			}
		});

		Label directionLabel = createLabel(composite, Messages.VariableTypeSelector_Direction_1); 
		
		// TODO: should these radio buttons really be a check box for the Reply case??
		// - for now no, since I'm afraid it might be kind of confusing.
		
		operationInputRadio = createButton(composite, Messages.VariableTypeSelector_Input_1, SWT.RADIO); 
		data = new FlatFormData();
		data.top = new FlatFormAttachment(operationViewer.getCCombo(), IDetailsAreaConstants.VSPACE);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(directionLabel, STANDARD_LABEL_WIDTH_SM));
		operationInputRadio.setLayoutData(data);
		operationInputRadio.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (!operationInputRadio.getSelection()) return;
				StructuredSelection selection = (StructuredSelection)operationViewer.getSelection();
				Operation operation = (Operation)selection.getFirstElement();
				lastChangeContext = OPERATION_INPUTRADIO_CONTEXT;
				callback.selectMessageType(getStoreMessageFromOperation(operation));
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		operationOutputRadio = createButton(composite, Messages.VariableTypeSelector_Output_1, SWT.RADIO); 
		data = new FlatFormData();
		data.top = new FlatFormAttachment(operationInputRadio, 0, SWT.TOP);
		data.left = new FlatFormAttachment(operationInputRadio, IDetailsAreaConstants.HSPACE);
		operationOutputRadio.setLayoutData(data);
		operationOutputRadio.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (!operationOutputRadio.getSelection()) return;
				StructuredSelection selection = (StructuredSelection)operationViewer.getSelection();
				Operation operation = (Operation)selection.getFirstElement();
				lastChangeContext = OPERATION_OUTPUTRADIO_CONTEXT;
				callback.selectMessageType(getStoreMessageFromOperation(operation));
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		operationFaultRadio = createButton(composite, Messages.VariableTypeSelector_Fault_1, SWT.RADIO); 
		data = new FlatFormData();
		data.top = new FlatFormAttachment(operationOutputRadio, 0, SWT.TOP);
		data.left = new FlatFormAttachment(operationOutputRadio, IDetailsAreaConstants.HSPACE);
		operationFaultRadio.setLayoutData(data);
		operationFaultRadio.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (!operationFaultRadio.getSelection()) return;
				StructuredSelection selection = (StructuredSelection)operationViewer.getSelection();
				Operation operation = (Operation)selection.getFirstElement();				
				lastChangeContext = OPERATION_FAULTRADIO_CONTEXT;
				// Get the fault from the operation.
				Fault fault = (Fault)operation.getEFaults().get(0);
				Message message = fault.getEMessage();
				callback.selectMessageType(message);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		faultLabel = createLabel(composite, Messages.VariableTypeSelector_Fault_2); 
			
		CCombo faultCombo = createCCombo(composite);
		faultViewer = new CComboViewer(faultCombo);

//		data = new FlatFormData();
//		data.top = new FlatFormAttachment(operationFaultRadio, IDetailsAreaConstants.VSPACE);
//		data.left = new FlatFormAttachment(faultLabel, IDetailsAreaConstants.HSPACE);
//		faultCombo.setLayoutData(data);

		data = new FlatFormData();
		data.top = new FlatFormAttachment(faultCombo, 0, SWT.CENTER);
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(faultCombo, -IDetailsAreaConstants.HSPACE);
		faultLabel.setLayoutData(data);

		faultAddSelectedObjectFilter = new AddSelectedObjectFilter();
		faultViewer.addFilter(faultAddSelectedObjectFilter);			
		faultViewer.setLabelProvider(new ModelLabelProvider());
		faultViewer.setContentProvider(new FaultContentProvider());
		faultViewer.setSorter(ModelViewerSorter.getInstance());
		faultViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection)operationViewer.getSelection();
				Operation operation = (Operation)selection.getFirstElement();
				lastChangeContext = FAULT_COMBO_CONTEXT;
				callback.selectMessageType(getStoreMessageFromOperation(operation));
			}
		});
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(operationCombo, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(operationInputRadio, 0, SWT.CENTER);
		directionLabel.setLayoutData(data);
		
		internalSetLayoutData();	
	}
	
	protected void createDataTypeWidgets(Composite parent) {
		FlatFormData data;

		Composite composite = dataTypeComposite = createFlatFormComposite(parent);
		data = new FlatFormData();
		data.top = new FlatFormAttachment(interfaceRadio, IDetailsAreaConstants.VMARGIN);
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.bottom = new FlatFormAttachment(100, 0);
		dataTypeComposite.setLayoutData(data);
		
		dataTypeBrowseButton = createButton(composite, Messages.VariableTypeSelector_Browse_2, SWT.PUSH); 

		Label dataTypeLabel = createLabel(composite, Messages.VariableTypeSelector_Data_Type_2); 
		
		dataTypeNameText = createHyperlink(composite, "", SWT.NONE); //$NON-NLS-1$
		dataTypeNameText.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				BPELUtil.openEditor(getVariableType(), bpelEditor);
			}
		});
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(dataTypeLabel, STANDARD_LABEL_WIDTH_SM));
		data.top = new FlatFormAttachment(0, 3);
		dataTypeNameText.setLayoutData(data);		

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(dataTypeNameText, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(dataTypeNameText, 3, SWT.TOP);
		dataTypeLabel.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(50, -IDetailsAreaConstants.CENTER_SPACE + IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(dataTypeNameText, -2, SWT.TOP);
		data.bottom = new FlatFormAttachment(dataTypeLabel, +2, SWT.BOTTOM);
		dataTypeBrowseButton.setLayoutData(data);
		dataTypeBrowseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				Object xsdType = BrowseUtil.browseForXSDTypeOrElement(bpelEditor.getResourceSet(), getShell());
				if (xsdType != null) {
    				lastChangeContext = DATATYPE_BROWSE_CONTEXT;
					if (xsdType instanceof XSDTypeDefinition) {
						// TODO:WDG: if type is anonymous, use enclosing element 
						callback.selectXSDType((XSDTypeDefinition)xsdType);
					} else if (xsdType instanceof XSDElementDeclaration) {
						callback.selectXSDElement((XSDElementDeclaration)xsdType);
					}
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) { }
		});
	}
	
	public interface Callback {
		public static final int KIND_DATATYPE = VariableExtension.KIND_DATATYPE;
		public static final int KIND_INTERFACE = VariableExtension.KIND_INTERFACE;
		public void selectRadioButton(int index);
		
		public void selectXSDType(XSDTypeDefinition xsdType);
		public void selectXSDElement(XSDElementDeclaration xsdElement);
		public void selectMessageType(Message message);
	}
	
	public Object getUserContext() {
		return new Integer(lastChangeContext);
	}
	public void restoreUserContext(Object userContext) {
		switch (((Integer)userContext).intValue()) {
		case DATATYPE_RADIO_CONTEXT: dataTypeRadio.setFocus(); return;
		case INTERFACE_RADIO_CONTEXT: interfaceRadio.setFocus(); return;
		case OPERATION_INPUTRADIO_CONTEXT: operationInputRadio.setFocus(); return;
		case OPERATION_OUTPUTRADIO_CONTEXT: operationOutputRadio.setFocus(); return;
		case OPERATION_FAULTRADIO_CONTEXT: operationFaultRadio.setFocus(); return;
		case OPERATION_COMBO_CONTEXT: operationViewer.getCCombo().setFocus(); return;
		case INTERFACE_COMBO_CONTEXT: interfaceViewer.getCCombo().setFocus(); return;
		case INTERFACE_BROWSE_CONTEXT: interfaceBrowseButton.setFocus(); return;
		case DATATYPE_BROWSE_CONTEXT: dataTypeBrowseButton.setFocus(); return;
		case FAULT_COMBO_CONTEXT: faultViewer.getCCombo().setFocus(); return;
		}
		throw new IllegalStateException();
	}
	
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);	
		dataTypeRadio.setEnabled(enabled);
		interfaceRadio.setEnabled(enabled);
		interfaceViewer.getCCombo().setEnabled(enabled);
		interfaceBrowseButton.setEnabled(enabled);
		operationViewer.getCCombo().setEnabled(enabled);
		operationInputRadio.setEnabled(enabled);
		operationOutputRadio.setEnabled(enabled);
		operationFaultRadio.setEnabled(enabled);
		dataTypeBrowseButton.setEnabled(enabled);
		dataTypeNameText.setEnabled(enabled);
		faultViewer.getCCombo().setEnabled(enabled);
	}
	
	protected Button createButton(Composite parent, String text, int style) {
		return wf.createButton(parent, text, style);
	}	
	protected Composite createComposite(Composite parent) {
		return wf.createComposite(parent);
	}
	protected Label createLabel(Composite parent, String text) {
		return wf.createLabel(parent, text);
	}	
	protected Hyperlink createHyperlink(Composite parent, String text, int style) {
		return wf.createHyperlink(parent, text, style);
	}	
	protected CCombo createCCombo(Composite parent) {
		return wf.createCCombo(parent);
	}
	
	protected void internalSetLayoutData() {
		FlatFormData data = new FlatFormData();
		data.left = new FlatFormAttachment(50, -IDetailsAreaConstants.CENTER_SPACE + IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(interfaceViewer.getControl(), -1, SWT.TOP);
		data.bottom = new FlatFormAttachment(interfaceViewer.getControl(), +1, SWT.BOTTOM);
		interfaceBrowseButton.setLayoutData(data);	
		
		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, 2);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(interfaceLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(interfaceBrowseButton, -IDetailsAreaConstants.HSPACE);
		interfaceViewer.getControl().setLayoutData(data);
		
		data = new FlatFormData();
		data.top = new FlatFormAttachment(interfaceViewer.getControl(), IDetailsAreaConstants.VSPACE);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(operationLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(interfaceBrowseButton, -IDetailsAreaConstants.HSPACE);
		operationViewer.getControl().setLayoutData(data);
		
		data = new FlatFormData();
		data.top = new FlatFormAttachment(operationFaultRadio, IDetailsAreaConstants.VSPACE);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(faultLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(interfaceBrowseButton, -IDetailsAreaConstants.HSPACE);
		faultViewer.getCCombo().setLayoutData(data);
	}
	
	protected void updateFaultRadio(Message message, Operation operation) {
		boolean isInput = true;
		boolean isOutput = false;
		boolean isFault = false;
		Fault fault = null;
		if (operation != null && message != null) {
			Iterator it = operation.getEFaults().iterator();
			while (it.hasNext()) {
				Fault nextFault = (Fault)it.next();
				Message faultMessage = nextFault.getEMessage();
				if (faultMessage != null && faultMessage.getQName() != null) {
					if (faultMessage.getQName().equals(message.getQName())) {
						isFault = true;
						isInput = false;
						isOutput = false;
						fault = nextFault;
					}
				}
			}
			Output output = operation.getEOutput();
			if (output != null) {
				Message outputMessage = output.getEMessage();
				if (outputMessage != null && outputMessage.getQName() != null) {
					if (outputMessage.getQName().equals(message.getQName())) {
						isOutput = true;
						isInput = false;
						isFault = false;
					}
				}
			}
		}
		operationInputRadio.setSelection(isInput);
		operationOutputRadio.setSelection(isOutput);
		operationFaultRadio.setSelection(isFault);
		operationFaultRadio.setVisible(operation != null && !operation.getEFaults().isEmpty());
		faultLabel.setVisible(isFault);
		faultViewer.getCCombo().setVisible(isFault);
		if (isFault) {
			if (operation != null) {
				faultViewer.setInput(operation);
				refreshCCombo(faultViewer, fault);
			}
		}
		this.layout(true);
		faultViewer.getCCombo().getParent().redraw();
		faultViewer.getCCombo().redraw();
		interfaceComposite.layout(true);
	}
	
	/**
	 * This one handles faults, also whacks the radio buttons when appropriate.
	 * Use this one after changing the operation, and the other one when 
	 * leaving the operation alone.
	 */
	protected Message computeMessageFromOperation(Operation operation) {
		Message message = null;
		if (operationFaultRadio.getSelection()) {
			if (operation.getEFaults().isEmpty()) {
				operationFaultRadio.setSelection(false);
				operationOutputRadio.setSelection(true);
				message = getStoreMessageFromOperation(operation);
			} else {
				Fault fault = (Fault)operation.getEFaults().get(0);
				message = fault.getEMessage();
			}
		} else {
			message = getStoreMessageFromOperation(operation);
		}	
		return message;
	}
}