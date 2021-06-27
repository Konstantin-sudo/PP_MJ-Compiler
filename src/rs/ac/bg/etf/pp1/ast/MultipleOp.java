// generated with ast extension for cup
// version 0.8
// 27/5/2021 11:54:11


package rs.ac.bg.etf.pp1.ast;

public class MultipleOp extends Mulop {

    public MultipleOp () {
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
        buffer.append("MultipleOp(\n");

        buffer.append(tab);
        buffer.append(") [MultipleOp]");
        return buffer.toString();
    }
}
