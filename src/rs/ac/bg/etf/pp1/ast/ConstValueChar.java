// generated with ast extension for cup
// version 0.8
// 29/5/2021 23:17:27


package rs.ac.bg.etf.pp1.ast;

public class ConstValueChar extends ConstValue {

    private Character constValue;

    public ConstValueChar (Character constValue) {
        this.constValue=constValue;
    }

    public Character getConstValue() {
        return constValue;
    }

    public void setConstValue(Character constValue) {
        this.constValue=constValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstValueChar(\n");

        buffer.append(" "+tab+constValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstValueChar]");
        return buffer.toString();
    }
}
