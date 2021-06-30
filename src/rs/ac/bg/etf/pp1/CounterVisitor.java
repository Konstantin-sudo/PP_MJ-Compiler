package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.MultipleFormParsDecl;
import rs.ac.bg.etf.pp1.ast.MultipleVarIdent;
import rs.ac.bg.etf.pp1.ast.SingleFormParsDecl;
import rs.ac.bg.etf.pp1.ast.SingleVarIdent;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;

	public int getCount() {
		return count;
	}

	public static class FormParamCounter extends CounterVisitor {

		public void visit(SingleFormParsDecl singleFormParsDecl) {
			count++;
		}

		public void visit(MultipleFormParsDecl multipleFormParsDecl) {
			count++;
		}
	}

	public static class VarCounter extends CounterVisitor {

		public void visit(SingleVarIdent singleVarIdent) {
			count++;
		}
		
		public void visit(MultipleVarIdent multipleVarIdent) {
			count++;
		}
	}
}
