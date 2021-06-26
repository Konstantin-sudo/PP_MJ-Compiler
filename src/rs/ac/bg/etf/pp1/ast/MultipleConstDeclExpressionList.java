// generated with ast extension for cup
// version 0.8
// 26/5/2021 22:36:38


package rs.ac.bg.etf.pp1.ast;

public class MultipleConstDeclExpressionList extends ConstDeclExpressionList {

    private ConstDeclExpressionList ConstDeclExpressionList;
    private ConstDeclExpression ConstDeclExpression;

    public MultipleConstDeclExpressionList (ConstDeclExpressionList ConstDeclExpressionList, ConstDeclExpression ConstDeclExpression) {
        this.ConstDeclExpressionList=ConstDeclExpressionList;
        if(ConstDeclExpressionList!=null) ConstDeclExpressionList.setParent(this);
        this.ConstDeclExpression=ConstDeclExpression;
        if(ConstDeclExpression!=null) ConstDeclExpression.setParent(this);
    }

    public ConstDeclExpressionList getConstDeclExpressionList() {
        return ConstDeclExpressionList;
    }

    public void setConstDeclExpressionList(ConstDeclExpressionList ConstDeclExpressionList) {
        this.ConstDeclExpressionList=ConstDeclExpressionList;
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
        if(ConstDeclExpressionList!=null) ConstDeclExpressionList.accept(visitor);
        if(ConstDeclExpression!=null) ConstDeclExpression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclExpressionList!=null) ConstDeclExpressionList.traverseTopDown(visitor);
        if(ConstDeclExpression!=null) ConstDeclExpression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclExpressionList!=null) ConstDeclExpressionList.traverseBottomUp(visitor);
        if(ConstDeclExpression!=null) ConstDeclExpression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleConstDeclExpressionList(\n");

        if(ConstDeclExpressionList!=null)
            buffer.append(ConstDeclExpressionList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclExpression!=null)
            buffer.append(ConstDeclExpression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleConstDeclExpressionList]");
        return buffer.toString();
    }
}
