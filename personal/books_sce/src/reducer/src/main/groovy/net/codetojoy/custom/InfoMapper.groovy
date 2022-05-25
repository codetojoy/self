
package net.codetojoy.custom

import net.codetojoy.utils.Utils

class InfoMapper {
    // "BookId","Title","Author","My Rating","Edition","Shelf","Year","My Review"
    static final def INDEX_TITLE = 1
    static final def INDEX_AUTHOR = 2
    static final def INDEX_RATING = 3
    static final def INDEX_YEAR = 6
    static final def INDEX_REVIEW = 7

    def utils = new Utils()

    def abbreviateTitle(s) {
        def result = s
        def maxLength = 30
        if (s.contains(":") && s.length() > maxLength) {
            def stem = s[0..maxLength].replaceAll(/"/, "")
            result = stem + " ..."
        }
        return result
    }

    def mapLine(def line) {
        def info = null

        try {
            def title = abbreviateTitle(line.getAt(INDEX_TITLE))
            def author = line.getAt(INDEX_AUTHOR)
            def rating = line.getAt(INDEX_RATING)
            def year = line.getAt(INDEX_YEAR)
            def review = line.getAt(INDEX_REVIEW)

            info = new Info(title: title, author: author, rating: rating,
                                year: year, review: review)
        } catch(Exception ex) {
            System.err.println("TRACER caught ex : ${ex.message}")
        }

        return info
    }
}

