package net.jfuentestgn.htmlsanitizer


import spock.lang.Specification
import spock.lang.Unroll

class SanitizerSpec extends Specification {

    @Unroll
    def 'test prepackages policies'() {
        given:
        PoliciesRegistry registry = new PoliciesRegistry();
        registry.addPredefinedPolicies();
        Sanitizer sanitizer = new Sanitizer(registry);
        String html =
'''<p>Sit <b>amet</b> <a href="http://www.google.com">commodo</a> nulla facilisi nullam vehicula <span style="font-weight:bold">ipsum</span></p>
<table>
<tr>
<th>text</th>
<th>list</th>
</tr>
</table>'''
        when:
        String result = sanitizer.sanitize(name, html)
        then:
        result == expected

        where:
        name << [
            'DEFAULT',
            "BLOCKS",
            "FORMATTING",
            "IMAGES",
            "LINKS",
            "STYLES",
            "TABLES",
        ]
        expected << [
// DEFAULT
'''Sit amet commodo nulla facilisi nullam vehicula ipsum
textlist''',
// BLOCKS
'''<p>Sit amet commodo nulla facilisi nullam vehicula ipsum</p>
textlist''',
// FORMATTING
'''Sit <b>amet</b> commodo nulla facilisi nullam vehicula ipsum
textlist''',
// IMAGES
'''Sit amet commodo nulla facilisi nullam vehicula ipsum
textlist''',
// LINKS
'''Sit amet <a href="http://www.google.com" rel="nofollow">commodo</a> nulla facilisi nullam vehicula ipsum
textlist''',
// STYLES
'''Sit amet commodo nulla facilisi nullam vehicula ipsum
textlist''',
// TABLES
'''Sit amet commodo nulla facilisi nullam vehicula ipsum
<table><tbody><tr><th>text</th><th>list</th></tr></tbody></table>'''
        ]
    }
}
