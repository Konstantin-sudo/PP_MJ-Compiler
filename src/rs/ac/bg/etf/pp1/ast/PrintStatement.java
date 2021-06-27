// generated with ast extension for cup
// version 0.8
// 27/5/2021 11:54:11


package rs.ac.bg.etf.pp1.ast;

public class PrintStatement extends Matched {

    private ExprPrint ExprPrint;

    public PrintStatement (ExprPrint ExprPrint) {
        this.ExprPrint=ExprPrint;
        if(ExprPrint!=null) ExprPrint.setParent(this);
    }

    public ExprPrint getExprPrint() {
        return ExprPrint;
    }

    public void setExprPrint(ExprPrint ExprPrint) {
        this.ExprPrint=ExprPrint;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprPrint!=null) ExprPrint.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprPrint!=null) ExprPrint.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprPrint!=null) ExprPrint.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintStatement(\n");

        if(ExprPrint!=null)
            buffer.append(ExprPrint.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintStatement]");
        return buffer.toString();
    }
}
