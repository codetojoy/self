
import groovy.transform.ToString
import java.time.*
import java.time.format.*

/*
input:
02-MAR-2015,162,28,5.79,1.64,""

output:
[new Date(2015,2,2),162],
*/

@ToString
class Info {
    def day
    def month
    def year
    def amount
}

def getDataFromLine = { line -> 
    def tokens = line.trim().split(",")
    def formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
                                                  .appendPattern("dd-MMM-yyyy")
                                                  .toFormatter()
    def date = LocalDate.parse(tokens[0], formatter)
    def day = date.dayOfMonth
    def month = date.monthValue - 1
    def year = date.year
    def amount = tokens[1]

    new Info(day: day, month: month, year: year, amount: amount)
}

def getDataFromFile = { file -> 
    def result = []
    def isHeader = true
    file.eachLine { line -> 
        if (!isHeader) {
            result << getDataFromLine(line)
        }
        isHeader = false
    }
    return result
}

def buildTokenFromRec = { rec, avg ->
    def result = "[new Date(${rec.year},${rec.month},${rec.day}),${rec.amount},${avg}],"
    return result
}

def testInfo = new Info(year: 2022, month: 10, day: 1, amount: 5150)
assert buildTokenFromRec(testInfo,123) == '[new Date(2022,10,1),5150,123],'

def NEW_LINE = "\n"

def buildToken = { infos, avg ->
    return infos.collect { buildTokenFromRec(it,avg) }.join(NEW_LINE)
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

def getAverage = { infos ->
    def count = infos.size()
    def total = infos.sum { it.amount as int }
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
def average = getAverage(infos)
def data = buildToken(infos, average)
writeFile(outputHtml, templateHtml, data)

println "Ready."
