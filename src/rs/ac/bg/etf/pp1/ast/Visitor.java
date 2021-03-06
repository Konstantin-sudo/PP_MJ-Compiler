// generated with ast extension for cup
// version 0.8
// 30/5/2021 22:35:55


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Unmatched Unmatched);
    public void visit(DeclarationList DeclarationList);
    public void visit(Mulop Mulop);
    public void visit(VarIdent VarIdent);
    public void visit(Matched Matched);
    public void visit(Relop Relop);
    public void visit(StatementList StatementList);
    public void visit(ConstDeclExpressionList ConstDeclExpressionList);
    public void visit(Addop Addop);
    public void visit(Factor Factor);
    public void visit(CondTerm CondTerm);
    public void visit(VarIdentError VarIdentError);
    public void visit(Designator Designator);
    public void visit(Term Term);
    public void visit(FormParsList FormParsList);
    public void visit(Condition Condition);
    public void visit(ConstValue ConstValue);
    public void visit(ExprPrint ExprPrint);
    public void visit(ExprToAssign ExprToAssign);
    public void visit(Expr Expr);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(Statement Statement);
    public void visit(VarIdentList VarIdentList);
    public void visit(CondFact CondFact);
    public void visit(MethodVarDeclList MethodVarDeclList);
    public void visit(Declaration Declaration);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(FormParsDecl FormParsDecl);
    public void visit(FormPars FormPars);
    public void visit(CondFactExprWithRelop CondFactExprWithRelop);
    public void visit(CondFactExpr CondFactExpr);
    public void visit(SingleConditionFact SingleConditionFact);
    public void visit(ConditionFactConjunctionList ConditionFactConjunctionList);
    public void visit(Or Or);
    public void visit(SingleConditionTerm SingleConditionTerm);
    public void visit(ConditionTermDisjunctionList ConditionTermDisjunctionList);
    public void visit(LessThenOrEqualToOp LessThenOrEqualToOp);
    public void visit(LessThenOp LessThenOp);
    public void visit(GreaterThenOrEqualToOp GreaterThenOrEqualToOp);
    public void visit(GreaterThenOp GreaterThenOp);
    public void visit(NotEqualToOp NotEqualToOp);
    public void visit(EqualToOp EqualToOp);
    public void visit(ModuleOp ModuleOp);
    public void visit(DivideOp DivideOp);
    public void visit(MultiplyOp MultiplyOp);
    public void visit(MinusOp MinusOp);
    public void visit(PlusOp PlusOp);
    public void visit(FactorArray FactorArray);
    public void visit(FactorExpr FactorExpr);
    public void visit(FactorConst FactorConst);
    public void visit(FactorDesignator FactorDesignator);
    public void visit(TermMulopFactorList TermMulopFactorList);
    public void visit(SingleTermFactor SingleTermFactor);
    public void visit(TermAddopExprList TermAddopExprList);
    public void visit(NegativeSingleTermExpr NegativeSingleTermExpr);
    public void visit(SingleTermExpr SingleTermExpr);
    public void visit(ExprPrintAndNumConst ExprPrintAndNumConst);
    public void visit(SingleExprPrint SingleExprPrint);
    public void visit(DesignatorArrayIdent DesignatorArrayIdent);
    public void visit(DesignatorArray DesignatorArray);
    public void visit(DesignatorBasic DesignatorBasic);
    public void visit(ErrorAssign ErrorAssign);
    public void visit(ExprToAssignSuccess ExprToAssignSuccess);
    public void visit(DesignatorStatementDec DesignatorStatementDec);
    public void visit(DesignatorStatementInc DesignatorStatementInc);
    public void visit(DesignatorStatementAssign DesignatorStatementAssign);
    public void visit(Else Else);
    public void visit(RparenIfCondition RparenIfCondition);
    public void visit(MatchedIfElse MatchedIfElse);
    public void visit(NestedStatement NestedStatement);
    public void visit(PrintStatement PrintStatement);
    public void visit(ReadStatement ReadStatement);
    public void visit(DesignatorStmt DesignatorStmt);
    public void visit(UnmatchedIfElse UnmatchedIfElse);
    public void visit(UnmatchedIf UnmatchedIf);
    public void visit(UnmatchedStatement UnmatchedStatement);
    public void visit(MatchedStatement MatchedStatement);
    public void visit(EmptyStatementList EmptyStatementList);
    public void visit(NotEmptyStatementList NotEmptyStatementList);
    public void visit(EmptyMethodVarDeclList EmptyMethodVarDeclList);
    public void visit(NotEmptyMethodVarDeclList NotEmptyMethodVarDeclList);
    public void visit(ArrayFormParsDecl ArrayFormParsDecl);
    public void visit(BasicFormParsDecl BasicFormParsDecl);
    public void visit(SingleFormParsDecl SingleFormParsDecl);
    public void visit(MultipleFormParsDecl MultipleFormParsDecl);
    public void visit(EmptyFormParsList EmptyFormParsList);
    public void visit(NotEmptyFormParsList NotEmptyFormParsList);
    public void visit(Type Type);
    public void visit(MethodName MethodName);
    public void visit(MethodDecl MethodDecl);
    public void visit(EmptyMethodDeclList EmptyMethodDeclList);
    public void visit(MultipleMethodDecl MultipleMethodDecl);
    public void visit(VarIdentListError VarIdentListError);
    public void visit(ArrayVarName ArrayVarName);
    public void visit(BasicVarName BasicVarName);
    public void visit(SingleVarIdent SingleVarIdent);
    public void visit(MultipleVarIdent MultipleVarIdent);
    public void visit(VarDeclList VarDeclList);
    public void visit(ConstValueBool ConstValueBool);
    public void visit(ConstValueChar ConstValueChar);
    public void visit(ConstValueNum ConstValueNum);
    public void visit(ConstDeclExpression ConstDeclExpression);
    public void visit(SingleConstDeclExpression SingleConstDeclExpression);
    public void visit(MultipleConstDeclExpressionList MultipleConstDeclExpressionList);
    public void visit(ConstDeclList ConstDeclList);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(ConstDeclarations ConstDeclarations);
    public void visit(EmptyDeclarationList EmptyDeclarationList);
    public void visit(MultipleDeclarationList MultipleDeclarationList);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
