// generated with ast extension for cup
// version 0.8
// 26/5/2021 23:7:33


package rs.ac.bg.etf.pp1.ast;

public class Designator implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private ExprDesignator ExprDesignator;

    public Designator (String I1, ExprDesignator ExprDesignator) {
        this.I1=I1;
        this.ExprDesignator=ExprDesignator;
        if(ExprDesignator!=null) ExprDesignator.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public ExprDesignator getExprDesignator() {
        return ExprDesignator;
    }

    public void setExprDesignator(ExprDesignator ExprDesignator) {
        this.ExprDesignator=ExprDesignator;
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
        if(ExprDesignator!=null) ExprDesignator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprDesignator!=null) ExprDesignator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprDesignator!=null) ExprDesignator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Designator(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(ExprDesignator!=null)
            buffer.append(ExprDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator]");
        return buffer.toString();
    }
}
