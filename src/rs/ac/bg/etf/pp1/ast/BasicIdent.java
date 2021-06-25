// generated with ast extension for cup
// version 0.8
// 25/5/2021 18:34:55


package rs.ac.bg.etf.pp1.ast;

public class BasicIdent extends VarIdent {

    private String varIdent;

    public BasicIdent (String varIdent) {
        this.varIdent=varIdent;
    }

    public String getVarIdent() {
        return varIdent;
    }

    public void setVarIdent(String varIdent) {
        this.varIdent=varIdent;
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
        buffer.append("BasicIdent(\n");

        buffer.append(" "+tab+varIdent);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BasicIdent]");
        return buffer.toString();
    }
}
