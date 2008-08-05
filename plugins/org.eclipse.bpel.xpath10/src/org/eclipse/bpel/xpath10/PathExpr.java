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

package org.eclipse.bpel.xpath10;

public class PathExpr extends Expr {
	
	Expr fFilterExpr;
	LocationPath fLocationPath;
	
	public PathExpr ( Expr filter, LocationPath locationPath) {
		super(null);
		fFilterExpr = filter;
		fLocationPath = locationPath;
	}
	
	public Expr getFilterExpr() {
		return fFilterExpr;
	}
	
	public LocationPath getLocationPath() {
		return fLocationPath;
	}
	
	protected String asText() {
        StringBuilder buf = new StringBuilder();

        if (getFilterExpr() != null) {
            buf.append(getFilterExpr().getText());
        }

        if (getLocationPath() != null) {
            if (!getLocationPath().getSteps().isEmpty()) buf.append("/");
            buf.append(getLocationPath().getText());
        }

        return buf.toString();
    }
	
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("{");
		buf.append(getClass().getSimpleName());
		buf.append(",");
        if (getFilterExpr() != null) {
            buf.append(getFilterExpr() );
        }

        if (getLocationPath() != null) {
            if (!getLocationPath().getSteps().isEmpty()) buf.append("/");
            buf.append(getLocationPath() );
        }
        buf.append("}");
        
        return buf.toString();
	}
	  
	
	public int getPosition () {
		return fFilterExpr.getPosition();
	}
	
	public int getEndPosition () {
		if (fLocationPath != null) {
			return fLocationPath.getEndPosition();
		}
		return fFilterExpr.getEndPosition();
	}
}