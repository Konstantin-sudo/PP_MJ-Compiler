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
				return;
			}
		}

		formParsCntInCurrMethod = 0;

		MySymTab.chainLocalSymbols(methodDecl.getMethodName().obj);
		MySymTab.closeScope();
	}

	public void visit(BasicFormParsDecl basicFormParsDecl) {

		String formParsName = basicFormParsDecl.getFormParName();

		if (MySymTab.currentScope().findSymbol(formParsName) == MySymTab.noObj) {

			MySymTab.insert(Obj.Var, formParsName, currentlyReadType);

			++formParsCntInCurrMethod;

		} else {
			report_error("Greska: Formalni parametar se ne moze deklarisati - simbol sa imenom: '" + formParsName
					+ "' vec postoji u tabeli simbola. Greska", basicFormParsDecl);
		}
	}

	public void visit(ArrayFormParsDecl arrayFormParsDecl) {

		String formParsName = arrayFormParsDecl.getFormParArrayName();

		if (MySymTab.currentScope().findSymbol(formParsName) == MySymTab.noObj) {

			MySymTab.insert(Obj.Var, formParsName, new Struct(Struct.Array, currentlyReadType));

			++formParsCntInCurrMethod;

		} else {
			report_error("Greska: Formalni parametar niz se ne moze deklarisati - simbol sa imenom: '" + formParsName
					+ "' vec postoji u tabeli simbola. Greska", arrayFormParsDecl);
		}
	}

	// =============================== Statements ===============================

	public void visit(DesignatorStatementAssign designatorStatementAssign) {

		int designatorKind = designatorStatementAssign.getDesignator().obj.getKind();

		if (designatorKind == Obj.Var || designatorKind == Obj.Elem) {
			Struct dst = designatorStatementAssign.getDesignator().obj.getType();
			Struct src = designatorStatementAssign.getExprToAssign().struct;

			if (!(dst.compatibleWith(src) && src.assignableTo(dst))) {
				report_error("Greska: ", designatorStatementAssign);
			}
		} else {
			report_error("Greska: ", designatorStatementAssign);
		}
	}

	public void visit(DesignatorStatementInc designatorStatementInc) {

		Obj designator = designatorStatementInc.getDesignator().obj;

		Struct designatorType = designator.getType();

		if (designatorType.getKind() != Struct.Int) {

			if (designatorType.getKind() == Struct.Array) {

				if (designatorType.getElemType().getKind() != Struct.Int) {
					report_error(
							"Greska: Izraz '" + designator.getName()
									+ "++' se moze pozvati samo za promenljive tipa int ili tipa niz int-ova. Greska",
							designatorStatementInc);
				}
			} else {
				report_error(
						"Greska: Izraz '" + designator.getName()
								+ "++' se moze pozvati samo za promenljive tipa int ili tipa niz int-ova. Greska",
						designatorStatementInc);
			}
		}

	}

	public void visit(DesignatorStatementDec designatorStatementDec) {

		Obj designator = designatorStatementDec.getDesignator().obj;

		Struct designatorType = designator.getType();

		if (designatorType.getKind() != Struct.Int) {

			if (designatorType.getKind() == Struct.Array) {

				if (designatorType.getElemType().getKind() != Struct.Int) {
					report_error(
							"Greska: Izraz '" + designator.getName()
									+ "--' se moze pozvati samo za promenljive tipa int ili tipa niz int-ova. Greska",
							designatorStatementDec);
				}
			} else {
				report_error(
						"Greska: Izraz '" + designator.getName()
								+ "--' se moze pozvati samo za promenljive tipa int ili tipa niz int-ova. Greska",
						designatorStatementDec);
			}
		}

	}

	public void visit(DesignatorBasic designatorBasic) {

		String designatorName = designatorBasic.getDesignatorName();

		Obj designator = MySymTab.currentScope().findSymbol(designatorName);

		if (designator != MySymTab.noObj) {

			designatorBasic.obj = designator;

		} else {

			report_error("Greska: Referencirani simbol: '" + designatorName + "' ne postoji u tabeli simbola. Greska",
					designatorBasic);

		}
	}

	public void visit(DesignatorArray designatorArray) {

		String designatorName = designatorArray.getDesignatorName();

		Obj designator = MySymTab.currentScope().findSymbol(designatorName);
		if (designator != MySymTab.noObj) {

			if (designator.getType().getKind() == Struct.Array) {

				if (designatorArray.getExpr().struct.getKind() == Struct.Int) {

					designatorArray.obj = new Obj(Obj.Elem, designator.getName(), designator.getType().getElemType());

				} else {

					report_error("Greska: ", designatorArray);

				}

			} else {
				report_error("Greska: ", designatorArray);
			}

		} else {
			report_error("Greska: Referencirani simbol: '" + designatorName + "' ne postoji u tabeli simbola. Greska",
					designatorArray);
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
			report_error("Greska: -term", negativeSingleTermExpr);
		}

	}

	public void visit(TermAddopExprList termAddopExprList) {
		if (termAddopExprList.getExpr().struct.getKind() == Struct.Int
				&& termAddopExprList.getTerm().struct.getKind() == Struct.Int) {

			termAddopExprList.struct = termAddopExprList.getTerm().struct;

		} else {
			report_error("Greska: expr addop term", termAddopExprList);
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
			report_error("Greska: term mulop factor", termMulopFactorList);
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
			report_error("Greska: ", factorArray);
		}

	}

	// ============= Relation operators

	public void visit(CondFactExpr condFactExpr) {
		if (condFactExpr.getExpr().struct.getKind() != Struct.Bool) {
			report_error("Greksa: ", condFactExpr);
		}
	}

	public void visit(CondFactExprWithRelop condFactExprWithRelop) {
		Struct expr1 = condFactExprWithRelop.getExpr().struct;
		Struct expr2 = condFactExprWithRelop.getExpr1().struct;

		if (expr1.getKind() == Struct.Bool) {
			report_error("Greska: ", condFactExprWithRelop);
		} else if (expr2.getKind() == Struct.Bool) {
			report_error("Greska: ", condFactExprWithRelop);
		} else if (expr1.compatibleWith(expr2)) {
			if (expr1.getKind() == Struct.Array || expr2.getKind() == Struct.Array) {
				if (!(condFactExprWithRelop.getRelop() instanceof EqualToOp
						|| condFactExprWithRelop.getRelop() instanceof NotEqualToOp)) {
					report_error("Greska: ", condFactExprWithRelop);
				}
			}
		} else {
			report_error("Greska: ", condFactExprWithRelop);
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
