package custis.easyabac.core.init;

import custis.easyabac.core.cache.Cache;
import custis.easyabac.core.model.abac.AbacAuthModel;
import custis.easyabac.core.trace.PolicyElementsFactory;
import custis.easyabac.core.trace.Trace;
import org.wso2.balana.PDP;
import org.wso2.balana.finder.AttributeFinderModule;
import org.wso2.balana.finder.PolicyFinderModule;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BalanaPdpHandlerFactory implements PdpHandlerFactory {

    public static final PdpHandlerFactory DIRECT_INSTANCE = new BalanaPdpHandlerFactory(false);
    public static final PdpHandlerFactory PROXY_INSTANCE = new BalanaPdpHandlerFactory(true);

    private final boolean useProxy;

    public BalanaPdpHandlerFactory(boolean useProxy) {
        this.useProxy = useProxy;
    }

    @Override
    public boolean supportsXacmlPolicies() {
        return true;
    }

    @Override
    public PdpHandler newInstance(AbacAuthModel abacAuthModel, List<Datasource> datasources, Cache cache, Trace trace) {
        return getInstance(new EasyPolicyFinderModule(abacAuthModel), datasources, cache, trace, useProxy);
    }

    @Override
    public PdpHandler newXacmlInstance(InputStream policyXacml, List<Datasource> datasources, Cache cache, Trace trace) {
        return getInstance(new InputStreamPolicyFinderModule(policyXacml), datasources, cache, trace, useProxy);
    }

    public static PdpHandler getInstance(PolicyFinderModule policyFinderModule, List<Datasource> datasources, Cache cache, Trace trace, boolean useProxy) {
        Set<PolicyFinderModule> policyModules = new HashSet<>();
        policyModules.add(policyFinderModule);

        List<AttributeFinderModule> finderModules = new ArrayList<>();
        for (Datasource datasource : datasources) {
            finderModules.add(new DatasourceAttributeFinderModule(datasource, cache));
        }

        PDP pdp = PolicyElementsFactory.newPDP(policyModules, finderModules, useProxy);

        return new BalanaPdpHandler(pdp, trace);
    }

}
