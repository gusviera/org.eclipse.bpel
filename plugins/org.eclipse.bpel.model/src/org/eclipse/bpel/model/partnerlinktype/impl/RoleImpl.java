/**
 * <copyright>
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 * </copyright>
 *
 * $Id: RoleImpl.java,v 1.3 2005/12/12 15:55:41 james Exp $
 */
package org.eclipse.bpel.model.partnerlinktype.impl;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypePackage;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.bpel.model.partnerlinktype.util.PartnerlinktypeConstants;
import org.eclipse.bpel.model.util.BPELServicesUtility;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.internal.impl.ExtensibilityElementImpl;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Role</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.partnerlinktype.impl.RoleImpl#getID <em>ID</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.partnerlinktype.impl.RoleImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.partnerlinktype.impl.RoleImpl#getPortType <em>Port Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RoleImpl extends ExtensibilityElementImpl implements Role {
	/**
	 * The default value of the '{@link #getID() <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPortType() <em>Port Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPortType()
	 * @generated
	 * @ordered
	 */
	protected static final Object PORT_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPortType() <em>Port Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPortType()
	 * @generated
	 * @ordered
	 */
	protected Object portType = PORT_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RoleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PartnerlinktypePackage.eINSTANCE.getRole();
	}

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @customized
     */
	public String getID()
    {
	    String id = ID_EDEFAULT;
	    PartnerLinkType plt = (PartnerLinkType) eContainer();
	    if (plt != null)
	    {
	        id = BPELServicesUtility.getIdForNestedNamedObject(plt.getID(), this, getName());
	    }
	    return id;
    }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PartnerlinktypePackage.ROLE__NAME, oldName, name));
	}

	/**
	 * @customized
	 */
	public Object getPortType() {
		if (portType instanceof PortType && ((PortType)portType).eIsProxy()) {
			PortType oldPortType = (PortType)portType;
			portType = (PortType)eResolveProxy((InternalEObject)portType);
			if (portType != oldPortType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PartnerlinktypePackage.ROLE__PORT_TYPE, oldPortType, portType));
			}
		}
        return portType;
    }
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPortType(Object newPortType) {
		Object oldPortType = portType;
		portType = newPortType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PartnerlinktypePackage.ROLE__PORT_TYPE, oldPortType, portType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case PartnerlinktypePackage.ROLE__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case PartnerlinktypePackage.ROLE__ELEMENT:
				return getElement();
			case PartnerlinktypePackage.ROLE__REQUIRED:
				return isRequired() ? Boolean.TRUE : Boolean.FALSE;
			case PartnerlinktypePackage.ROLE__ELEMENT_TYPE:
				return getElementType();
			case PartnerlinktypePackage.ROLE__ID:
				return getID();
			case PartnerlinktypePackage.ROLE__NAME:
				return getName();
			case PartnerlinktypePackage.ROLE__PORT_TYPE:
				return getPortType();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case PartnerlinktypePackage.ROLE__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case PartnerlinktypePackage.ROLE__ELEMENT:
				setElement((Element)newValue);
				return;
			case PartnerlinktypePackage.ROLE__REQUIRED:
				setRequired(((Boolean)newValue).booleanValue());
				return;
			case PartnerlinktypePackage.ROLE__ELEMENT_TYPE:
				setElementType((QName)newValue);
				return;
			case PartnerlinktypePackage.ROLE__NAME:
				setName((String)newValue);
				return;
			case PartnerlinktypePackage.ROLE__PORT_TYPE:
				setPortType((Object)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case PartnerlinktypePackage.ROLE__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case PartnerlinktypePackage.ROLE__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case PartnerlinktypePackage.ROLE__REQUIRED:
				setRequired(REQUIRED_EDEFAULT);
				return;
			case PartnerlinktypePackage.ROLE__ELEMENT_TYPE:
				setElementType(ELEMENT_TYPE_EDEFAULT);
				return;
			case PartnerlinktypePackage.ROLE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PartnerlinktypePackage.ROLE__PORT_TYPE:
				setPortType(PORT_TYPE_EDEFAULT);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case PartnerlinktypePackage.ROLE__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case PartnerlinktypePackage.ROLE__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case PartnerlinktypePackage.ROLE__REQUIRED:
				return required != REQUIRED_EDEFAULT;
			case PartnerlinktypePackage.ROLE__ELEMENT_TYPE:
				return ELEMENT_TYPE_EDEFAULT == null ? elementType != null : !ELEMENT_TYPE_EDEFAULT.equals(elementType);
			case PartnerlinktypePackage.ROLE__ID:
				return ID_EDEFAULT == null ? getID() != null : !ID_EDEFAULT.equals(getID());
			case PartnerlinktypePackage.ROLE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PartnerlinktypePackage.ROLE__PORT_TYPE:
				return PORT_TYPE_EDEFAULT == null ? portType != null : !PORT_TYPE_EDEFAULT.equals(portType);
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", portType: ");
		result.append(portType);
		result.append(')');
		return result.toString();
	}

	/**
	 * Override the XML element token.
	 */
	public QName getElementType()
	{
		if (elementType == null)
			elementType = new QName(PartnerlinktypeConstants.NAMESPACE, PartnerlinktypeConstants.ROLE_ELEMENT_TAG);
		return elementType;
    }
	
	//
	// Reconcile methods: DOM -> Model
	//

	public void reconcileAttributes(Element changedElement)
	{
	    super.reconcileAttributes(changedElement);

	    setName
    	(PartnerlinktypeConstants.getAttribute(changedElement, PartnerlinktypeConstants.NAME_ATTRIBUTE));

	    reconcileReferences(false);
	}

	public void reconcileReferences(boolean deep) {
		// Reconcile the PortType reference.
		if (element != null && element.hasAttribute(PartnerlinktypeConstants.PORT_TYPE_ATTRIBUTE))
	    {
	    	Definition definition = getEnclosingDefinition();
	    	if (definition != null)
	    	{
		    	QName portTypeQName = createQName(definition, element.getAttribute(PartnerlinktypeConstants.PORT_TYPE_ATTRIBUTE));
		    	PortType newPortType = (portTypeQName != null) ? (PortType) definition.getPortType(portTypeQName) : null;
		    	if (newPortType != null && newPortType != getPortType())
		    		setPortType(newPortType);
	    	}
	    }
		super.reconcileReferences(deep);
	}

	//
	// For reconciliation: Model -> DOM
	//

	protected void changeAttribute(EAttribute eAttribute)
	{
	    //System.out.println("RoleImpl.changeAttribute("+eAttribute+")");
	    if (isReconciling)
	        return;

	    super.changeAttribute(eAttribute);
	    Element theElement = getElement();
	    if (theElement != null)
	    {
	        if (eAttribute == null || eAttribute == PartnerlinktypePackage.eINSTANCE.getRole_Name())
	            niceSetAttribute(theElement,PartnerlinktypeConstants.NAME_ATTRIBUTE,getName());
	        if (eAttribute == null || eAttribute == PartnerlinktypePackage.eINSTANCE.getRole_PortType())
	        {
	            PortType pt = (PortType)getPortType();
	            QName qname = (pt == null) ? null : pt.getQName();
	            if (qname != null)
	            	niceSetAttributeURIValue(theElement, PartnerlinktypeConstants.PORT_TYPE_ATTRIBUTE, qname.getNamespaceURI() + "#" + qname.getLocalPart());
	        }
	        
	    }
	}
} //RoleImpl