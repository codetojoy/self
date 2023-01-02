
package net.codetojoy.custom

import net.codetojoy.utils.Utils

class InfoMapper {
    static final def INDEX_DATE = 0
    static final def INDEX_RUN = 1
    // static final def INDEX_BIKE = 2
    // static final def INDEX_SWIM = 3
    static final def INDEX_NOTES = 5
    static final def INDEX_WEIGHT = 6

    def party
    def utils = new Utils()

    def mapLine(def line) {
        def info = null

        try {
            def date = line.getAt(INDEX_DATE)
            def run = line.getAt(INDEX_RUN)
            def bike = 0
            def swim = 0
            def weight = line.getAt(INDEX_WEIGHT)
            def notes = line.getAt(INDEX_NOTES)

            info = new Info(date: date, run: run, bike: bike,
                            swim: swim, weight: weight, notes: notes)
        } catch(Exception ex) {
            System.err.println("TRACER caught ex : ${ex.message}")
        }

        return info
    }
}

