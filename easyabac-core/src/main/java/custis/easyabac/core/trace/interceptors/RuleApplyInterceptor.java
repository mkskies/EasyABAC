package custis.easyabac.core.trace.interceptors;

import custis.easyabac.core.trace.balana.BalanaTraceHandlerProvider;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.wso2.balana.cond.EvaluationResult;
import org.wso2.balana.cond.Expression;

import java.lang.reflect.Method;

public class RuleApplyInterceptor implements MethodInterceptor {

    private final Expression expression;

    public RuleApplyInterceptor(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        String methodName = method.getName();

        Object realResult = null;


        if (methodName.equals("evaluate")) {
            BalanaTraceHandlerProvider.get().onRuleExpressionStart(expression);
            realResult = invocation.proceed();
            BalanaTraceHandlerProvider.get().onRuleExpressionEnd((EvaluationResult) realResult);
        } else {
            realResult = invocation.proceed();
        }

        return realResult;

    }
}
