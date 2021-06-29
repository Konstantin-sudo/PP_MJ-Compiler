// generated with ast extension for cup
// version 0.8
// 27/5/2021 17:2:4


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementAssign extends DesignatorStatement {

    private Designator Designator;
    private ExprToAssign ExprToAssign;

    public DesignatorStatementAssign (Designator Designator, ExprToAssign ExprToAssign) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.ExprToAssign=ExprToAssign;
        if(ExprToAssign!=null) ExprToAssign.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public ExprToAssign getExprToAssign() {
        return ExprToAssign;
    }

    public void setExprToAssign(ExprToAssign ExprToAssign) {
        this.ExprToAssign=ExprToAssign;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(ExprToAssign!=null) ExprToAssign.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(ExprToAssign!=null) ExprToAssign.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(ExprToAssign!=null) ExprToAssign.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementAssign(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprToAssign!=null)
            buffer.append(ExprToAssign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementAssign]");
        return buffer.toString();
    }
}
