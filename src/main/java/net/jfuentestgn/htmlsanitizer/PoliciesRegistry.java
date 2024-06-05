package net.jfuentestgn.htmlsanitizer;

import lombok.extern.slf4j.Slf4j;
import org.owasp.html.PolicyFactory;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.Sanitizers;

import java.util.HashMap;
import java.util.Map;

/**
 * Helps in the configuration of a named list of OWASP sanitizer policies
 */
@Slf4j
public class PoliciesRegistry {

    private final Map<String, PolicyFactory> policies = new HashMap<>();

    /**
     * Empty constructor
     */
    public PoliciesRegistry() {
    }

    /**
     * Adds OWASP predefined policies.
     */
    public void addPredefinedPolicies() {
        registerPolicy(Sanitizer.DEFAULT, new HtmlPolicyBuilder().toFactory());
        registerPolicy(Sanitizer.BLOCKS, Sanitizers.BLOCKS);
        registerPolicy(Sanitizer.FORMATTING, Sanitizers.FORMATTING);
        registerPolicy(Sanitizer.IMAGES, Sanitizers.IMAGES);
        registerPolicy(Sanitizer.LINKS, Sanitizers.LINKS);
        registerPolicy(Sanitizer.STYLES, Sanitizers.STYLES);
        registerPolicy(Sanitizer.TABLES, Sanitizers.TABLES);
    }


    /**
     * Registers given policy.
     *
     * @param name   Name that will identify this policy
     * @param policy the policy itself
     */
    public void registerPolicy(String name, PolicyFactory policy) {
        policies.put(name, policy);
    }

    /**
     * Retrieves a policy from the registry
     *
     * @param name the name of the policy
     * @return policy The policy, or null if not found
     */
    public PolicyFactory getPolicy(String name) {
        return policies.get(name);
    }


}
