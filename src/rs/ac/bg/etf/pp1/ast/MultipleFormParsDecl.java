// generated with ast extension for cup
// version 0.8
// 25/5/2021 18:34:55


package rs.ac.bg.etf.pp1.ast;

public class MultipleFormParsDecl extends FormPars {

    private FormPars FormPars;
    private FormParsDecl FormParsDecl;

    public MultipleFormParsDecl (FormPars FormPars, FormParsDecl FormParsDecl) {
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
        this.FormParsDecl=FormParsDecl;
        if(FormParsDecl!=null) FormParsDecl.setParent(this);
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
    }

    public FormParsDecl getFormParsDecl() {
        return FormParsDecl;
    }

    public void setFormParsDecl(FormParsDecl FormParsDecl) {
        this.FormParsDecl=FormParsDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormPars!=null) FormPars.accept(visitor);
        if(FormParsDecl!=null) FormParsDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
        if(FormParsDecl!=null) FormParsDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        if(FormParsDecl!=null) FormParsDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleFormParsDecl(\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsDecl!=null)
            buffer.append(FormParsDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleFormParsDecl]");
        return buffer.toString();
    }
}
