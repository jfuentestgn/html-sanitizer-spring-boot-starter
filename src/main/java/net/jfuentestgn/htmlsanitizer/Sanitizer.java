package net.jfuentestgn.htmlsanitizer;

import org.owasp.html.PolicyFactory;

/**
 * The type Sanitizer.
 */
public class Sanitizer {

    /**
     * The constant DEFAULT.
     */
    public static final String DEFAULT = "DEFAULT";
    /**
     * The constant BLOCKS.
     */
    public static final String BLOCKS = "BLOCKS";
    /**
     * The constant FORMATTING.
     */
    public static final String FORMATTING = "FORMATTING";
    /**
     * The constant IMAGES.
     */
    public static final String IMAGES = "IMAGES";
    /**
     * The constant LINKS.
     */
    public static final String LINKS = "LINKS";
    /**
     * The constant STYLES.
     */
    public static final String STYLES = "STYLES";
    /**
     * The constant TABLES.
     */
    public static final String TABLES = "TABLES";


    private final PoliciesRegistry registry;

    /**
     * Instantiates a new Sanitizer.
     *
     * @param registry the registry
     */
    public Sanitizer(PoliciesRegistry registry) {
        this.registry = registry;
    }

    /**
     * Sanitize string.
     *
     * @param name the name
     * @param html the html
     * @return string
     */
    public String sanitize(String name, String html) {
        PolicyFactory policy = this.registry.getPolicy(name);
        if (policy == null) {
            throw new IllegalArgumentException("Policy not found: " + name);
        }
        return policy.sanitize(html);
    }
}
