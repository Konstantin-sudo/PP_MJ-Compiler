// generated with ast extension for cup
// version 0.8
// 27/5/2021 11:54:11


package rs.ac.bg.etf.pp1.ast;

public class SingleConstDeclExpression extends ConstDeclExpressionList {

    private ConstDeclExpression ConstDeclExpression;

    public SingleConstDeclExpression (ConstDeclExpression ConstDeclExpression) {
        this.ConstDeclExpression=ConstDeclExpression;
        if(ConstDeclExpression!=null) ConstDeclExpression.setParent(this);
    }

    public ConstDeclExpression getConstDeclExpression() {
        return ConstDeclExpression;
    }

    public void setConstDeclExpression(ConstDeclExpression ConstDeclExpression) {
        this.ConstDeclExpression=ConstDeclExpression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclExpression!=null) ConstDeclExpression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclExpression!=null) ConstDeclExpression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclExpression!=null) ConstDeclExpression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleConstDeclExpression(\n");

        if(ConstDeclExpression!=null)
            buffer.append(ConstDeclExpression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleConstDeclExpression]");
        return buffer.toString();
    }
}
