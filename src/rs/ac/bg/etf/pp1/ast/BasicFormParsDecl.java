// generated with ast extension for cup
// version 0.8
// 29/5/2021 23:17:27


package rs.ac.bg.etf.pp1.ast;

public class BasicFormParsDecl extends FormParsDecl {

    private Type Type;
    private String formParName;

    public BasicFormParsDecl (Type Type, String formParName) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.formParName=formParName;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getFormParName() {
        return formParName;
    }

    public void setFormParName(String formParName) {
        this.formParName=formParName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BasicFormParsDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+formParName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BasicFormParsDecl]");
        return buffer.toString();
    }
}
