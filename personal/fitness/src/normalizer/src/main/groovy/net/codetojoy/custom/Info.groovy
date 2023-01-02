
package net.codetojoy.custom

import net.codetojoy.utils.Utils

class Info {
    def date = ""
    def run = ""
    def bike = ""
    def swim = ""
    def weight = 0
    def notes = ""

    static def utils = new Utils()

    static String getHeader() {
        utils.buildList(["Date","Run","Bike","Swim","Weight","Notes"])
    }

    String toString() {
        utils.buildList([date, run, bike, swim, weight, notes])
    }
}
