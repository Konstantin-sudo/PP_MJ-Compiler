package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

	int constDeclNumber = 0;
	int globalVarDeclNumber = 0;
	int localVarDeclNumber = 0;

	Struct currentlyReadType = MySymTab.noType;

	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
//		errorDetected = true;
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

	// ===============================================================

	public void visit(ProgName progName) {
		progName.obj = MySymTab.insert(Obj.Prog, progName.getProgName(), MySymTab.noType);
		MySymTab.openScope();
	}

	public void visit(Program program) {
		MySymTab.chainLocalSymbols(program.getProgName().obj);
		MySymTab.closeScope();
	}

	public void visit(BasicVarName basicVarName) {
		String varName = basicVarName.getVarName();

		if (MySymTab.currentScope().findSymbol(varName) == null) {
			Obj varNode = MySymTab.insert(Obj.Var, varName, currentlyReadType);
			globalVarDeclNumber++;
			report_info(
					"Pronadjena deklaracija nove varijable sa imenom: '" + varName + "' tipa: '" + currentlyReadType
							+ "'. Odgovarajuci objektni cvor iz tabele simbola: [" + varNode + "]. Pronadjeno",
					basicVarName);
		} else {
			report_error("Greska: Simbol sa imenom: '" + varName + "' se vec postoji u tabeli simbola. Greska",
					basicVarName);
		}
	}

	public void visit(ArrayVarName arrayVarName) {
		String varName = arrayVarName.getVarArrayName();

		if (MySymTab.currentScope().findSymbol(varName) == null) {
			Obj varNode = MySymTab.insert(Obj.Var, varName, currentlyReadType);
			globalVarDeclNumber++;
			report_info(
					"Pronadjena deklaracija novog niza sa imenom: '" + varName + "' tipa: '" + currentlyReadType
							+ "'. Odgovarajuci objektni cvor iz tabele simbola: [" + varNode + "]. Pronadjeno",
					arrayVarName);
		} else {
			report_error("Greska: Simbol sa imenom: '" + varName + "' se vec postoji u tabeli simbola. Greska",
					arrayVarName);
		}
	}

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

}
