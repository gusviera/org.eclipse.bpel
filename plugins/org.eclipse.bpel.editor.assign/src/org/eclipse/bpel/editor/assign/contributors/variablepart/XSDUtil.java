/*******************************************************************************
* Copyright (c) 2011 EBM WebSourcing (PetalsLink)
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* Mickael Istria, EBM WebSourcing (PetalsLink) - initial API and implementation
*******************************************************************************/
package org.eclipse.bpel.editor.assign.contributors.variablepart;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.bpel.model.Query;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.XSDUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDNamedComponent;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 * Copied from org.eclipse.bpel.ui.properties.VariablePartAssignCategory
 */
public class XSDUtil {

	public static String getXPath(VariableTreeContentProvider variableContentProvider, Object element, Shell shell) {
		EObject eObject = null;
		if (element instanceof EObject) {
			eObject = (EObject)element;
		} else if (element instanceof ITreeNode) {
			eObject = (EObject) ((ITreeNode)element).getModelObject();
		}
		Object[] path = variableContentProvider.getPathToRoot(element);

		StringBuilder builder = new StringBuilder();
		ArrayList<String> querySegments = new ArrayList<String>();

		for (Object next : path) {

			Object eObj = BPELUtil.resolveXSDObject(BPELUtil.adapt(next,
					ITreeNode.class).getModelObject());
			builder.setLength(0);

			String targetNamespace = null;
			String namespacePrefix = null;

			if (eObj instanceof XSDAttributeDeclaration) {

				XSDAttributeDeclaration att = (XSDAttributeDeclaration) eObj;
				targetNamespace = att.getTargetNamespace();
				builder.append("/@");

				if (targetNamespace != null) {

					namespacePrefix = BPELUtil.lookupOrCreateNamespacePrefix(eObject, targetNamespace, "xsd", shell);
					if (namespacePrefix == null) {
						break;
					}

					builder.append(namespacePrefix).append(":");
				}
				builder.append(att.getName());

			} else if (eObj instanceof XSDElementDeclaration) {

				XSDElementDeclaration elm = (XSDElementDeclaration) eObj;
				targetNamespace = elm.getTargetNamespace();
				int maxOccurs = XSDUtils.getMaxOccurs(elm);

				builder.append("/");
				if (targetNamespace != null) {
					namespacePrefix = BPELUtil.lookupOrCreateNamespacePrefix(eObject, targetNamespace, "xsd", shell);
					if (namespacePrefix == null) {
						break;
					}
					builder.append(namespacePrefix).append(":");
				}

				builder.append(elm.getName());
				// Unbounded or bounded by something higher then 1.
				if (maxOccurs != 1) {
					builder.append("[1]");
				}
			}

			// If the current builder has length > 0, then there is a query
			// segment for us to put in.
			if (builder.length() > 0) {
				querySegments.add(builder.toString());
			}
		}

		Collections.reverse(querySegments);
		builder.setLength(0);
		for (String s : querySegments) {
			builder.append(s);
		}

		if (builder.length() > 0) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}
	
	public static ITreeNode getTreeNode(VariableTreeContentProvider variableContentProvider, EObject modelObject, Variable variable, Part part, Query queryObject) {
		ArrayList<ITreeNode> pathToNode = new ArrayList<ITreeNode>();
		ITreeNode node = null;

		// First, find the variable node.
		Object context = variable;
		if (context != null) {

			Object[] items = variableContentProvider.getElements(modelObject);
			node = variableContentProvider.findModelNode(items, context, 0);
			if (node != null) {
				pathToNode.add(node);
			}
		}

		// Find the part (or property) node within the container node.
		context = part;

		if (node != null && context != null) {
			Object[] items = variableContentProvider.getChildren(node);
			node = variableContentProvider.findModelNode(items, context,
					variableContentProvider.isCondensed() ? 0 : 1);
			if (node != null) {
				pathToNode.add(node);
			}
		}

		if (context == null) {
			context = variable;
		}

		String query = null;

		if (node != null && context != null) {

			if (queryObject != null) {
				// TODO: we shouldn't ignore the queryLanguage here!!
				query = queryObject.getValue();
			}

			if (query != null && query.length() != 0) {

				int tokenCount = 0;
				outer: for (String token : query.split("\\/")) {
					tokenCount += 1;
					if (token.length() == 0) {
						// Is it the first empty string preceeding the first /
						if (tokenCount == 1) {
							continue;
						}
						// could be // , as in //foo:bar/bar, which is
						// impossible to show here.
						break outer;
					}

					QueryStep step = new QueryStep(token);
					step.updateNamespaceURI(modelObject);

					if (step.fAxis.equals("child") == false) {
						break outer;
					}

					Object[] items = variableContentProvider.getChildren(node);

					inner: for (Object item : items) {

						Object originalMatch = ((ITreeNode) item)
								.getModelObject();
						Object match = BPELUtil.resolveXSDObject(originalMatch);

						if (match instanceof XSDElementDeclaration) {
							XSDElementDeclaration elmDecl = (XSDElementDeclaration) match;

							if (match(step, elmDecl) == false) {
								continue;
							}
							node = variableContentProvider.findModelNode(items,
									originalMatch, 0);
							// no matching node, we are done
							if (node == null) {
								break outer;
							}
							pathToNode.add(node);
							break inner;

						} else if (match instanceof XSDAttributeDeclaration) {

							XSDAttributeDeclaration attrDecl = (XSDAttributeDeclaration) match;
							if (match(step, attrDecl)) {
								node = variableContentProvider.findModelNode(
										items, originalMatch, 0);
								if (node != null) {
									pathToNode.add(node);
								}
							}
							// attribute is the leaf node
							break outer;
						}
					}
				}
			}
		}

		if (pathToNode.size() > 0) {
			node = pathToNode.get(pathToNode.size() - 1);
		}
		return node;
	}
	

	protected static boolean match(QueryStep step, XSDNamedComponent xsdNamed) {
		// local name
		if (step.fLocalPart.equals(xsdNamed.getName()) == false) {
			return false;
		}

		// namespace
		return (step.fNamespaceURI.equals(xsdNamed.getTargetNamespace())
				|| (step.fNamespaceURI.length() == 0 && xsdNamed.getTargetNamespace() == null));
	}
}
