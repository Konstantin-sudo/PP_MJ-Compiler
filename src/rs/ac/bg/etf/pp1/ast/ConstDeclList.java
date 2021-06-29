// generated with ast extension for cup
// version 0.8
// 29/5/2021 23:17:27


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private ConstDeclExpressionList ConstDeclExpressionList;

    public ConstDeclList (Type Type, ConstDeclExpressionList ConstDeclExpressionList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ConstDeclExpressionList=ConstDeclExpressionList;
        if(ConstDeclExpressionList!=null) ConstDeclExpressionList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ConstDeclExpressionList getConstDeclExpressionList() {
        return ConstDeclExpressionList;
    }

    public void setConstDeclExpressionList(ConstDeclExpressionList ConstDeclExpressionList) {
        this.ConstDeclExpressionList=ConstDeclExpressionList;
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
        if(Type!=null) Type.accept(visitor);
        if(ConstDeclExpressionList!=null) ConstDeclExpressionList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstDeclExpressionList!=null) ConstDeclExpressionList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstDeclExpressionList!=null) ConstDeclExpressionList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclList(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclExpressionList!=null)
            buffer.append(ConstDeclExpressionList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclList]");
        return buffer.toString();
    }
}
