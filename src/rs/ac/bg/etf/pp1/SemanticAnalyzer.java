package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

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
		if(mainFunctionCnt != 1) {
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
		
		if(methodDecl.getMethodName().getMethodName().equals("main")) {
		
			++mainFunctionCnt;
			
			if(formParsCntInCurrMethod != 0) {
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
		
		if(MySymTab.currentScope().findSymbol(formParsName) == MySymTab.noObj) {
			
			MySymTab.insert(Obj.Var, formParsName, currentlyReadType);
			
			++formParsCntInCurrMethod;
			
		}else {
			report_error("Greska: Formalni parametar se ne moze deklarisati - simbol sa imenom: '" + formParsName
					+ "' vec postoji u tabeli simbola. Greska", basicFormParsDecl);
		}
	}
	
	public void visit(ArrayFormParsDecl arrayFormParsDecl) {
		
		String formParsName = arrayFormParsDecl.getFormParArrayName();
		
		if(MySymTab.currentScope().findSymbol(formParsName) == MySymTab.noObj) {
			
			MySymTab.insert(Obj.Var, formParsName, new Struct(Struct.Array, currentlyReadType));
			
			++formParsCntInCurrMethod;
			
		}else {
			report_error("Greska: Formalni parametar niz se ne moze deklarisati - simbol sa imenom: '" + formParsName
					+ "' vec postoji u tabeli simbola. Greska", arrayFormParsDecl);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}





































