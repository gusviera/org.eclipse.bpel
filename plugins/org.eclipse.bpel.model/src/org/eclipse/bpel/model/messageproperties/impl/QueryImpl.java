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
 * $Id: QueryImpl.java,v 1.1 2005/11/29 18:50:26 james Exp $
 */
package org.eclipse.bpel.model.messageproperties.impl;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.messageproperties.MessagepropertiesPackage;
import org.eclipse.bpel.model.messageproperties.Query;
import org.eclipse.bpel.model.messageproperties.util.MessagepropertiesConstants;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.wst.wsdl.internal.impl.ExtensibilityElementImpl;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Query</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.messageproperties.impl.QueryImpl#getQueryLanguage <em>Query Language</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.messageproperties.impl.QueryImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class QueryImpl extends ExtensibilityElementImpl implements Query {
	/**
	 * The default value of the '{@link #getQueryLanguage() <em>Query Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueryLanguage()
	 * @generated
	 * @ordered
	 */
	protected static final String QUERY_LANGUAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQueryLanguage() <em>Query Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueryLanguage()
	 * @generated
	 * @ordered
	 */
	protected String queryLanguage = QUERY_LANGUAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected QueryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return MessagepropertiesPackage.eINSTANCE.getQuery();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getQueryLanguage() {
		return queryLanguage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueryLanguage(String newQueryLanguage) {
		String oldQueryLanguage = queryLanguage;
		queryLanguage = newQueryLanguage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MessagepropertiesPackage.QUERY__QUERY_LANGUAGE, oldQueryLanguage, queryLanguage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MessagepropertiesPackage.QUERY__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case MessagepropertiesPackage.QUERY__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case MessagepropertiesPackage.QUERY__ELEMENT:
				return getElement();
			case MessagepropertiesPackage.QUERY__REQUIRED:
				return isRequired() ? Boolean.TRUE : Boolean.FALSE;
			case MessagepropertiesPackage.QUERY__ELEMENT_TYPE:
				return getElementType();
			case MessagepropertiesPackage.QUERY__QUERY_LANGUAGE:
				return getQueryLanguage();
			case MessagepropertiesPackage.QUERY__VALUE:
				return getValue();
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
			case MessagepropertiesPackage.QUERY__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case MessagepropertiesPackage.QUERY__ELEMENT:
				setElement((Element)newValue);
				return;
			case MessagepropertiesPackage.QUERY__REQUIRED:
				setRequired(((Boolean)newValue).booleanValue());
				return;
			case MessagepropertiesPackage.QUERY__ELEMENT_TYPE:
				setElementType((QName)newValue);
				return;
			case MessagepropertiesPackage.QUERY__QUERY_LANGUAGE:
				setQueryLanguage((String)newValue);
				return;
			case MessagepropertiesPackage.QUERY__VALUE:
				setValue((String)newValue);
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
			case MessagepropertiesPackage.QUERY__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case MessagepropertiesPackage.QUERY__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case MessagepropertiesPackage.QUERY__REQUIRED:
				setRequired(REQUIRED_EDEFAULT);
				return;
			case MessagepropertiesPackage.QUERY__ELEMENT_TYPE:
				setElementType(ELEMENT_TYPE_EDEFAULT);
				return;
			case MessagepropertiesPackage.QUERY__QUERY_LANGUAGE:
				setQueryLanguage(QUERY_LANGUAGE_EDEFAULT);
				return;
			case MessagepropertiesPackage.QUERY__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case MessagepropertiesPackage.QUERY__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case MessagepropertiesPackage.QUERY__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case MessagepropertiesPackage.QUERY__REQUIRED:
				return required != REQUIRED_EDEFAULT;
			case MessagepropertiesPackage.QUERY__ELEMENT_TYPE:
				return ELEMENT_TYPE_EDEFAULT == null ? elementType != null : !ELEMENT_TYPE_EDEFAULT.equals(elementType);
			case MessagepropertiesPackage.QUERY__QUERY_LANGUAGE:
				return QUERY_LANGUAGE_EDEFAULT == null ? queryLanguage != null : !QUERY_LANGUAGE_EDEFAULT.equals(queryLanguage);
			case MessagepropertiesPackage.QUERY__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
		result.append(" (queryLanguage: ");
		result.append(queryLanguage);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

	//
	// Reconcile methods: DOM -> Model
	//

	public void reconcileAttributes(Element changedElement)
	{
	    //System.out.println("RoleImpl.reconcileAttributes("+changedElement+")");
	    super.reconcileAttributes(changedElement);

	    setQueryLanguage(MessagepropertiesConstants.getAttribute(changedElement, MessagepropertiesConstants.QUERY_QUERYLANGUAGE_ATTRIBUTE));

		// Determine whether or not there is an element in the child list.
		Node candidateChild = null;
		NodeList nodeList = changedElement.getChildNodes();
		int length = nodeList.getLength();
		for (int i = 0; i < length; i++) {
			Node child = nodeList.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				candidateChild = child;
				break;
			}
		}
		if (candidateChild == null) {
			candidateChild = changedElement.getFirstChild();
		}
		String data = getText(candidateChild);
		if (data != null) {
			setValue(data);
		}
		reconcileReferences(true); // TODO true?
	}

	public void handleUnreconciledElement(Element child, Collection remainingModelObjects)
    {
	    //System.out.println("QueryImpl.handleUnreconciledElement()");
//		String value = getText(child);
//		if (value != null) {
//			setValue(value);
//		}
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
	        if (eAttribute == null || eAttribute == MessagepropertiesPackage.eINSTANCE.getQuery_QueryLanguage())
	            niceSetAttribute(theElement,MessagepropertiesConstants.QUERY_QUERYLANGUAGE_ATTRIBUTE,getQueryLanguage());
	    }
	}

	public Element createElement()
    {
	    //System.out.println("QueryImpl.createElement()");
	    Element newElement = super.createElement();
	    
	    String value = getValue();
	    if (value != null) {
			CDATASection cdata = getEnclosingDefinition().getDocument().createCDATASection(value);
			newElement.appendChild(cdata);
	    }
	    
	    return newElement;
    }
	
	/**
	 * Helper method to get a string from the given text node or CDATA text node.
	 */
	private String getText(Node node) {
		String data = "";
		boolean containsValidData = false;
		while (node != null) {
			if (node.getNodeType() == Node.TEXT_NODE) {
				Text text = (Text)node;
				data += text.getData();
			} else if (node.getNodeType() == Node.CDATA_SECTION_NODE) {
				data="";
				do {
					CDATASection cdata = (CDATASection) node;
					data += cdata.getData();
					node = node.getNextSibling();
					containsValidData = true;
				} while (node != null && node.getNodeType() == Node.CDATA_SECTION_NODE);
				break;
			}
			node = node.getNextSibling();
		}
		if (!containsValidData) {
			for (int i = 0; i < data.length(); i++) {
				char charData = data.charAt(i);
				if (charData == '\n' || Character.isWhitespace(charData)){}//ignore
				else { //valid data
					containsValidData = true;
					break;
				}
			}
		}
		if (containsValidData) {
			return data;
		} else {
			return null;
		}
	}
	
	
	/**
	 * Override the XML element token.
	 */
	public QName getElementType()
	{
		if (elementType == null) 
			elementType = new QName(MessagepropertiesConstants.NAMESPACE, MessagepropertiesConstants.QUERY_ELEMENT_TAG);
		return elementType;
    }
	
	
} //QueryImpl