package org.eclipse.bpel.editor.assign.contributors.variablepart;

import org.eclipse.bpel.model.util.BPELUtils;
import org.eclipse.emf.ecore.EObject;

class QueryStep {

	String fAxis = "child";

	String fPrefix = "";
	String fLocalPart = "";
	String fNamespaceURI = "";

	@SuppressWarnings("nls")
	QueryStep(String step) {

		int axisMark = step.indexOf("::");
		if (axisMark >= 0) {
			fAxis = step.substring(0, axisMark);
			step = step.substring(axisMark + 2);
		}

		int qnameMark = step.indexOf(":");
		if (qnameMark < 0) {
			fLocalPart = step;
		} else {
			fLocalPart = step.substring(qnameMark + 1);
			fPrefix = step.substring(0, qnameMark);
		}

		if (fLocalPart.charAt(0) == '@') {
			fLocalPart = fLocalPart.substring(1);
		}

		int arrayMark1 = fLocalPart.indexOf('[');
		int arrayMark2 = fLocalPart.indexOf(']');
		if (arrayMark2 > arrayMark1 && arrayMark1 > 0) {
			fLocalPart = fLocalPart.substring(0, arrayMark1);
		}
	}

	void updateNamespaceURI(EObject eObj) {
		if (fPrefix.length() > 0) {
			fNamespaceURI = BPELUtils.getNamespace(eObj, fPrefix);
			if (fNamespaceURI == null) {
				fNamespaceURI = "urn:unresolved:" + System.currentTimeMillis()
						+ ":" + fPrefix;
			}
		} else {
			fNamespaceURI = "";
		}
	}

}