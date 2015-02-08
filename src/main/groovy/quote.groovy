package me.zhihan.quote

import groovy.transform.CompileStatic
import groovy.json.*

import java.net.URI
import java.util.List
import java.util.Map

@CompileStatic
enum SourceType {
    BOOK,
    MOVIE,
    SONG,
    TV,
    SPEECH,
    BLOG,
    PODCAST,
    UNKNOWN
}

@CompileStatic
class Source {
    SourceType sourceType
    String uriString

    URI uri() {
        new URI(uriString)
    }

    boolean equals(Object obj) {
        if (obj instanceof Source) {
            Source that = obj as Source
            if (!sourceType.equals(that.sourceType)){
                return false;
            }
            if (!uriString.equals(that.uriString)) {
                return false;
            }
            return true;
        } else {
            return false
        }
    }
}

/**
 * Quote 
 *
 * A small piece of text with source and tagging information. 
 */
@CompileStatic
class Quote {
    String text
    String person

    List<String> tags  // non-repetitive set
     
    Source source
    List<String> links // Links to related web pages 
 
    boolean containTag(String tag) {
        return tags.any{it.equals(tag) }
    }

    boolean containTagSub(String sub) {
        return tags.any{it.indexOf(sub) >= 0}
    }

    boolean addTag(String t) {
        if (tags.any{it.equals(t)}) {
            false
        } else {
            tags.add(t)
            true
        }
    }

    String toJson() {
        JsonOutput.toJson(this)
    }

    @Override
    boolean equals(Object other) {
        if (other instanceof Quote) {
            Quote o = other as Quote
            boolean result = false 
            if (text == null) {
                result = (o.text == null)
            } else {
                result = text.equals(o.text)
            }
            if (!result) return result

            if (person == null) {
                result = (o.person == null)
            } else {
                result = person.equals(o.person)
            }
            if (!result) return result

            if (source == null) {
                result = (o.source == null)
            } else {
                result = source.equals(o.source)
            }
            return result
        } else {
            false
        }
    }
}

/** 
  * Note this class should not be compiled as JsonSlurper uses the 
  * dynamic feature of Groovy. 
  */
class QuoteUtil {
    /** Create a Quote object from json string */
    static Quote fromJson(String json) {
        Object object = new JsonSlurper().parseText(json)
        new Quote(object)
    }
}
