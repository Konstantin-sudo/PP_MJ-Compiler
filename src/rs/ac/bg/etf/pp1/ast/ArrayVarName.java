// generated with ast extension for cup
// version 0.8
// 30/5/2021 22:35:55


package rs.ac.bg.etf.pp1.ast;

public class ArrayVarName extends VarIdent {

    private String varArrayName;

    public ArrayVarName (String varArrayName) {
        this.varArrayName=varArrayName;
    }

    public String getVarArrayName() {
        return varArrayName;
    }

    public void setVarArrayName(String varArrayName) {
        this.varArrayName=varArrayName;
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
        buffer.append("ArrayVarName(\n");

        buffer.append(" "+tab+varArrayName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayVarName]");
        return buffer.toString();
    }
}
