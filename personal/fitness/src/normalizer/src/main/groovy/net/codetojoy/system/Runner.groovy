
package net.codetojoy.system

import net.codetojoy.custom.Config

class Runner {

    def parser
    def outputHeader

    def Runner() {
        def config = new Config()
        parser = config.parser
        outputHeader = config.outputHeader
    }

    def buildInfos(def infile) {
        def infos = []
        def isHeader = true
        def header = ""

        new File(infile).eachLine { line ->
            if (isHeader) {
                isHeader = false
                header = line
            } else {
                def text = "${header}\n${line}\n"
                infos = parser.parseFile(text, infos)
            } 
        }
    
        return infos
    }

    def generateOutput(def infos) {
        println outputHeader
        infos.each { println it.toString() }
    }

    def run(def infile) {
        def infos = buildInfos(infile)
        generateOutput(infos)
    }

    def static void main(String[] args) {
        if (args.size() < 1) {
            println "Usage: groovy Runner.groovy infile"
            System.exit(-1)
        }

        def infile = args[0]
        assert new File(infile).exists() 

        new Runner().run(infile)
    }
}


