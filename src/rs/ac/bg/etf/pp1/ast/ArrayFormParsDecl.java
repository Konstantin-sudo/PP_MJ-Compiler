// generated with ast extension for cup
// version 0.8
// 29/5/2021 23:17:27


package rs.ac.bg.etf.pp1.ast;

public class ArrayFormParsDecl extends FormParsDecl {

    private Type Type;
    private String formParArrayName;

    public ArrayFormParsDecl (Type Type, String formParArrayName) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.formParArrayName=formParArrayName;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getFormParArrayName() {
        return formParArrayName;
    }

    public void setFormParArrayName(String formParArrayName) {
        this.formParArrayName=formParArrayName;
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
        buffer.append("ArrayFormParsDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+formParArrayName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayFormParsDecl]");
        return buffer.toString();
    }
}
