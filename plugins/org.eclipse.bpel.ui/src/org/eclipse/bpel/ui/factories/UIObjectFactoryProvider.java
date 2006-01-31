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
package org.eclipse.bpel.ui.factories;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.bpel.ui.Policy;
import org.eclipse.bpel.ui.bpelactions.AbstractBPELAction;
import org.eclipse.bpel.ui.extensions.ActionDescriptor;
import org.eclipse.bpel.ui.extensions.BPELUIRegistry;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.util.Assert;


/**
 * A factory provider for BPEL model objects and other model objects used within the
 * BPEL editor.
 */
public class UIObjectFactoryProvider {

    private static UIObjectFactoryProvider instance;
	protected Map eClass2factory = new HashMap();
	
	public static UIObjectFactoryProvider getInstance() {
	    if (instance == null) {
		    instance = new UIObjectFactoryProvider();
		    createUIObjectFactories(instance);
	    }
	    return instance;
	}
	
	protected static void createUIObjectFactories(UIObjectFactoryProvider provider) {
		// TODO: temporary HACK!
		for (int i = 0; i<BPELUIObjectFactory.classArray.length; i++) {
			EClass modelType = BPELUIObjectFactory.classArray[i];
			provider.register(modelType, new BPELUIObjectFactory(modelType));
		}

		// TODO: this is even worse
//		for (int i = 0; i<BPELUIObjectFactory.bpelPlusClassArray.length; i++) {
//			EClass modelType = BPELUIObjectFactory.bpelPlusClassArray[i];
//			provider.register(modelType, new BPELUIObjectFactory(modelType));
//		}
		
		// TODO: We are currently overwritting the ones already provided above.
		// We should change that so that we do not create the ones for action twice.
		// (i.e. we should not use BPELUIObjectFactory.classArray neither BPELUIObjectFactory.bpelPlusClassArray
		ActionDescriptor[] descriptors = BPELUIRegistry.getInstance().getActionDescriptors();
		for (int i = 0; i < descriptors.length; i++) {
            AbstractBPELAction action = descriptors[i].getAction();
            provider.register(action.getModelType(), new ActionUIObjectFactory(action));
        }
	}
	
	public AbstractUIObjectFactory getFactoryFor(EClass modelType) {
		return (AbstractUIObjectFactory) eClass2factory.get(modelType);
	}
	
	public void register(EClass modelType, AbstractUIObjectFactory factory) {
		Assert.isTrue(factory.getModelType() == modelType);
		eClass2factory.put(modelType, factory);
		if (Policy.DEBUG) System.out.println("BPELUIObjectFactoryProvider registering EClass: "+modelType.getInstanceClassName()); //$NON-NLS-1$
	}
}