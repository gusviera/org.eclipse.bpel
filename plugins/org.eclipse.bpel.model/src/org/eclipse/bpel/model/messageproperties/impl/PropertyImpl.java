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
 * $Id: PropertyImpl.java,v 1.1 2005/11/29 18:50:27 james Exp $
 */
package org.eclipse.bpel.model.messageproperties.impl;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.messageproperties.MessagepropertiesPackage;
import org.eclipse.bpel.model.messageproperties.Property;
import org.eclipse.bpel.model.messageproperties.util.MessagepropertiesConstants;
import org.eclipse.bpel.model.util.BPELServicesUtility;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.internal.impl.DefinitionImpl;
import org.eclipse.wst.wsdl.internal.impl.ExtensibilityElementImpl;
import org.eclipse.xsd.XSDTypeDefinition;

import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.messageproperties.impl.PropertyImpl#getQName <em>QName</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.messageproperties.impl.PropertyImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.messageproperties.impl.PropertyImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.messageproperties.impl.PropertyImpl#getID <em>ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyImpl extends ExtensibilityElementImpl implements Property {
	/**
	 * The default value of the '{@link #getQName() <em>QName</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQName()
	 * @generated
	 * @ordered
	 */
	protected static final QName QNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQName() <em>QName</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQName()
	 * @generated
	 * @ordered
	 */
	protected QName qName = QNAME_EDEFAULT;

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
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final Object TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected Object type = TYPE_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return MessagepropertiesPackage.eINSTANCE.getProperty();
	}

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated NOT
     */
	public QName getQName()
    {
        return new QName(getEnclosingDefinition().getTargetNamespace(), name);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated NOT
     */
	public void setQName(QName newQName)
    {
		// Do nothing.
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
			eNotify(new ENotificationImpl(this, Notification.SET, MessagepropertiesPackage.PROPERTY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(Object newType) {
		Object oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MessagepropertiesPackage.PROPERTY__TYPE, oldType, type));
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public String getID()
    {
        // TODO: NEW_WSDL review this
        String namespace = getEnclosingDefinition() != null ? getEnclosingDefinition().getTargetNamespace() : "";
        String id = BPELServicesUtility.getId(new QName(namespace, getName()), this);
        return id;
    }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case MessagepropertiesPackage.PROPERTY__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case MessagepropertiesPackage.PROPERTY__ELEMENT:
				return getElement();
			case MessagepropertiesPackage.PROPERTY__REQUIRED:
				return isRequired() ? Boolean.TRUE : Boolean.FALSE;
			case MessagepropertiesPackage.PROPERTY__ELEMENT_TYPE:
				return getElementType();
			case MessagepropertiesPackage.PROPERTY__QNAME:
				return getQName();
			case MessagepropertiesPackage.PROPERTY__NAME:
				return getName();
			case MessagepropertiesPackage.PROPERTY__TYPE:
				return getType();
			case MessagepropertiesPackage.PROPERTY__ID:
				return getID();
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
			case MessagepropertiesPackage.PROPERTY__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case MessagepropertiesPackage.PROPERTY__ELEMENT:
				setElement((Element)newValue);
				return;
			case MessagepropertiesPackage.PROPERTY__REQUIRED:
				setRequired(((Boolean)newValue).booleanValue());
				return;
			case MessagepropertiesPackage.PROPERTY__ELEMENT_TYPE:
				setElementType((QName)newValue);
				return;
			case MessagepropertiesPackage.PROPERTY__QNAME:
				setQName((QName)newValue);
				return;
			case MessagepropertiesPackage.PROPERTY__NAME:
				setName((String)newValue);
				return;
			case MessagepropertiesPackage.PROPERTY__TYPE:
				setType((Object)newValue);
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
			case MessagepropertiesPackage.PROPERTY__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case MessagepropertiesPackage.PROPERTY__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case MessagepropertiesPackage.PROPERTY__REQUIRED:
				setRequired(REQUIRED_EDEFAULT);
				return;
			case MessagepropertiesPackage.PROPERTY__ELEMENT_TYPE:
				setElementType(ELEMENT_TYPE_EDEFAULT);
				return;
			case MessagepropertiesPackage.PROPERTY__QNAME:
				setQName(QNAME_EDEFAULT);
				return;
			case MessagepropertiesPackage.PROPERTY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MessagepropertiesPackage.PROPERTY__TYPE:
				setType(TYPE_EDEFAULT);
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
			case MessagepropertiesPackage.PROPERTY__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case MessagepropertiesPackage.PROPERTY__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case MessagepropertiesPackage.PROPERTY__REQUIRED:
				return required != REQUIRED_EDEFAULT;
			case MessagepropertiesPackage.PROPERTY__ELEMENT_TYPE:
				return ELEMENT_TYPE_EDEFAULT == null ? elementType != null : !ELEMENT_TYPE_EDEFAULT.equals(elementType);
			case MessagepropertiesPackage.PROPERTY__QNAME:
				return QNAME_EDEFAULT == null ? qName != null : !QNAME_EDEFAULT.equals(qName);
			case MessagepropertiesPackage.PROPERTY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MessagepropertiesPackage.PROPERTY__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case MessagepropertiesPackage.PROPERTY__ID:
				return ID_EDEFAULT == null ? getID() != null : !ID_EDEFAULT.equals(getID());
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
		result.append(" (qName: ");
		result.append(qName);
		result.append(", name: ");
		result.append(name);
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

	/**
	 * Override the XML element token.
	 */
	public QName getElementType()
	{
		if (elementType == null)
			elementType = new QName(MessagepropertiesConstants.NAMESPACE, MessagepropertiesConstants.PROPERTY_ELEMENT_TAG);
		return elementType;
    }
	
	//
	// Reconcile methods: DOM -> Model
	//

	public void reconcileAttributes(Element changedElement)
	{
	    super.reconcileAttributes(changedElement);

	    setName
    	(MessagepropertiesConstants.getAttribute(changedElement, MessagepropertiesConstants.PROPERTY_NAME_ATTRIBUTE));

	    reconcileReferences(false);
	}

	public void reconcileReferences(boolean deep) {
		if (element != null && element.hasAttribute(MessagepropertiesConstants.PROPERTY_TYPE_ATTRIBUTE))
	    {
	    	Definition definition = getEnclosingDefinition();
	    	QName qname = createQName(definition, element.getAttribute(MessagepropertiesConstants.PROPERTY_TYPE_ATTRIBUTE));
	    	XSDTypeDefinition xsdType = ((DefinitionImpl) definition).resolveTypeDefinition(qname.getNamespaceURI(), qname.getLocalPart());
	    	if (xsdType != null && getType() != xsdType)
	    		setType(xsdType);
	    }
		super.reconcileReferences(deep);		
	}
	
	public void handleUnreconciledElement(Element child, Collection remainingModelObjects)
    {
    }

	//
	// For reconciliation: Model -> DOM
	//

	protected void changeAttribute(EAttribute eAttribute)
	{
	    if (isReconciling)
	        return;

	    super.changeAttribute(eAttribute);
	    Element theElement = getElement();
	    if (theElement != null)
	    {
	        if (eAttribute == null || eAttribute == MessagepropertiesPackage.eINSTANCE.getProperty_Name())
	            niceSetAttribute(theElement,MessagepropertiesConstants.PROPERTY_NAME_ATTRIBUTE,getName());
	        if (eAttribute == null || eAttribute == MessagepropertiesPackage.eINSTANCE.getProperty_Type())
	        {
            	Object type = getType();
            	if (type instanceof XSDTypeDefinition)
            	{
            		XSDTypeDefinition xsdType = (XSDTypeDefinition) type;
            		String uri = xsdType.getURI();
            		niceSetAttributeURIValue(theElement, MessagepropertiesConstants.PROPERTY_TYPE_ATTRIBUTE, uri);
            	}
	        }   
	    }
	}
	
	public Element createElement()
    {
		// Register a prefix for the namespace.  
		// If we don't do this, the WSDL model will deserialize our elements as UnknownExtensibilityElements.
		if (getEnclosingDefinition().getPrefix(MessagepropertiesConstants.NAMESPACE) == null)
			getEnclosingDefinition().addNamespace(MessagepropertiesPackage.eNS_PREFIX, MessagepropertiesConstants.NAMESPACE);
		
	    Element newElement = super.createElement();
	    
	    return newElement;
    }
	
} //PropertyImpl