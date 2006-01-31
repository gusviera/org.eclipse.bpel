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
 * $Id: LinksImpl.java,v 1.2 2006/01/19 21:08:47 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.Links;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Links</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.LinksImpl#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LinksImpl extends ExtensibleElementImpl implements Links {
	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList children = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinksImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getLinks();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getChildren() {
		if (children == null) {
			children = new EObjectContainmentEList(Link.class, this, BPELPackage.LINKS__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.LINKS__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.LINKS__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.LINKS__CHILDREN:
					return ((InternalEList)getChildren()).basicRemove(otherEnd, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case BPELPackage.LINKS__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.LINKS__ELEMENT:
				return getElement();
			case BPELPackage.LINKS__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.LINKS__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.LINKS__CHILDREN:
				return getChildren();
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
			case BPELPackage.LINKS__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.LINKS__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.LINKS__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.LINKS__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.LINKS__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection)newValue);
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
			case BPELPackage.LINKS__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.LINKS__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.LINKS__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.LINKS__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.LINKS__CHILDREN:
				getChildren().clear();
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
			case BPELPackage.LINKS__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.LINKS__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.LINKS__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.LINKS__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.LINKS__CHILDREN:
				return children != null && !children.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

} //LinksImpl