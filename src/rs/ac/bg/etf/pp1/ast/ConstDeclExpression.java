// generated with ast extension for cup
// version 0.8
// 25/5/2021 18:34:55


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclExpression implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String constIdent;
    private ConstValue ConstValue;

    public ConstDeclExpression (String constIdent, ConstValue ConstValue) {
        this.constIdent=constIdent;
        this.ConstValue=ConstValue;
        if(ConstValue!=null) ConstValue.setParent(this);
    }

    public String getConstIdent() {
        return constIdent;
    }

    public void setConstIdent(String constIdent) {
        this.constIdent=constIdent;
    }

    public ConstValue getConstValue() {
        return ConstValue;
    }

    public void setConstValue(ConstValue ConstValue) {
        this.ConstValue=ConstValue;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstValue!=null) ConstValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstValue!=null) ConstValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstValue!=null) ConstValue.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclExpression(\n");

        buffer.append(" "+tab+constIdent);
        buffer.append("\n");

        if(ConstValue!=null)
            buffer.append(ConstValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclExpression]");
        return buffer.toString();
    }
}
