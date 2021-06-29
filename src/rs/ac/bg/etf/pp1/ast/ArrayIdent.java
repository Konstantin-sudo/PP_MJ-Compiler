// generated with ast extension for cup
// version 0.8
// 27/5/2021 17:2:4


package rs.ac.bg.etf.pp1.ast;

public class ArrayIdent extends VarIdent {

    private String arrayIdent;

    public ArrayIdent (String arrayIdent) {
        this.arrayIdent=arrayIdent;
    }

    public String getArrayIdent() {
        return arrayIdent;
    }

    public void setArrayIdent(String arrayIdent) {
        this.arrayIdent=arrayIdent;
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
        buffer.append("ArrayIdent(\n");

        buffer.append(" "+tab+arrayIdent);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayIdent]");
        return buffer.toString();
    }
}
