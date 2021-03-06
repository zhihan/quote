package me.zhihan.quote

import org.junit.Test
import org.junit.Assert

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*

class QuoteTest {
    @Test
    void testTag() {
        Quote q = new Quote(text: "whatever", tags: ["a", "b"])
        assertThat(q.containTag("a"), is(true))
        assertThat(q.containTag("c"), is(false))
    }
    
    @Test
    void testSubTag() {
        Quote q = new Quote(text: "whatever", tags: ["funny", "sincere"])
        assertThat(q.containTagSub("unny"), is(true))
        assertThat(q.containTagSub("fun"), is(true))
        assertThat(q.containTagSub("xxx"), is(false))
    }

    @Test
    void testAddTag() {
        Quote q = new Quote(text: "whatever", tags: ["a", "b"])
        Boolean isNew = q.addTag("a")
        assertThat(isNew, equalTo(false))
        assertThat(q.getTags().size(), equalTo(2))
        isNew = q.addTag("c")
        assertThat(isNew, equalTo(true))
        assertThat(q.getTags().size(), equalTo(3))
    }

    @Test
    void testToJson() {
        Quote q = new Quote(text: "whatever", tags: ["a", "b"])
        String jsonText = q.toJson()
        assertThat(jsonText.size(), greaterThan(10))
    }

    @Test
    void testFromJson() {
        Quote q = new Quote(text: "whatever", tags: ["a", "b"])
        String jsonText = '{"text": "whatever", "tags": ["a", "b"]}'
        Quote x = QuoteUtil.fromJson(jsonText)
        println(x.toJson())
        assertThat(q, equalTo(x))
    }

    @Test
    void testEnumSerialization() {
        Source source = new Source(sourceType: SourceType.SONG, 
            uriString: "http://example.com")
        Quote q = new Quote(text: "let it be.", tags:["song"], source: source)
        String jsonText = q.toJson()
        
        Quote q2 = QuoteUtil.fromJson(jsonText)
        assertThat(q, equalTo(q2))
        assertThat(q2.source.sourceType, equalTo(SourceType.SONG))
    }

    @Test
    void testLoadFromJson() {
        URL url = ClassLoader.getSystemClassLoader().getResource("test_data1.json")
        List<Quote> l = QuoteUtil.loadList(url)
        assertThat(l.size(), equalTo(1))

        Quote x = l[0]
        assertThat(x.text, equalTo("The truth is out there."))
        assertThat(x.person, equalTo(null))
        assertThat(x.links, equalTo(
            ["http://en.wikipedia.org/wiki/The_X-Files"]))
        assertThat(x.source.sourceType, 
            equalTo(SourceType.TV))
    }
}
