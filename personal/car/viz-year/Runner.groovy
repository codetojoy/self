
import groovy.transform.ToString
import java.time.*
import java.time.format.*

/*
input:
01-MAR-2022,129279,217

output:
[new Date(2022,2,1),217,$avg],
*/

@ToString
class Info {
    def year
    def amount
}

def getDataFromLine = { line ->
    def tokens = line.trim().split(",")
    def formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
                                                  .appendPattern("dd-MMM-yyyy")
                                                  .toFormatter(Locale.ENGLISH)

    def date = LocalDate.parse(tokens[0], formatter)
    def effectiveDate = date.minusDays(1)
    def year = effectiveDate.year
    def amount = tokens[2] as int

    new Info(year: year, amount: amount)
}

def getDataFromFile = { file ->
    def infos = [:].withDefault { key -> new Info(year: key, amount: 0) }

    def isHeader = true
    file.eachLine { line ->
        if (!isHeader) {
            def info = getDataFromLine(line)
            def refInfo = infos[info.year]
            refInfo.amount += info.amount
        }
        isHeader = false
    }

    return infos
}

def buildTokenFromRec = { rec, averageInMiles ->
    def result = "[new Date(${rec.year},1,1),${rec.amount},${averageInMiles}],"
    return result
}

def testInfo = new Info(year: 2022, amount: 5150)
assert buildTokenFromRec(testInfo,123) == '[new Date(2022,1,1),5150,123],'

def NEW_LINE = "\n"

def buildToken = { infos, averageInMiles ->
    return infos.values().collect { buildTokenFromRec(it, averageInMiles) }.join(NEW_LINE)
}

final String SUBSTITUTION_TOKEN = "__CODETOJOY_DATA"

def writeFile = { outputFile, templateFile, infos ->
    outputFile.withWriter { writer ->
        templateFile.eachLine { line ->
            def isToken = line.trim() == SUBSTITUTION_TOKEN
            if (isToken) {
                writer.write(infos)
            } else {
                writer.write(line)
            }
            writer.write("\n")
        }
    }
}

def getAverageMiles = { infos ->
    def count = 0
    def total = 0
    infos.each { key, value ->
        total += value.amount
        count++
    }
    def result = total / count as int
    return result
}

// ---------- main

if (args.length < 3) {
    if (args.length >= 1 && args[0] == "test") {
        // no-op: run tests
        System.exit 0
    } else {
        System.err.println "usage: groovy Runner.groovy csv-file template-html output-html"
        System.exit -1
    }
}

def csvFile = new File(args[0])
def templateHtml = new File(args[1])
def outputHtml = new File(args[2])

assert csvFile.exists() && csvFile.isFile()
assert templateHtml.exists() && templateHtml.isFile()

def infos = getDataFromFile(csvFile)

def averageInMiles = getAverageMiles(infos)
println "TRACER average is: " + averageInMiles

def data = buildToken(infos, averageInMiles)
writeFile(outputHtml, templateHtml, data)

println "Ready."
