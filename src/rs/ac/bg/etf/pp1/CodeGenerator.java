package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;

	public int getMainPc() {
		return mainPc;
	}

	public void visit(MethodName methodName) {
		if (methodName.getMethodName() == "main") {
			mainPc = Code.pc;
		}

		methodName.obj.setAdr(Code.pc);

		// Collect arguments and local variables
		SyntaxNode methodNode = methodName.getParent();

		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);

		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
	}

	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(SingleExprPrint singleExprPrint) {
		int exprKind = singleExprPrint.getExpr().struct.getKind();
		if (exprKind == Struct.Int) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}

	public void visit(ExprPrintAndNumConst exprPrintAndNumConst) {
		int exprKind = exprPrintAndNumConst.getExpr().struct.getKind();
		int n = exprPrintAndNumConst.getN2().intValue();
		for (int i = 0; i < n; i++) {
			Code.put(Code.dup);

			if (exprKind == Struct.Char) {
				Code.loadConst(1);
				Code.put(Code.bprint);
			} else {

				Code.loadConst(5);
				Code.put(Code.print);
			}

		}
	}

	public void visit(ReadStatement readStatement) {
		Obj designatorObj = readStatement.getDesignator().obj;

		// ucitaj vrednost sa ulaza i stavi je na stek
		if (designatorObj.getType().getKind() == Struct.Char) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		// upisi tu vrednost u promenljivu
		Code.store(designatorObj);
	}

	public void visit(DesignatorStatementAssign designatorAssign) {
		Obj designatorObj = designatorAssign.getDesignator().obj;

		if (designatorObj.getKind() == Obj.Elem) {
			if (designatorObj.getType().getKind() == Struct.Char) {
				Code.put(Code.bastore);
			} else {
				Code.put(Code.astore);
			}
		} else {
			Code.store(designatorAssign.getDesignator().obj);
		}
	}

	public void visit(DesignatorStatementInc designatorStatementInc) {

		Obj designatorObj = designatorStatementInc.getDesignator().obj;

		if (designatorObj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
			Code.put(Code.aload);
			Code.put(Code.const_1);
			Code.put(Code.add);
			Code.put(Code.astore);
		} else {
			Code.put(Code.const_1);
			Code.put(Code.add);
			Code.store(designatorObj);
		}
	}

	public void visit(DesignatorStatementDec decDesignatorStatementDec) {
		Obj designatorObj = decDesignatorStatementDec.getDesignator().obj;

		if (designatorObj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
			Code.put(Code.aload);
			Code.put(Code.const_1);
			Code.put(Code.sub);
			Code.put(Code.astore);
		} else {
			Code.put(Code.const_1);
			Code.put(Code.sub);
			Code.store(designatorObj);
		}
	}

	public void visit(DesignatorBasic designatorBasic) {
		Class parentClass = designatorBasic.getParent().getClass();

		if (parentClass.equals(DesignatorStatementInc.class) || parentClass.equals(DesignatorStatementDec.class)
				|| parentClass.equals(FactorDesignator.class)) {
			Code.load(designatorBasic.obj);
		}
	}

	public void visit(DesignatorArrayIdent designatorArrayIdent) {
		Class parentOfParentClass = designatorArrayIdent.getParent().getParent().getClass();

		if (parentOfParentClass.equals(DesignatorStatementInc.class) || parentOfParentClass.equals(DesignatorStatementDec.class)
				|| parentOfParentClass.equals(FactorDesignator.class)) {
			Code.load(designatorArrayIdent.obj);
		}
	}

	public void visit(DesignatorArray designatorArray) {
		Class parentClass = designatorArray.getParent().getClass();

		if (parentClass.equals(FactorDesignator.class)) {
			Struct elemType = designatorArray.obj.getType();
			if (elemType.getKind() == Struct.Char) {
				Code.put(Code.baload);
			} else {
				Code.put(Code.aload);
			}
		}
	}

	public void visit(FactorConst factorConst) {
		Code.load(factorConst.getConstValue().obj);
	}

	/*
	 * private void generateCodeForPredefineMethodCHR() { Obj chrObj =
	 * MySymTab.chrObj; chrObj.setAdr(Code.pc);
	 * 
	 * Code.put(Code.enter); Code.put(1); Code.put(chrObj.getLocalSymbols().size());
	 * 
	 * Code.put(Code.load_n);
	 * 
	 * Code.put(Code.exit); Code.put(Code.return_); }
	 * 
	 * private void generateCodeForPredefineMethodORD() { Obj ordObj =
	 * MySymTab.ordObj; ordObj.setAdr(Code.pc);
	 * 
	 * Code.put(Code.enter); Code.put(1); Code.put(ordObj.getLocalSymbols().size());
	 * 
	 * Code.put(Code.load_n);
	 * 
	 * Code.put(Code.exit); Code.put(Code.return_); }
	 * 
	 * private void generateCodeForPredefineMethodLEN() { Obj lenObj =
	 * MySymTab.lenObj; lenObj.setAdr(Code.pc);
	 * 
	 * Code.put(Code.enter); Code.put(1); Code.put(lenObj.getLocalSymbols().size());
	 * 
	 * Code.put(Code.load_n); Code.put(Code.arraylength);
	 * 
	 * Code.put(Code.exit); Code.put(Code.return_); }
	 * 
	 * public void visit(ProgName progName) {
	 * 
	 * generateCodeForPredefineMethodCHR();
	 * 
	 * generateCodeForPredefineMethodORD();
	 * 
	 * generateCodeForPredefineMethodLEN();
	 * 
	 * }
	 */
}
