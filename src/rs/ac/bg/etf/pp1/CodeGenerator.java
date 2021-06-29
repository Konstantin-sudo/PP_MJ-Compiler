package rs.ac.bg.etf.pp1;

import java.util.ArrayList;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int currentRelop = 0;
	public static ArrayList<Integer> currentCondFalse = new ArrayList<Integer>();
	public static ArrayList<Integer> currentCondTrue = new ArrayList<Integer>();
	public static ArrayList<Integer> endOfElse = new ArrayList<Integer>();

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

	// ================================= statements
	// ================================================

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

	// ==================================== designator
	// ======================================

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

		if (parentOfParentClass.equals(DesignatorStatementInc.class)
				|| parentOfParentClass.equals(DesignatorStatementDec.class)
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

	// ================================= expressions
	// ============================================

	public void visit(NegativeSingleTermExpr negativeSingleTermExpr) {
		Code.put(Code.neg);
	}

	public void visit(TermAddopExprList termAddopExprList) {
		Class addopClass = termAddopExprList.getAddop().getClass();
		if (addopClass.equals(PlusOp.class)) {
			Code.put(Code.add);
		} else {
			Code.put(Code.sub);
		}
	}

	public void visit(TermMulopFactorList termMulopFactorList) {
		Class mulopClass = termMulopFactorList.getMulop().getClass();
		if (mulopClass.equals(MultiplyOp.class)) {
			Code.put(Code.mul);
		} else if (mulopClass.equals(DivideOp.class)) {
			Code.put(Code.div);
		} else {
			Code.put(Code.rem);
		}
	}

	public void visit(FactorConst factorConst) {
		Code.load(factorConst.getConstValue().obj);
	}

	public void visit(FactorArray factorArray) {
		Struct type = factorArray.getType().struct;
		Code.put(Code.newarray);
		Code.put(type.getKind() == Struct.Char ? 0 : 1);
	}

	// ================================= relop
	// ================================================

	public void visit(EqualToOp equalToOp) {
		currentRelop = Code.eq;
	}

	public void visit(NotEqualToOp notEqualToOp) {
		currentRelop = Code.ne;
	}

	public void visit(GreaterThenOp greaterThenOp) {
		currentRelop = Code.gt;
	}

	public void visit(GreaterThenOrEqualToOp greaterThenOrEqualToOp) {
		currentRelop = Code.ge;
	}

	public void visit(LessThenOp lessThenOp) {
		currentRelop = Code.lt;
	}

	public void visit(LessThenOrEqualToOp lessThenOrEqualToOp) {
		currentRelop = Code.le;
	}

	// ================================= Condition ============================

	public void visit(CondFactExprWithRelop condFactExprWithRelop) {
		Code.putFalseJump(currentRelop, 0);
		currentCondFalse.add(Code.pc - 2);
	}

	public void visit(CondFactExpr condFactExpr) {
		Code.put(Code.const_1);
		Code.putFalseJump(Code.eq, 0);
		currentCondFalse.add(Code.pc - 2);
	}

	public void visit(Or or) {
		Code.put(Code.jmp);
		Code.put2(0 - Code.pc + 1);

		currentCondTrue.add(Code.pc - 2);

		for (Integer adr : currentCondFalse) {
			Code.fixup(adr);
		}

		currentCondFalse = new ArrayList<Integer>();
	}

	public void visit(UnmatchedIfElse unmatchedIf) {
		if (!endOfElse.isEmpty()) {
			Code.fixup(endOfElse.remove(0));
		}
	}

	public void visit(MatchedIfElse unmatchedIf) {
		if (!endOfElse.isEmpty()) {
			Code.fixup(endOfElse.remove(0));
		}
	}

	public void visit(Else else1) {

		Code.putJump(0);
		endOfElse.add(Code.pc - 2);

		for (Integer address : currentCondFalse) {
			Code.fixup(address);
		}
		currentCondFalse = new ArrayList<Integer>();
	}

	public void visit(UnmatchedIf unmatchedIf) {
		for (Integer address : currentCondFalse) {
			Code.fixup(address);
		}
		if (!endOfElse.isEmpty()) {
			Code.fixup(endOfElse.remove(0));
		}
		currentCondFalse = new ArrayList<Integer>();
	}

	public void visit(RparenIfCondition rparenIfCondition) {
		for (Integer address : currentCondTrue) {
			Code.fixup(address);
		}
		currentCondTrue = new ArrayList<Integer>();
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
