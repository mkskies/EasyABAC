package custis.easyabac.core.pdp.balana.functions;

import custis.easyabac.model.attribute.DataType;
import org.wso2.balana.cond.*;

public class BalanaDateTimeFunctions implements BalanaFunctions {
    @Override
    public Function equal() {
        return new EqualFunction(EqualFunction.NAME_DATETIME_EQUAL);
    }

    @Override
    public Function greater() {
        return new ComparisonFunction(ComparisonFunction.NAME_DATETIME_GREATER_THAN);
    }

    @Override
    public Function less() {
        return new ComparisonFunction(ComparisonFunction.NAME_DATETIME_LESS_THAN);
    }

    @Override
    public Function greaterOrEqual() {
        return new ComparisonFunction(ComparisonFunction.NAME_DATETIME_GREATER_THAN_OR_EQUAL);
    }

    @Override
    public Function lessOrEqual() {
        return new ComparisonFunction(ComparisonFunction.NAME_DATETIME_LESS_THAN_OR_EQUAL);
    }

    @Override
    public Function in() {
        return new ConditionBagFunction("urn:oasis:names:tc:xacml:1.0:function:dateTime-is-in");
    }

    @Override
    public Function oneOf() {
        return new ConditionSetFunction("urn:oasis:names:tc:xacml:1.0:function:date-at-least-one-member-of");
    }

    @Override
    public Function subset() {
        return new ConditionSetFunction("urn:oasis:names:tc:xacml:1.0:function:date-subset");
    }

    @Override
    public Function bag() {
        return new GeneralBagFunction("urn:oasis:names:tc:xacml:1.0:function:dateTime-bag");
    }

    @Override
    public Function oneAndOnly() {
        return new GeneralBagFunction("urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only");
    }

    @Override
    public DataType supportedType() {
        return DataType.DATE;
    }
}
