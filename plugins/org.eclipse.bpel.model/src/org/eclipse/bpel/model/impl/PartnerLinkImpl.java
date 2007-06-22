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
 * $Id: PartnerLinkImpl.java,v 1.6 2007/06/22 21:56:21 mchmielewski Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.model.partnerlinktype.Role;
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
 * An implementation of the model object '<em><b>Partner Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.PartnerLinkImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.PartnerLinkImpl#getMyRole <em>My Role</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.PartnerLinkImpl#getPartnerRole <em>Partner Role</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.PartnerLinkImpl#getPartnerLinkType <em>Partner Link Type</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.PartnerLinkImpl#getInitializePartnerRole <em>Initialize Partner Role</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PartnerLinkImpl extends ExtensibleElementImpl implements PartnerLink {
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
	 * The cached value of the '{@link #getMyRole() <em>My Role</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMyRole()
	 * @generated
	 * @ordered
	 */
	protected Role myRole;

	/**
	 * The cached value of the '{@link #getPartnerRole() <em>Partner Role</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartnerRole()
	 * @generated
	 * @ordered
	 */
	protected Role partnerRole;

	/**
	 * The cached value of the '{@link #getPartnerLinkType() <em>Partner Link Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartnerLinkType()
	 * @generated
	 * @ordered
	 */
	protected PartnerLinkType partnerLinkType;

	/**
	 * The default value of the '{@link #getInitializePartnerRole() <em>Initialize Partner Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitializePartnerRole()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean INITIALIZE_PARTNER_ROLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInitializePartnerRole() <em>Initialize Partner Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitializePartnerRole()
	 * @generated
	 * @ordered
	 */
	protected Boolean initializePartnerRole = INITIALIZE_PARTNER_ROLE_EDEFAULT;

	/**
	 * This is true if the Initialize Partner Role attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean initializePartnerRoleESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PartnerLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.Literals.PARTNER_LINK;
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
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PARTNER_LINK__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Role getMyRole() {
		if (myRole != null && myRole.eIsProxy()) {
			InternalEObject oldMyRole = (InternalEObject)myRole;
			myRole = (Role)eResolveProxy(oldMyRole);
			if (myRole != oldMyRole) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.PARTNER_LINK__MY_ROLE, oldMyRole, myRole));
			}
		}
		return myRole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Role basicGetMyRole() {
		return myRole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMyRole(Role newMyRole) {
		Role oldMyRole = myRole;
		myRole = newMyRole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PARTNER_LINK__MY_ROLE, oldMyRole, myRole));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Role getPartnerRole() {
		if (partnerRole != null && partnerRole.eIsProxy()) {
			InternalEObject oldPartnerRole = (InternalEObject)partnerRole;
			partnerRole = (Role)eResolveProxy(oldPartnerRole);
			if (partnerRole != oldPartnerRole) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.PARTNER_LINK__PARTNER_ROLE, oldPartnerRole, partnerRole));
			}
		}
		return partnerRole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Role basicGetPartnerRole() {
		return partnerRole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPartnerRole(Role newPartnerRole) {
		Role oldPartnerRole = partnerRole;
		partnerRole = newPartnerRole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PARTNER_LINK__PARTNER_ROLE, oldPartnerRole, partnerRole));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PartnerLinkType getPartnerLinkType() {
		if (partnerLinkType != null && partnerLinkType.eIsProxy()) {
			InternalEObject oldPartnerLinkType = (InternalEObject)partnerLinkType;
			partnerLinkType = (PartnerLinkType)eResolveProxy(oldPartnerLinkType);
			if (partnerLinkType != oldPartnerLinkType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.PARTNER_LINK__PARTNER_LINK_TYPE, oldPartnerLinkType, partnerLinkType));
			}
		}
		return partnerLinkType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PartnerLinkType basicGetPartnerLinkType() {
		return partnerLinkType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPartnerLinkType(PartnerLinkType newPartnerLinkType) {
		PartnerLinkType oldPartnerLinkType = partnerLinkType;
		partnerLinkType = newPartnerLinkType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PARTNER_LINK__PARTNER_LINK_TYPE, oldPartnerLinkType, partnerLinkType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getInitializePartnerRole() {
		return initializePartnerRole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitializePartnerRole(Boolean newInitializePartnerRole) {
		Boolean oldInitializePartnerRole = initializePartnerRole;
		initializePartnerRole = newInitializePartnerRole;
		boolean oldInitializePartnerRoleESet = initializePartnerRoleESet;
		initializePartnerRoleESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PARTNER_LINK__INITIALIZE_PARTNER_ROLE, oldInitializePartnerRole, initializePartnerRole, !oldInitializePartnerRoleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetInitializePartnerRole() {
		Boolean oldInitializePartnerRole = initializePartnerRole;
		boolean oldInitializePartnerRoleESet = initializePartnerRoleESet;
		initializePartnerRole = INITIALIZE_PARTNER_ROLE_EDEFAULT;
		initializePartnerRoleESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.PARTNER_LINK__INITIALIZE_PARTNER_ROLE, oldInitializePartnerRole, INITIALIZE_PARTNER_ROLE_EDEFAULT, oldInitializePartnerRoleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetInitializePartnerRole() {
		return initializePartnerRoleESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BPELPackage.PARTNER_LINK__NAME:
				return getName();
			case BPELPackage.PARTNER_LINK__MY_ROLE:
				if (resolve) return getMyRole();
				return basicGetMyRole();
			case BPELPackage.PARTNER_LINK__PARTNER_ROLE:
				if (resolve) return getPartnerRole();
				return basicGetPartnerRole();
			case BPELPackage.PARTNER_LINK__PARTNER_LINK_TYPE:
				if (resolve) return getPartnerLinkType();
				return basicGetPartnerLinkType();
			case BPELPackage.PARTNER_LINK__INITIALIZE_PARTNER_ROLE:
				return getInitializePartnerRole();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BPELPackage.PARTNER_LINK__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.PARTNER_LINK__MY_ROLE:
				setMyRole((Role)newValue);
				return;
			case BPELPackage.PARTNER_LINK__PARTNER_ROLE:
				setPartnerRole((Role)newValue);
				return;
			case BPELPackage.PARTNER_LINK__PARTNER_LINK_TYPE:
				setPartnerLinkType((PartnerLinkType)newValue);
				return;
			case BPELPackage.PARTNER_LINK__INITIALIZE_PARTNER_ROLE:
				setInitializePartnerRole((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case BPELPackage.PARTNER_LINK__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.PARTNER_LINK__MY_ROLE:
				setMyRole((Role)null);
				return;
			case BPELPackage.PARTNER_LINK__PARTNER_ROLE:
				setPartnerRole((Role)null);
				return;
			case BPELPackage.PARTNER_LINK__PARTNER_LINK_TYPE:
				setPartnerLinkType((PartnerLinkType)null);
				return;
			case BPELPackage.PARTNER_LINK__INITIALIZE_PARTNER_ROLE:
				unsetInitializePartnerRole();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BPELPackage.PARTNER_LINK__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.PARTNER_LINK__MY_ROLE:
				return myRole != null;
			case BPELPackage.PARTNER_LINK__PARTNER_ROLE:
				return partnerRole != null;
			case BPELPackage.PARTNER_LINK__PARTNER_LINK_TYPE:
				return partnerLinkType != null;
			case BPELPackage.PARTNER_LINK__INITIALIZE_PARTNER_ROLE:
				return isSetInitializePartnerRole();
		}
		return super.eIsSet(featureID);
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
		result.append(", initializePartnerRole: ");
		if (initializePartnerRoleESet) result.append(initializePartnerRole); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //PartnerLinkImpl
