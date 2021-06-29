package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;

public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String[] args) throws Exception {

		Logger log = Logger.getLogger(Compiler.class);

		Reader br = null;
		try {
			File sourceCode = new File("test/test301.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());

			log.info("=================================== SINTAKSNA ANALIZA ===================================");
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);

			MJParser parser = new MJParser(lexer);
			Symbol s = parser.parse(); // pocetak parsiranja

			Program prog = (Program) (s.value);

			// ispis sintaksnog stabla
			log.info("=================================== SINTAKSNO STABLO ===================================");
			log.info(prog.toString(""));

			log.info("=================================== SEMANTICKA ANALIZA ===================================");
			MySymTab.myInit();
			SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
			prog.traverseBottomUp(semanticAnalyzer);

			if (semanticAnalyzer.isSuccessfullyFinished()) {
				File objFile = new File("test/program.obj");
				if (objFile.exists())
					objFile.delete();

				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize = semanticAnalyzer.nVars;
				Code.mainPc = codeGenerator.getMainPc();
				Code.write(new FileOutputStream(objFile));
				log.info("Semanticka analiza uspesno zavrsena!");
			} else {
				log.error("Semanticka analiza nije uspesno zavrsena!");
			}

			MySymTab.dump();

			// ispis prepoznatih programskih konstrukcija
			log.info(" Globalno deklarisanih promenljivih ima = " + semanticAnalyzer.globalVarDeclNumber);
			log.info(" Lokalno deklarisanih promenljivih ima = " + semanticAnalyzer.localVarDeclNumber);
			log.info(" Deklarisanih konstanti ima = " + semanticAnalyzer.constDeclNumber);
			log.info(" Deklarisanih metoda main ima = " + semanticAnalyzer.mainFunctionCnt);

		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e1) {
					log.error(e1.getMessage(), e1);
				}
		}

	}
}
