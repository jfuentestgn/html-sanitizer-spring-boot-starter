package net.jfuentestgn.htmlsanitizer;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates a field from user input that should be sanitized to remove undesired o dangerous HTML content
 *
 * <h2>Usage</h2>
 *
 * <p>Add the annotation before any field to be sanitized. For example:</p>
 *
 * <pre class="code">
 *     &#064;Sanitize
 *     private String description;
 *
 *     &#064;Sanitize("LINKS")
 *     private String userInputThatAcceptsLinks;
 * </pre>
 *
 * <p>Annotation value can be used to customize the sanitizer policy. If not set, it defaults to <code>"DEFAULT"</code>.
 * Available values, all corresponding to the predefined OWASP policies, are:</p>
 * <ul>
 *     <li>DEFAULT (all HTMl elements are stripeed)</li>
 *     <li>BLOCKS</li>
 *     <li>FORMATTING</li>
 *     <li>IMAGES</li>
 *     <li>LINKS</li>
 *     <li>STYLES</li>
 *     <li>TABLES</li>
 *  </ul>
 *
 *  <p>To create a custom policy just add it to the {@link PoliciesRegistry} using the corresponding bean, and reference it by the given name</p>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sanitize {

    /**
     * Sanitize policy to be used
     *
     * @return Name of the policy, defaults to "DEFAULT"
     */
    String value() default "DEFAULT";
}
