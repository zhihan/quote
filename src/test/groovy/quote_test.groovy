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
}
