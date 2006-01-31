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
 * $Id: WhileImpl.java,v 1.2 2006/01/19 21:08:47 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Condition;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Targets;
import org.eclipse.bpel.model.While;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.InternalEList;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>While</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.WhileImpl#getActivity <em>Activity</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.WhileImpl#getCondition <em>Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WhileImpl extends ActivityImpl implements While {
	/**
	 * The cached value of the '{@link #getActivity() <em>Activity</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivity()
	 * @generated
	 * @ordered
	 */
	protected Activity activity = null;

	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected Condition condition = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WhileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getWhile();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActivity(Activity newActivity, NotificationChain msgs) {
		Activity oldActivity = activity;
		activity = newActivity;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.WHILE__ACTIVITY, oldActivity, newActivity);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivity(Activity newActivity) {
		if (newActivity != activity) {
			NotificationChain msgs = null;
			if (activity != null)
				msgs = ((InternalEObject)activity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.WHILE__ACTIVITY, null, msgs);
			if (newActivity != null)
				msgs = ((InternalEObject)newActivity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.WHILE__ACTIVITY, null, msgs);
			msgs = basicSetActivity(newActivity, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.WHILE__ACTIVITY, newActivity, newActivity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Condition getCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCondition(Condition newCondition, NotificationChain msgs) {
		Condition oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.WHILE__CONDITION, oldCondition, newCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(Condition newCondition) {
		if (newCondition != condition) {
			NotificationChain msgs = null;
			if (condition != null)
				msgs = ((InternalEObject)condition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.WHILE__CONDITION, null, msgs);
			if (newCondition != null)
				msgs = ((InternalEObject)newCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.WHILE__CONDITION, null, msgs);
			msgs = basicSetCondition(newCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.WHILE__CONDITION, newCondition, newCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.WHILE__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.WHILE__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.WHILE__TARGETS:
					return basicSetTargets(null, msgs);
				case BPELPackage.WHILE__SOURCES:
					return basicSetSources(null, msgs);
				case BPELPackage.WHILE__ACTIVITY:
					return basicSetActivity(null, msgs);
				case BPELPackage.WHILE__CONDITION:
					return basicSetCondition(null, msgs);
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
			case BPELPackage.WHILE__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.WHILE__ELEMENT:
				return getElement();
			case BPELPackage.WHILE__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.WHILE__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.WHILE__NAME:
				return getName();
			case BPELPackage.WHILE__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.WHILE__TARGETS:
				return getTargets();
			case BPELPackage.WHILE__SOURCES:
				return getSources();
			case BPELPackage.WHILE__ACTIVITY:
				return getActivity();
			case BPELPackage.WHILE__CONDITION:
				return getCondition();
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
			case BPELPackage.WHILE__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.WHILE__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.WHILE__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.WHILE__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.WHILE__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.WHILE__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.WHILE__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.WHILE__SOURCES:
				setSources((Sources)newValue);
				return;
			case BPELPackage.WHILE__ACTIVITY:
				setActivity((Activity)newValue);
				return;
			case BPELPackage.WHILE__CONDITION:
				setCondition((Condition)newValue);
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
			case BPELPackage.WHILE__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.WHILE__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.WHILE__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.WHILE__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.WHILE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.WHILE__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.WHILE__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.WHILE__SOURCES:
				setSources((Sources)null);
				return;
			case BPELPackage.WHILE__ACTIVITY:
				setActivity((Activity)null);
				return;
			case BPELPackage.WHILE__CONDITION:
				setCondition((Condition)null);
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
			case BPELPackage.WHILE__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.WHILE__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.WHILE__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.WHILE__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.WHILE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.WHILE__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.WHILE__TARGETS:
				return targets != null;
			case BPELPackage.WHILE__SOURCES:
				return sources != null;
			case BPELPackage.WHILE__ACTIVITY:
				return activity != null;
			case BPELPackage.WHILE__CONDITION:
				return condition != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //WhileImpl