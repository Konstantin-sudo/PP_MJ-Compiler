package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;

	public int getMainPc() {
		return mainPc;
	}

	
	public void visit(MethodName methodName){
		if(methodName.getMethodName() == "main"){
			mainPc = Code.pc;
		}
		
		methodName.obj.setAdr(Code.pc);
	
		//Collect arguments and local variables 
	 	SyntaxNode methodNode = methodName.getParent();
	 	
	 	VarCounter varCnt = new VarCounter();
	 	methodNode.traverseTopDown(varCnt);
	 	
	 	FormParamCounter fpCnt = new FormParamCounter();
	 	methodNode.traverseTopDown(fpCnt);
	 	
	 	//Generate the entry
	 	Code.put(Code.enter);
	 	Code.put(fpCnt.getCount());
	 	Code.put(fpCnt.getCount() + varCnt.getCount());
	}
	
	public void visit(MethodDecl methodDecl)
	{
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(SingleExprPrint singleExprPrint) {
		int exprKind = singleExprPrint.getExpr().struct.getKind();
		if(exprKind == Struct.Int) {
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
		for(int i = 0; i < n; i++) 
		{
			Code.put(Code.dup);
			
			if(exprKind == Struct.Int) {
				Code.loadConst(5);
				Code.put(Code.print);	
			} else {
				Code.loadConst(1);
				Code.put(Code.bprint);
			}
				
		}
	}

	public void visit(FactorConst factorConst) {
		Code.load(factorConst.getConstValue().obj);
	}

}
