/*******************************************************************************
 * Copyright (c) 2006 Oracle Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.validator.xpath;

import org.eclipse.bpel.validator.model.ARule;
import org.eclipse.bpel.validator.model.INode;
import org.eclipse.bpel.validator.model.IProblem;
import org.jaxen.expr.Expr;
import org.jaxen.expr.FunctionCallExpr;
import org.jaxen.expr.LocationPath;
import org.jaxen.expr.VariableReferenceExpr;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Mar 23, 2007
 *
 */

@SuppressWarnings({"nls","boxing"})
public class Query extends XPathValidator {

	boolean bBpelFunctions  = true;
	boolean bBpelVariables  = false;
	
	
	/**
	 * @see org.eclipse.bpel.validator.xpath.XPathValidator#start()
	 */
	
	@Override
	public void start () {
		super.start();
		bBpelFunctions = getValue("bpel.function",  bBpelFunctions);
		bBpelVariables = getValue("bpel.variables", bBpelVariables);
	}
	/**
	 * Check query type of expressions (can only LocationPaths).
	 *
	 */
	
	@ARule(
		sa = 0,
		desc = "Check the query location path",
		author = "michal.chmielewski@oracle.com",
		date = "01/20/2007",
		order = 15
	)
	
	public void CheckQuery () {
		
		IProblem problem;
		Expr expr = xpathExpr.getRootExpr();
		
		if (expr instanceof LocationPath) {
			mVisitor.visit( expr );
		} else {
			problem = createError();
			problem.fill("XPATH_NOT_A_LOCATION", //$NON-NLS-1$
					exprStringTrimmed,
					fNodeName
				);	
			repointOffsets(problem, expr);
		}			
		
		disableRules();
	}
	
	
	
	/**
	 * Check Location path expressions
	 * @param expr the location path expr
	 *
	 */
	

	@ARule(
		sa = 27,
		desc = "There is no implicit context node in XPath expressions used in BPEL",
		author = "michal.chmielewski@oracle.com",
		date = "0/20/2007",
		tag = "location",
		order = 16
	)
	
	public void CheckLocationPath ( LocationPath expr ) {			
		
		Object obj = mVisitor.contextPeek();
		if (obj instanceof INode) {
			return ;
		}
		
		IProblem problem = createError();
		problem.fill("XPATH_NO_LOCATION_PATH", 
				expr.getText(),
				fNodeName );			
		repointOffsets(problem, expr);
	
		disableRules();
	}
	
	
	
	/**
	 * @param expr
	 */
	@ARule(
		sa = 29,
		desc = "BPEL variables cannot be used in propertyAlias queries",
		author = "michal.chmielewski@oracle.com",
		date = "01/30/2007",
		tag = "variables"
	)		
	
	public void CheckVariable ( VariableReferenceExpr expr ) {
		if (bBpelVariables) {
			return ;
		}
	}

	
	/**
	 * @param expr
	 */
	@SuppressWarnings("nls")
	@ARule(
		sa = 29,
		desc = "BPEL Functions must not be used in query expressions for propertyAlias",
		author = "michal.chmielewski@oracle.com",
		date = "01/20/2007",
		tag = "bpel.functions"
	)
	public void CheckBPELFunctions ( FunctionCallExpr expr ) {
				
		if (bBpelFunctions) {
			return ;
		}
		
		IProblem problem = createError();
		problem.fill("XPATH_BPEL_FUNCTION", 
				mNode.nodeName(),				
				expr.getFunctionName(),
				fExprByNode
			);		
		repointOffsets(problem, expr);				
	}
	
	
	
}