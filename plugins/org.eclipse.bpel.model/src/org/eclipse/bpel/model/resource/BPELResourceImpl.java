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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.bpel.model.Import;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.util.BPELConstants;
import org.eclipse.bpel.model.util.BPELProxyURI;
import org.eclipse.bpel.model.util.ImportResolver;
import org.eclipse.bpel.model.util.ImportResolverRegistry;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;


public class BPELResourceImpl extends XMLResourceImpl implements BPELResource { 
	protected static boolean USE_IMPORTS = false;

    /** @see #getNamespaceURI() */
    private String processNamespaceURI = BPELConstants.NAMESPACE;
    
    /** @see #getOptionUseNSPrefix() */
    private boolean optionUseNSPrefix = true;
    
	public class NotifierMapImpl extends HashMap implements NotifierMap {		
		private Vector listeners = new Vector();
		
		public NotifierMapImpl() {
			super();
		}
		
		public void addListener(MapListener listener) {
			listeners.add(listener);
		}
		
		public void removeListener(MapListener listener) {
			listeners.remove(listener);
		}

		public NotifierMap reserve() {
			NotifierMap reserveMap = new NotifierMapImpl();
			for (Iterator i = this.entrySet().iterator(); i.hasNext(); ) {
				Map.Entry entry = (Map.Entry)i.next();
				reserveMap.put(entry.getValue(), entry.getKey());
			}
			return reserveMap;						
		}				
		
		public Object put(Object key, Object value) {
			for (Iterator it = listeners.iterator(); it.hasNext();) {
				((MapListener)it.next()).objectAdded(key, value);
			}
			return super.put(key, value);
		}
	}

	public NotifierMap prefixToNamespaceMap = new NotifierMapImpl();
	// Properties for validating bpel document
	protected boolean validating = false;
	protected EntityResolver entityResolver = null;
	protected ErrorHandler errorHandler = null;

	public BPELResourceImpl() {
		super();
	}

	public BPELResourceImpl(URI arg0) {
		super(arg0);
	}
	
	public BPELResourceImpl(URI uri, EntityResolver entityResolver, ErrorHandler errorHandler) throws IOException {
		super(uri);
		this.entityResolver = entityResolver;
		this.errorHandler = errorHandler;
		validating = true;
	}

	/**
	 * Convert the BPEL model to an XML DOM model and then write the DOM model
	 * to the output stream.
	 */
	public void doSave(OutputStream out, Map args) throws IOException
    {
        Map nsMap = getPrefixToNamespaceMap();
        if (getOptionUseNSPrefix()) {
            // Check for existing prefix.
            String prefix = null;
            for (Iterator i = nsMap.entrySet().iterator(); i.hasNext();) {
                Map.Entry entry = (Map.Entry) i.next();
                if (getNamespaceURI().equals(entry.getValue())) {
                    // Remove the entry if it is the default namespace.
                    if ("".equals(entry.getKey())) {
                        i.remove();
                    } else {
                        prefix = (String) entry.getKey();
                    }
                }
            }
            // Install the BPEL namespace with prefix if it was not already defined.
            if (prefix == null) {
                nsMap.put(BPELConstants.PREFIX, getNamespaceURI());
            }
        } else {
            // Install the BPEL namespace as the default namespace.
            nsMap.put("", getNamespaceURI());
        }
        
// RTP:        resolver = new Resolver(this);
		
        BPELWriter writer = new BPELWriter();
		writer.write(this, out, args);
	}
	
	/** 
	 * Convert a BPEL XML document into the BPEL EMF model.
     * After loading, the process' namespace URI is reset to the current namespace URI.
	 */
	public void doLoad(InputStream inputStream, Map options) throws IOException
    {
        getPrefixToNamespaceMap().put("", BPELConstants.NAMESPACE);
        
// RTP:        resolver = new Resolver(this);
        
		BPELReader reader = new BPELReader(getDocumentBuilder());
		reader.read(this, inputStream);
        
        setNamespaceURI(BPELConstants.NAMESPACE);
	}

	public NotifierMap getPrefixToNamespaceMap(Object object)
    {
		NotifierMap map = null;		
		if (object instanceof Process)
			map = (NotifierMap)prefixToNamespaceMap.get(this);
		else
			map = (NotifierMap)prefixToNamespaceMap.get(object);
			
		if (map == null) {
			map = new NotifierMapImpl();
			prefixToNamespaceMap.put(object, map);
		}
		return map;		
	}
	public NotifierMap getPrefixToNamespaceMap(){
		return getPrefixToNamespaceMap(this);
	}
	
    /*
     * TODO Implement getURIFragment to return our encoding.
     */
    public String getURIFragment(EObject eObject)
    {
        return super.getURIFragment(eObject);
    }
    
    /**
     * Find and return the EObject represented by the given uriFragment.
     *
     * @return the resolved EObject or null if none could be found.
     */
    public EObject getEObject(String uriFragment) {
	    if (uriFragment == null) return null;
	    try {
	    	// Consult the superclass
		    EObject eObject = super.getEObject(uriFragment);
		    if (eObject != null) return eObject;
		    // Consult our helper method
	        eObject = getEObjectExtended(uriFragment);
	        if (eObject != null) return eObject;
			return null;
	    } catch (RuntimeException e) {
	    	// TODO: Should log this instead of printing to stderr.
	        e.printStackTrace();
	        throw e;
	    }
	}

    /**
     * Helper method for resolving the EObject.
     * 
     */
    protected EObject getEObjectExtended(String uriFragment) 
    {
    	// RTP: this implementation should be extensible
    	
        BPELProxyURI proxyURI = new BPELProxyURI(uriFragment);
        
        QName qname = proxyURI.getQName();
        String typeName = proxyURI.getTypeName();

        if (qname == null || typeName == null) {
            return null;
        }
        
        EObject result = null;
        
    	// Try the BPEL imports if any exist.
        Process process = getProcess();
        if (process != null) 
        {
            Iterator it = process.getImports().iterator();
            while (it.hasNext() && result == null)
            {
                Import imp = (Import) it.next();
                if (imp.getNamespace().equals(qname.getNamespaceURI()))
                {
                	if (imp.getLocation() != null) {
	                    ImportResolver[] resolvers = ImportResolverRegistry.INSTANCE.getResolvers(imp.getImportType());
	                    for (int i = 0; i < resolvers.length; i++)
	                    {
	                        result = resolvers[i].resolve(imp, qname, proxyURI.getID(), proxyURI.getTypeName());
	                        if (result != null) {
	                            return result;
	                        }
	                    }
                	}
                }
            }
        }
        
        // Failed to resolve.
        return null;
    }

	public Process getProcess() {
	    return getContents().size() == 1 && getContents().get(0) instanceof Process ? (Process) getContents().get(0) : null;
	}
	
	protected DocumentBuilder getDocumentBuilder() throws IOException {
		final DocumentBuilderFactory factory = //DocumentBuilderFactory.newInstance();
		   // new org.apache.crimson.jaxp.DocumentBuilderFactoryImpl();
			new org.apache.xerces.jaxp.DocumentBuilderFactoryImpl();

		if (validating && factory.getClass().getName().indexOf("org.apache.xerces") != -1)
		{
		  // Note: This section is subject to change as this issue will be
		  //       addressed in a maintenance release of JSR-63.
		  //       Hopefully this will be a proper API in JAXP 1.2!
		  // turn dynamic schema validation on
		  factory.setAttribute("http://apache.org/xml/features/validation/dynamic", Boolean.TRUE);
		  // turn schema validation on
		  factory.setAttribute("http://apache.org/xml/features/validation/schema", Boolean.TRUE);
		  // set the default schemaLocation for syntactical validation
          factory.setAttribute("http://apache.org/xml/properties/schema/external-schemaLocation",
              BPELConstants.NAMESPACE_2004 + " " + BPELConstants.NAMESPACE_2004);
		}

		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);

		factory.setValidating(validating);
		factory.setNamespaceAware(true);

		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException exc) {
			// exc.printStackTrace();
			throw new IOException(exc.toString());
		}
		if (validating) {
			builder.setEntityResolver( entityResolver );
			builder.setErrorHandler( errorHandler );
		}

		return builder;
	}
	
	public static void setUseImports(boolean useImports) {
	    USE_IMPORTS = useImports;
	}

    public String getNamespaceURI() {
        return processNamespaceURI;
    }
    
    public void setNamespaceURI(String namespaceURI) {
        processNamespaceURI = namespaceURI;
    }
    
    public boolean getOptionUseNSPrefix() {
        return optionUseNSPrefix;
    }
    
    public void setOptionUseNSPrefix(boolean useNSPrefix) {
        optionUseNSPrefix = useNSPrefix;
    }

}	