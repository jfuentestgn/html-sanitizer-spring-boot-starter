# HTML Sanitizer Spring Boot Starter

HTML Sanititizer Starter for Spring Boot Applications

This starter makes it really easy to sanitize user input in Spring Boot Web Applications using the [OWASP Java HTML Sanitizer](https://owasp.org/www-project-java-html-sanitizer/). Simply add a @Sanitize annotation to DTO classes

![WIP - Work in progress](https://img.shields.io/badge/WIP-Work_in_progress-8A2BE2)

## Installation

![WARN - Not yet published](https://img.shields.io/badge/WARN-Not_yet_published-red)

Install `html-sanitizer-spring-boot-starter` with maven:
```
<dependency>
    <groupId>net.jfuentestgn</groupId>
    <artifactId>html-sanitizer-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```
or with gradle:
```
implementation 'net.jfuentestgn:html-sanitizer-spring-boot-starter:1.0.0'
```

## Usage/Examples

Supose you have a `BookController` with CRUD operations on a book collection. For the `create` action, you model a form with two fields: `title` and `description`.

In the BookController you will have something similar to this:

```java
@RequestMapping('/books')
@Controller
public class BookController {

    // Constructor and other methods ...

    @PostMapping('create')
    public RedirectView createFormSubmit(CreateBookForm createBookForm) {
        // extract title and description from createBookForm
        // do whatever with your database
        // redirect to the books list
    }

    // ... and more methods here ...
}
```

Of course, you need a `CreateBookForm` class to hold the two parameters: `title` and `description`:

```java
public class CreateBookForm {

    private String title;
    private String description;

    // Constructor, getters and setters
}
```

If you are concerned about security and XSS, you will see that this code is prone to attacks. Therefore, before using the `title` and `description` properties from the `CreateBookForm`, you have to filter them and remove all possible malicious code.

Using the HTML Sanitizer, you can achieve this by just adding a @Sanitize annotation to every property you want to filter. Just do this:

```java
public class CreateBookForm {

    @Sanitize
    private String title;
    @Sanitize
    private String description;

    // Constructor, getters and setters
}
```

Now, when Spring binds the form parameters to an instance of the `CreteBookForm` class, it will filter user input and strip out all HTML elements.

But... Maybe you want to allow users to add some formatting HTML code in the `description` field. With the previous example, all HTML would be deleted.

In that case, you can add an attribute to the annotation, specifying which sanitize policy must be used. So, the code will be:

```java
public class CreateBookForm {

    @Sanitize
    private String title;
    @Sanitize(Sanitizer.FORMATTING)
    private String description;

    // Constructor, getters and setters
}
```

### Available sanitize policies

All predefined [OWASP policies](https://github.com/OWASP/java-html-sanitizer/blob/master/src/main/java/org/owasp/html/Sanitizers.java) are available using `Sanitizer` constants as in the example. These are:

| Constant name | Description | Example |
| ------------- | ----------- | ------- |
| DEFAULT    | No HTML elements allowed                                       | @Sanitize                       |
| BLOCKS     | Allows common block elements including `<p>`, `<h1>`, etc      | @Sanitize(Sanitizer.BLOCKS)     |
| FORMATTING | Allows common formatting elements including `<b>`, `<i>`, etc  | @Sanitize(Sanitizer.FORMATTING) |
| IMAGES     | Allows `<img>` elements from HTTP, HTTPS, and relative sources | @Sanitize(Sanitizer.IMAGES      |
| LINKS      | Allows HTTP, HTTPS, MAILTO, and relative links                 | @Sanitize(Sanitizer.LINKS)      |
| STYLES     | Allows certain safe CSS properties in `style="..."` attributes | @Sanitize(Sanitizer.STYLES)     |
| TABLES     | Allows common table elements                                   | @Sanitize(Sanitizer.TABLES)     |


### Using custom policies

If none of the predefined policies fit your use case, you can register any number of custom policies using the `PoliciesRegistry` bean. To define a new policy, just use the OWASP `PolicyFactory` class.

#### Example
```java
@Configuration
public class CustomPolicyConfiguration {

    @Autowired
    public void configureSanitizers(PoliciesRegistry policiesRegistry) {
        PolicyFactory policy = new HtmlPolicyBuilder()
                .allowElements("a")
                .allowUrlProtocols("https")
                .allowAttributes("href").onElements("a")
                .requireRelNofollowOnLinks()
                // Add all requirements of the custom policy
                .toFactory();
        policiesRegistry.registerPolicy("CUSTOM_POLICY", policy);
    
}
```

Once the custom policy is registered, it can be used to filter any user input using its name in the @Sanitize annotation:
```java
public class CustomForm {
    @Sanitize("CUSTOM_POLICY")
    private String fieldThatMustBeFiltered;
}
```

### Sanitizing data without annotation

If you don't want to use the `@Sanitize` annotation or can't use it because of use case requirements, you can sanitize user
input directly through the `Sanitizer` Bean. Just inject it into the controller or service class and call the `sanitize` method.

```java
@Controller
public TestController {

    // Sanitizer bean used to clean user input
    private final Sanitizer sanitizer;

    public TestController(Sanitizer sanitizer) {
        this.sanitizer = sanitizer;
    }

    ... code ...

    @PostMapping("/submitForm")ç
    public String submitForm(TheForm theForm) {
        String description = theForm.getDescription();
        // Of course, use any policiy you want, in this case, the CUSTOM_POLICY is just an example
        String cleaned = this.sanitizer.sanitize("CUSTOM_POLICY", description);
        // User cleaned value as needed
    }
}
```
## Demo

You can see the starter in action in the [Demo Application]()

## Authors

- Juan Fuentes <jfuentestgna@gmail.com>

## License

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)

This package is licensed under the [MIT License](https://choosealicense.com/licenses/mit/)

## Acknowledgements

This project is just a spring-boot autoconfiguration on the [Java HTML Sanitizer](https://owasp.org/www-project-java-html-sanitizer/) maintained by the [OWASP Foundation](https://owasp.org/)

I want to thank the OWASP crew for their great job!!
