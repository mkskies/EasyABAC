package custis.easyabac.core.trace.model;

import custis.easyabac.core.model.abac.AbacAuthModel;
import custis.easyabac.pdp.RequestId;

import java.util.HashMap;
import java.util.Map;

/**
 * Trace result
 */
public class TraceResult implements Populatable {

    public static final TraceResult EMPTY = new TraceResult(null);

    private RequestId requestId;
    private AbstractCalculatedPolicy mainPolicy;
    private Map<String, CalculatedAttribute> attributes = new HashMap<>();

    public TraceResult(RequestId requestId) {
        this.requestId = requestId;
    }

    public void setMainPolicy(AbstractCalculatedPolicy mainPolicy) {
        this.mainPolicy = mainPolicy;
    }

    public void putAttribute(CalculatedAttribute calculatedAttribute) {
        CalculatedAttribute existing = attributes.get(calculatedAttribute.getId());
        if (existing == null) {
            attributes.put(calculatedAttribute.getId(), calculatedAttribute);
        } else {
            existing.addValues(calculatedAttribute.getValues());
        }
    }

    public RequestId getRequestId() {
        return requestId;
    }

    public AbstractCalculatedPolicy getMainPolicy() {
        return mainPolicy;
    }

    public Map<String, CalculatedAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "TraceResult{" +
                "requestId=" + requestId +
                ", mainPolicy=" + mainPolicy +
                ", attributes=" + attributes +
                '}';
    }

    @Override
    public void populateByModel(AbacAuthModel abacAuthModel) {
        if (mainPolicy == null) {
            return;
        }
        mainPolicy.populateByModel(abacAuthModel);
    }
}
