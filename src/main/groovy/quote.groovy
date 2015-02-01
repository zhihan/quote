package me.zhihan.quote

import java.util.List

class Quote {
	String text
	String person
	List<String> tags

	Boolean containTag(String tag) {
		return tags.any{it.equals(tag) }
	}

	Boolean containTagSub(String sub) {
		return tags.any{it.indexOf(sub) >= 0}
	}
}
