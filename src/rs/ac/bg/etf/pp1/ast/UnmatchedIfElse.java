// generated with ast extension for cup
// version 0.8
// 29/5/2021 23:17:27


package rs.ac.bg.etf.pp1.ast;

public class UnmatchedIfElse extends Unmatched {

    private Condition Condition;
    private RparenIfCondition RparenIfCondition;
    private Matched Matched;
    private Else Else;
    private Unmatched Unmatched;

    public UnmatchedIfElse (Condition Condition, RparenIfCondition RparenIfCondition, Matched Matched, Else Else, Unmatched Unmatched) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.RparenIfCondition=RparenIfCondition;
        if(RparenIfCondition!=null) RparenIfCondition.setParent(this);
        this.Matched=Matched;
        if(Matched!=null) Matched.setParent(this);
        this.Else=Else;
        if(Else!=null) Else.setParent(this);
        this.Unmatched=Unmatched;
        if(Unmatched!=null) Unmatched.setParent(this);
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public RparenIfCondition getRparenIfCondition() {
        return RparenIfCondition;
    }

    public void setRparenIfCondition(RparenIfCondition RparenIfCondition) {
        this.RparenIfCondition=RparenIfCondition;
    }

    public Matched getMatched() {
        return Matched;
    }

    public void setMatched(Matched Matched) {
        this.Matched=Matched;
    }

    public Else getElse() {
        return Else;
    }

    public void setElse(Else Else) {
        this.Else=Else;
    }

    public Unmatched getUnmatched() {
        return Unmatched;
    }

    public void setUnmatched(Unmatched Unmatched) {
        this.Unmatched=Unmatched;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(RparenIfCondition!=null) RparenIfCondition.accept(visitor);
        if(Matched!=null) Matched.accept(visitor);
        if(Else!=null) Else.accept(visitor);
        if(Unmatched!=null) Unmatched.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(RparenIfCondition!=null) RparenIfCondition.traverseTopDown(visitor);
        if(Matched!=null) Matched.traverseTopDown(visitor);
        if(Else!=null) Else.traverseTopDown(visitor);
        if(Unmatched!=null) Unmatched.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(RparenIfCondition!=null) RparenIfCondition.traverseBottomUp(visitor);
        if(Matched!=null) Matched.traverseBottomUp(visitor);
        if(Else!=null) Else.traverseBottomUp(visitor);
        if(Unmatched!=null) Unmatched.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("UnmatchedIfElse(\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RparenIfCondition!=null)
            buffer.append(RparenIfCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Matched!=null)
            buffer.append(Matched.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Else!=null)
            buffer.append(Else.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Unmatched!=null)
            buffer.append(Unmatched.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [UnmatchedIfElse]");
        return buffer.toString();
    }
}
