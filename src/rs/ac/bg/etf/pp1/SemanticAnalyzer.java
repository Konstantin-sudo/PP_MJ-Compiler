package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

	final int MAX_NUMBER_OF_GLOBAL_VAR = 65536;
	final int MAX_NUMBER_OF_LOCAL_VAR = 256;

	int constDeclNumber = 0;
	int globalVarDeclNumber = 0;
	int localVarDeclNumber = 0;

	Struct currentlyReadType = MySymTab.noType;

	int mainFunctionCnt = 0;
	int formParsCntInCurrMethod = 0;

	int nVars = 0;
	boolean errorDetected = false;

	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	// =============================== Program ===============================

	public void visit(ProgName progName) {
		progName.obj = MySymTab.insert(Obj.Prog, progName.getProgName(), MySymTab.noType);
		MySymTab.openScope();
	}

	public void visit(Program program) {
		nVars = MySymTab.currentScope.getnVars();
		MySymTab.chainLocalSymbols(program.getProgName().obj);
		MySymTab.closeScope();
		if (mainFunctionCnt != 1) {
			report_error("Greska: Metoda 'main' mora biti deklarisana tacno jednom. Broj deklarisanih metoda main je: "
					+ mainFunctionCnt + ". Greska", program);
		}
	}

	// =============================== Type ===============================
	public void visit(Type type) {
		Obj typeNode = MySymTab.find(type.getTypeName());
		if (typeNode == MySymTab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
			type.struct = MySymTab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
				type.struct = MySymTab.noType;
			}
		}
		currentlyReadType = type.struct;
	}

	// =============================== Vars ===============================

	public void visit(BasicVarName basicVarName) {
		String varName = basicVarName.getVarName();

		if (MySymTab.currentScope().findSymbol(varName) == null) {

			Obj varNode = MySymTab.insert(Obj.Var, varName, currentlyReadType);

			if (varNode.getLevel() == 0) {
				++globalVarDeclNumber;
			} else {
				++localVarDeclNumber;
			}

			report_info("Pronadjena nova " + (varNode.getLevel() == 0 ? "globalna" : "lokalna")
					+ " deklaracija varijable sa imenom: '" + varName + "'", basicVarName);
		} else {
			report_error("Greska: Varijabla se ne moze deklarisati - simbol sa imenom: '" + varName
					+ "' vec postoji u tabeli simbola. Greska", basicVarName);
		}
	}

	public void visit(ArrayVarName arrayVarName) {
		String varName = arrayVarName.getVarArrayName();

		if (MySymTab.currentScope().findSymbol(varName) == null) {

			Obj varNode = MySymTab.insert(Obj.Var, varName, new Struct(Struct.Array, currentlyReadType));

			if (varNode.getLevel() == 0) {
				++globalVarDeclNumber;
			} else {
				++localVarDeclNumber;
			}
			report_info("Pronadjena nova " + (varNode.getLevel() == 0 ? "globalna" : "lokalna")
					+ " deklaracija niza sa imenom: '" + varName + "'", arrayVarName);
		} else {
			report_error("Greska: Niz se ne moze deklarisati - simbol sa imenom: '" + varName
					+ "' vec postoji u tabeli simbola. Greska", arrayVarName);
		}
	}

	// =============================== Const ===============================

	public void visit(ConstValueNum constValueNum) {
		constValueNum.obj = new Obj(Obj.Con, "constValueNum", MySymTab.intType,
				constValueNum.getConstValue().intValue(), 0);
	}

	public void visit(ConstValueChar constValueChar) {
		constValueChar.obj = new Obj(Obj.Con, "constValueChar", MySymTab.charType,
				constValueChar.getConstValue().charValue(), 0);
	}

	public void visit(ConstValueBool constValueBool) {
		int constValue = constValueBool.getConstValue() == "true" ? 1 : 0;
		constValueBool.obj = new Obj(Obj.Con, "constValueBool", MySymTab.boolType, constValue, 0);
	}

	public void visit(ConstDeclExpression constDeclExpression) {
		String constName = constDeclExpression.getConstName();

		if (MySymTab.find(constName) == MySymTab.noObj) {

			Struct constType = currentlyReadType;
			Struct constValueType = constDeclExpression.getConstValue().obj.getType();

			if (constType == constValueType) {
				int constValue = constDeclExpression.getConstValue().obj.getAdr();

				Obj newConstObj = MySymTab.insert(Obj.Con, constName, constType);
				newConstObj.setAdr(constValue);

				++constDeclNumber;

				report_info("Pronadjena deklaracija nove konstante sa imenom: '" + constName + "'",
						constDeclExpression);
			} else {
				report_error(
						"Greska: Vrednost: " + constDeclExpression.getConstValue().obj.getAdr()
								+ " koja se dodeljuje konstanti: '" + constName + "'  je pogresnog tipa. Greska",
						constDeclExpression);
			}

		} else {
			report_error("Greska: Konstanta se ne moze deklarisati - simbol sa imenom: '" + constName
					+ "' vec postoji u tabeli simbola. Greska", constDeclExpression);
		}
	}

	// =============================== MehodDecl ===============================

	public void visit(MethodName methodName) {

		if (MySymTab.find(methodName.getMethodName()) == MySymTab.noObj) {

			methodName.obj = MySymTab.insert(Obj.Meth, methodName.getMethodName(), MySymTab.noType);

			MySymTab.openScope();

		} else {
			report_error("Greska: Metoda se ne moze deklarisati - simbol sa imenom: '" + methodName.getMethodName()
					+ "' vec postoji u tabeli simbola. Greska", methodName);
		}
	}

	public void visit(MethodDecl methodDecl) {

		if (methodDecl.getMethodName().getMethodName().equals("main")) {

			++mainFunctionCnt;

			if (formParsCntInCurrMethod != 0) {
				report_error("Greska: Metoda main ne sme imati argumente! Greska", methodDecl);
			}
		}

		formParsCntInCurrMethod = 0;

		MySymTab.chainLocalSymbols(methodDecl.getMethodName().obj);
		MySymTab.closeScope();
	}

	public void visit(BasicFormParsDecl basicFormParsDecl) {

		String formParsName = basicFormParsDecl.getFormParName();

		if (MySymTab.currentScope().findSymbol(formParsName) == null) {

			MySymTab.insert(Obj.Var, formParsName, currentlyReadType);

			++formParsCntInCurrMethod;

		} else {
			report_error("Greska: Formalni parametar se ne moze deklarisati - simbol sa imenom: '" + formParsName
					+ "' vec postoji u tabeli simbola. Greska", basicFormParsDecl);
		}
	}

	public void visit(ArrayFormParsDecl arrayFormParsDecl) {

		String formParsName = arrayFormParsDecl.getFormParArrayName();

		if (MySymTab.currentScope().findSymbol(formParsName) == null) {

			MySymTab.insert(Obj.Var, formParsName, new Struct(Struct.Array, currentlyReadType));

			++formParsCntInCurrMethod;

		} else {
			report_error("Greska: Formalni parametar niz se ne moze deklarisati - simbol sa imenom: '" + formParsName
					+ "' vec postoji u tabeli simbola. Greska", arrayFormParsDecl);
		}
	}

	// =============================== Statements ===============================

	public void visit(PrintStatement printStatement) {
		int exprKind = printStatement.getExprPrint().struct.getKind();
		if (exprKind != Struct.Int && exprKind != Struct.Char && exprKind != Struct.Bool) {
			report_error("Greska: Funkcija print prima argumente tipa int, char ili bool. Greska", printStatement);
		}
	}

	public void visit(SingleExprPrint singleExprPrint) {
		singleExprPrint.struct = singleExprPrint.getExpr().struct;
	}

	public void visit(ExprPrintAndNumConst exprPrintAndNumConst) {
		exprPrintAndNumConst.struct = exprPrintAndNumConst.getExpr().struct;
	}

	public void visit(ReadStatement readStatement) {
		Obj designatorObj = readStatement.getDesignator().obj;

		if (designatorObj.getKind() == Obj.Var && designatorObj.getKind() == Obj.Elem) {
			Struct designatorType = designatorObj.getType();
			if (designatorType.getKind() != Struct.Int && designatorType.getKind() != Struct.Char
					&& designatorType.getKind() != Struct.Bool) {
				report_error("Greska: Funkcija read prima argumente tipa int, char ili bool. Greska", readStatement);
			}
		} else {
			report_error("Greska: Funkcija read prima argumente koji su ili promenljiva ili element niza. Greska",
					readStatement);
		}
	}

	public void visit(DesignatorStatementAssign designatorStatementAssign) {

		int designatorKind = designatorStatementAssign.getDesignator().obj.getKind();

		if (designatorKind == Obj.Var || designatorKind == Obj.Elem) {
			Struct dst = designatorStatementAssign.getDesignator().obj.getType();
			Struct src = designatorStatementAssign.getExprToAssign().struct;

			if (!src.assignableTo(dst)) {
				report_error("Greska: src se ne moze dodeliti dst - tipovi nisu kompatibilni. Greska",
						designatorStatementAssign);
			}
		} else {
			if (designatorKind == Obj.NO_VALUE) {
				report_error("Greska: Dst nije deklarisan. Greska", designatorStatementAssign);
			} else {
				report_error("Greska: Dst mora biti varijabla ili element niza. Greska", designatorStatementAssign);
			}
		}
	}

	public void visit(DesignatorStatementInc designatorStatementInc) {

		Obj designator = designatorStatementInc.getDesignator().obj;
		Struct designatorType = designator.getType();

		if (designator.getKind() == Obj.Var || designator.getKind() == Obj.Elem) {
			if (designatorType.getKind() != Struct.Int) {
				report_error(
						"Greska: Izraz '" + designator.getName()
								+ "++' se moze pozvati samo za promenljive tipa int ili elemente niza int-ova. Greska",
						designatorStatementInc);
			}
		} else {
			report_error(
					"Greska: Izraz '" + designator.getName()
							+ "++' se moze pozvati samo za promenljive ili elemente niza. Greska",
					designatorStatementInc);
		}

	}

	public void visit(DesignatorStatementDec designatorStatementDec) {

		Obj designator = designatorStatementDec.getDesignator().obj;
		Struct designatorType = designator.getType();

		if (designator.getKind() == Obj.Var || designator.getKind() == Obj.Elem) {
			if (designatorType.getKind() != Struct.Int) {
				report_error(
						"Greska: Izraz '" + designator.getName()
								+ "--' se moze pozvati samo za promenljive tipa int ili elemente niza int-ova. Greska",
						designatorStatementDec);
			}
		} else {
			report_error(
					"Greska: Izraz '" + designator.getName()
							+ "--' se moze pozvati samo za promenljive ili elemente niza. Greska",
					designatorStatementDec);
		}

	}

	public void visit(DesignatorBasic designatorBasic) {

		String designatorName = designatorBasic.getDesignatorName();

		Obj var = MySymTab.currentScope().findSymbol(designatorName);
		if (var == null) {
			var = MySymTab.find(designatorName);
			if (var == MySymTab.noObj) {
				var = null;
			} else {
				if (var.getLevel() != 0) {
					var = null;
				}
			}
		}
		if (var != null) {

			designatorBasic.obj = var;

		} else {
			designatorBasic.obj = new Obj(Obj.NO_VALUE, "", MySymTab.noType);
			report_error("Greska: Referencirani simbol: '" + designatorName + "' ne postoji u tabeli simbola. Greska",
					designatorBasic);
		}

	}

	public void visit(DesignatorArray designatorArray) {

		String arrayVarName = designatorArray.getDesignatorArrayIdent().getVarName();

		Obj arrayVar = designatorArray.getDesignatorArrayIdent().obj;

		if (arrayVar.getKind() != Obj.NO_VALUE) {
			if (designatorArray.getExpr().struct.getKind() == Struct.Int) {
				designatorArray.obj = new Obj(Obj.Elem, arrayVar.getName(), arrayVar.getType().getElemType());
			} else {
				designatorArray.obj = new Obj(Obj.NO_VALUE, "", MySymTab.noType);
				report_error("Greska: Pri indeksiranju niza: '" + arrayVarName
						+ "' prosledjen je izraz koji nije tipa int. Greska", designatorArray);
			}
		}
	}

	public void visit(DesignatorArrayIdent designatorArrayIdent) {
		String arrayVarName = designatorArrayIdent.getVarName();

		Obj arrayVar = MySymTab.currentScope().findSymbol(arrayVarName);
		if (arrayVar == null) {
			arrayVar = MySymTab.find(arrayVarName);
			if (arrayVar == MySymTab.noObj) {
				arrayVar = null;
			} else {
				if (arrayVar.getLevel() != 0) {
					arrayVar = null;
				}
			}
		}
		if (arrayVar != null) {
			if (arrayVar.getType().getKind() == Struct.Array) {
				designatorArrayIdent.obj = arrayVar;
			} else {
				designatorArrayIdent.obj = new Obj(Obj.NO_VALUE, "", MySymTab.noType);
				report_error("Greska: Referencirani simbol: '" + arrayVarName + "' nije niz. Greska", designatorArrayIdent);
			}
		} else {
			designatorArrayIdent.obj = new Obj(Obj.NO_VALUE, "", MySymTab.noType);
			report_error("Greska: Referencirani simbol: '" + arrayVarName + "' ne postoji u tabeli simbola. Greska",
					designatorArrayIdent);
		}
	}

	// =============================== Expressions ===============================

	public void visit(ExprToAssignSuccess exprToAssignSuccess) {
		exprToAssignSuccess.struct = exprToAssignSuccess.getExpr().struct;
	}

	public void visit(SingleTermExpr singleTermExpr) {
		singleTermExpr.struct = singleTermExpr.getTerm().struct;
	}

	public void visit(NegativeSingleTermExpr negativeSingleTermExpr) {

		if (negativeSingleTermExpr.getTerm().struct.getKind() == Struct.Int) {

			negativeSingleTermExpr.struct = negativeSingleTermExpr.getTerm().struct;

		} else {
			report_error("Greska: Clan izraza '-term' je tipa: '" + negativeSingleTermExpr.getTerm().struct.getKind()
					+ "' a mora biti tipa int. Greska", negativeSingleTermExpr);
		}

	}

	public void visit(TermAddopExprList termAddopExprList) {
		if (termAddopExprList.getExpr().struct.getKind() == Struct.Int
				&& termAddopExprList.getTerm().struct.getKind() == Struct.Int) {

			termAddopExprList.struct = termAddopExprList.getTerm().struct;

		} else {
			report_error("Greska: Clanovi izraza 'expr addop term' moraju biti tipa int. Greska", termAddopExprList);
		}
	}

	public void visit(SingleTermFactor singleTermFactor) {
		singleTermFactor.struct = singleTermFactor.getFactor().struct;
	}

	public void visit(TermMulopFactorList termMulopFactorList) {
		if (termMulopFactorList.getFactor().struct.getKind() == Struct.Int
				&& termMulopFactorList.getTerm().struct.getKind() == Struct.Int) {

			termMulopFactorList.struct = termMulopFactorList.getTerm().struct;

		} else {
			report_error("Greska: Clanovi izraza 'term mulop factor' moraju biti tipa int. Greska",
					termMulopFactorList);
		}
	}

	public void visit(FactorDesignator factorDesignator) {
		factorDesignator.struct = factorDesignator.getDesignator().obj.getType();
	}

	public void visit(FactorConst factorConst) {
		factorConst.struct = factorConst.getConstValue().obj.getType();
	}

	public void visit(FactorExpr factorExpr) {
		factorExpr.struct = factorExpr.getExpr().struct;
	}

	public void visit(FactorArray factorArray) {

		if (factorArray.getExpr().struct.getKind() == Struct.Int) {
			factorArray.struct = new Struct(Struct.Array, factorArray.getType().struct);
		} else {
			report_error("Greska: Index pri referenciranju elem niza mora biti tipa int. Greska", factorArray);
		}

	}

	// ============= Relation operators

	public void visit(CondFactExpr condFactExpr) {
		if (condFactExpr.getExpr().struct.getKind() != Struct.Bool) {
			report_error("Greksa: Clan relacionog izraza mora biti tipa bool. Greska", condFactExpr);
		}
	}

	public void visit(CondFactExprWithRelop condFactExprWithRelop) {
		Struct expr1 = condFactExprWithRelop.getExpr().struct;
		Struct expr2 = condFactExprWithRelop.getExpr1().struct;

		if (expr1.getKind() == Struct.Bool) {
			report_error(
					"Greska: Prvi clan relacionog izraza je tipa bool. Izrazi tipa bool se ne mogu porediti. Greska",
					condFactExprWithRelop);
		} else if (expr2.getKind() == Struct.Bool) {
			report_error(
					"Greska: Drugi clan relacionog izraza nije tipa bool. Izrazi tipa bool se ne mogu porediti. Greska",
					condFactExprWithRelop);
		} else if (expr1.compatibleWith(expr2)) {
			if (expr1.getKind() == Struct.Array || expr2.getKind() == Struct.Array) {
				if (!(condFactExprWithRelop.getRelop() instanceof EqualToOp
						|| condFactExprWithRelop.getRelop() instanceof NotEqualToOp)) {
					report_error(
							"Greska: Uz promenjlive tipa niz u relacionom izrazu je moguce koristiti samo relacione operatore: '==' i '!='. Greska",
							condFactExprWithRelop);
				}
			}
		} else {
			report_error("Greska: Clanovi relacionog izraza nisu kompatibilni. Greska", condFactExprWithRelop);
		}
	}

	// ===================================================================

	public boolean isSuccessfullyFinished() {

		if (globalVarDeclNumber > MAX_NUMBER_OF_GLOBAL_VAR) {
			report_error("Broj deklarisanih globalnih promenljivi (" + globalVarDeclNumber + ") je presao "
					+ MAX_NUMBER_OF_GLOBAL_VAR, null);
			return false;
		}

		if (localVarDeclNumber > MAX_NUMBER_OF_LOCAL_VAR) {
			report_error("Broj deklarisanih lokalnih promenljivi (" + localVarDeclNumber + ") je presao "
					+ MAX_NUMBER_OF_LOCAL_VAR, null);
			return false;
		}

		return !errorDetected;

	}
}
