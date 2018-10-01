package custis.easyabac.core.model.easy;

import custis.easyabac.core.model.abac.Condition;
import custis.easyabac.core.model.abac.Effect;
import custis.easyabac.core.model.abac.Operation;

import java.util.List;

public class EasyRule {
    private String title;
    private Effect effect = Effect.PERMIT;
    private Operation operation = Operation.AND;
    private List<Condition> conditions;

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }


    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
