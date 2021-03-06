package custis.easyabac.api.impl;

import custis.easyabac.api.core.PermissionCheckerInformation;
import custis.easyabac.api.core.call.callprocessor.MethodCallProcessor;
import custis.easyabac.api.core.call.callprocessor.MethodCallProcessorFactory;
import custis.easyabac.core.pdp.AuthService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Method Interceptor for dynamic methods like ensure*
 */
public class DynamicMethodInterceptor implements MethodInterceptor {

    private final Map<Method, MethodCallProcessor> callProcessors = new HashMap<>();
    private final PermissionCheckerInformation permissionCheckerInformation;
    private final AuthService authService;

    public DynamicMethodInterceptor(PermissionCheckerInformation permissionCheckerInformation, AuthService authService) {
        this.permissionCheckerInformation = permissionCheckerInformation;
        this.authService = authService;
        lookupMethods(permissionCheckerInformation);
    }

    private void lookupMethods(PermissionCheckerInformation permissionCheckerInformation) {
        if (permissionCheckerInformation.hasAuthorizationCalls()) {
            for (Method authorizationCall : permissionCheckerInformation.getAuthorizationCalls()) {
                callProcessors.put(authorizationCall, MethodCallProcessorFactory.createCallProcessor(permissionCheckerInformation, authorizationCall, authService));
            }
        }

    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] argumentsArray = invocation.getArguments();
        List<Object> arguments = Arrays.asList(argumentsArray);

        if (hasCustomCall(method)) {
            return callProcessors.get(method).execute(arguments);
        }

        return invocation.proceed();
    }

    private boolean hasCustomCall(Method method) {
        return callProcessors.containsKey(method);
    }
}
