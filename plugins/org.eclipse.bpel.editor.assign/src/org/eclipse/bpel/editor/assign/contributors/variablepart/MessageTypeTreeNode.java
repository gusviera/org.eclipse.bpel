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
package org.eclipse.bpel.editor.assign.contributors.variablepart;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.bpel.model.messageproperties.Property;
import org.eclipse.bpel.model.messageproperties.PropertyAlias;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.bpel.ui.details.tree.PartTreeNode;
import org.eclipse.bpel.ui.details.tree.PropertyTreeNode;
import org.eclipse.bpel.ui.details.tree.TreeNode;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Input;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Output;
import org.eclipse.wst.wsdl.Part;

/**
 * Tree node to represent a message-type model object.
 */
public class MessageTypeTreeNode extends TreeNode {

	boolean displayParticles;
	
	public MessageTypeTreeNode(Input msg, boolean isCondensed) {
		this(msg, isCondensed, true);		
	}
	
	
	public MessageTypeTreeNode(Output msg, boolean isCondensed) {
		this(msg, isCondensed, true);		
	}
	
	public MessageTypeTreeNode(Message messageType, boolean isCondensed) {
		this(messageType, isCondensed, true);
	}
	
	
	public MessageTypeTreeNode (Message messageType, boolean isCondensed, boolean displayParticles) {
		this((EObject) messageType,isCondensed, displayParticles);
	}
	
	
	private MessageTypeTreeNode(EObject obj,  boolean isCondensed, boolean displayParticles ) 
	{
		super(obj, isCondensed);
		this.displayParticles = displayParticles;
	}

	/* ITreeNode */

	@Override
	public Object[] getChildren() {
		
		Message msg = getMessage();
		
		if (msg == null) {
			return null;
		}
	
		List<ITreeNode> res = new ArrayList<ITreeNode>();
		
		// Find propertyAliases that refer to this message.
		List<PropertyAlias> aliases = BPELUtil.getPropertyAliasesForMessageType(msg);
		List<Property> properties = getPropertiesFromPropertyAliases(aliases);

		for (Iterator<Property> it = properties.iterator(); it.hasNext(); ) {
			res.add(new PropertyTreeNode(it.next(), isCondensed));
		}

		for (Object part : msg.getParts().values()) {
			res.add(new PartTreeNode((Part)part, isCondensed, displayParticles));
		}
		return res.toArray();
	}

	@Override
	public boolean hasChildren() {
		return getChildren().length > 0;
	}

	/* other methods */
	
	protected List<Property> getPropertiesFromPropertyAliases(List<PropertyAlias> aliases) {
		List<Property> properties = new ArrayList<Property>();
		Set<Property> propertySet = new HashSet<Property>();
		for (Iterator<PropertyAlias> it = aliases.iterator(); it.hasNext(); ) {
			PropertyAlias alias = it.next();
			Property property = (Property)alias.getPropertyName();
			if (!propertySet.contains(property)) {
				properties.add(property);
				propertySet.add(property);
			}
		}
		return properties;
	}
	
	
	Message getMessage () {
		if (modelObject instanceof Message) {
			return (Message) modelObject;
		}
		if (modelObject instanceof Input) {
			return ((Input)modelObject).getEMessage();
		}
		if (modelObject instanceof Output) {
			return ((Output)modelObject).getEMessage();
		}
		return null;
	}
	
	
}
