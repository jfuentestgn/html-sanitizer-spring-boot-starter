package net.jfuentestgn.htmlsanitizer;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.Set;

/**
 * A factory that creates formatters to format values of fields annotated a {@link Sanitize} annotation.
 */
public class SanitizerFormatterFactory implements AnnotationFormatterFactory<Sanitize> {

    private static final Set<Class<?>> FIELD_TYPES = Set.of(String.class);

    private final Sanitizer sanitizer;

    /**
     * Constructor of the factory
     *
     * @param sanitizer The satinizer used by the formatter
     */
    public SanitizerFormatterFactory(Sanitizer sanitizer) {
        this.sanitizer = sanitizer;
    }


    @Override
    public Set<Class<?>> getFieldTypes() {
        return FIELD_TYPES;
    }

    @Override
    public Printer<String> getPrinter(Sanitize annotation, Class<?> fieldType) {
        return (text, locale) -> text;
    }

    @Override
    public Parser<String> getParser(Sanitize annotation, Class<?> fieldType) {
        return (text, locale) -> this.sanitizer.sanitize(annotation.value(), text);
    }
}
