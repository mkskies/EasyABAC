package custis.easyabac.pdp;

import java.util.Collections;
import java.util.Map;

public class AuthResponse {

    private final Decision decision;
    private final Map<String, String> returnValues;
    private final String errorMsg;

    public AuthResponse(Decision decision, Map<String, String> returnValues) {
        this.decision = decision;
        this.returnValues = returnValues;
        this.errorMsg = null;
    }

    public AuthResponse(String errorMsg) {
        this.errorMsg = errorMsg;
        decision = Decision.INDETERMINATE;
        returnValues = Collections.emptyMap();
    }

    public Decision getDecision() {
        return decision;
    }

    public enum Decision {
        PERMIT(0), DENY(1), INDETERMINATE(2), NOT_APPLICABLE(3);

        private final int index;

        Decision(int index) {
            this.index = index;
        }

        public static Decision getByIndex(int index) {
            for (Decision decision : Decision.values()) {
                if (decision.index == index) {
                    return decision;
                }
            }
            return DENY;
        }
    }
}
