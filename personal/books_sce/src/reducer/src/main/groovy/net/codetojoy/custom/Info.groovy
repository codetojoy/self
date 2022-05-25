
package net.codetojoy.custom

import net.codetojoy.utils.Utils

class Info {
    // "BookId","Title","Author","My Rating","Edition","Shelf","Year","My Review"
    def title = ""
    def author = ""
    def rating = ""
    def year = ""
    def review = ""

    static def utils = new Utils()

    static String getHeader() {
        utils.buildList(["Title", "Author", "Rating", "Year", "Review"])
    }

    String toString() {
        utils.buildList([title, author, rating, year, review])
    }
}
