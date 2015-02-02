package me.zhihan.quote

import groovy.transform.CompileStatic

import java.util.List

@CompileStatic
class Quote {
	String text
	String person  
	String source
	List<String> tags 
	List<String> videos 
	List<String> links // Links to related web pages 
 
	Boolean containTag(String tag) {
		return tags.any{it.equals(tag) }
	}

	Boolean containTagSub(String sub) {
		return tags.any{it.indexOf(sub) >= 0}
	}

	Boolean addTag(String t) {
		if (tags.any{it.equals(t)}) {
			false
		} else {
			tags.add(t)
			true
		}
	}
}
