
package net.codetojoy.custom

import net.codetojoy.system.Parser

class Config {
    def parser
    def outputHeader = Info.getHeader()

    def Config() {
        def party = "PARTY_A"
        def infoMapper = new InfoMapper(party: party)
        parser = new Parser(infoMapper: infoMapper)
    }
}

