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
package org.eclipse.bpel.model.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.bpel.model.Process;
import org.eclipse.emf.ecore.resource.Resource;


public interface BPELResource extends Resource {

    /**
     * Converts the BPEL model to an XML DOM model and then write the DOM model to the output stream.
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#doSave(OutputStream, Map)
	 */
	void doSave(OutputStream out, Map args) throws IOException;
    
    /**
     * Returns the BPEL process contained by this resource or <code>null</code> if there is none.
     */
    Process getProcess();

    /**
     * Returns the namespace URI for the process/resource.
     * If unset the default value is {@link org.eclipse.bpel.model.util.BPELConstants#NAMESPACE}.
     */
    String getNamespaceURI();    
    
    /**
     * Sets the <code>namespaceURI</code> for the process/resource.
     * Expected to be one of the BPEL namespaces defined by {@link org.eclipse.bpel.model.util.BPELConstants}.
     */
    void setNamespaceURI(String namespaceURI);
    
    /**
     * Returns <code>true</code> if the resource will be saved using a prefix for the BPEL namespace.
     * Returns <code>false</code> if the resource will be saved with BPEL as the default namespace.
     */
    boolean getOptionUseNSPrefix();
    
    /**
     * Sets the useNSPrefix option.
     * @see #getOptionUseNSPrefix()
     */
    void setOptionUseNSPrefix(boolean useNSPrefix);

    interface MapListener {
        public void objectAdded(Object key, Object value);
    }
    
    interface NotifierMap extends Map {
        public void addListener(MapListener listener);
        public void removeListener(MapListener listener);
        public NotifierMap reserve();
    }

    /**
     * Returns the prefix-to-namespace map for the resource/process.
     */
    NotifierMap getPrefixToNamespaceMap();

    /**
     * Returns the prefix-to-namespace map for the specified <code>object</code>.
     */
    NotifierMap getPrefixToNamespaceMap(Object object);
    
}