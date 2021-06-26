package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;

public class RuleVisitor extends VisitorAdaptor {

	int printCallCount = 0;
	int varDeclCount = 0;

	boolean globalDecl = true;

	Logger log = Logger.getLogger(getClass());

	public void visit(MethodDeclList methodDeclList) {
		globalDecl = false;
	}

	public void visit(VarDeclList varDeclList) {
		if (globalDecl) {
			varDeclCount++;
		}
	}

	public void visit(PrintStatement print) {
		printCallCount++;
	}

}
