
import groovy.transform.ToString
import java.time.*
import java.time.format.*

/*
input:
"BookId","Title","Author","My Rating","Edition","Shelf","Year","My Review"

output:
*/

@ToString
class Info {
    def title
    def author
    def rating
    def year
    def review
}

def cleanNumber(s) {
    return s.replaceAll(/^"/, "").replaceAll(/"$/, "")
}

def cleanTitle(s) {
    return s.replaceAll(/:.*/, /: [subtitle]"/)
}

def getDataFromLine = { line -> 
    def tokens = line.trim().split(",")
    def title = cleanTitle(tokens[1])
    def author = tokens[2]
    def rating = cleanNumber(tokens[3])
    def year = cleanNumber(tokens[6])
    def review = tokens[7]

    new Info(title: title, author: author, rating: rating, year: year, review: review) 
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

def buildTokenFromRec = { rec ->
    def result = "[${rec.title},${rec.author},${rec.rating},${rec.year},${rec.review}],"
    return result
}

// def testInfo = new Info(year: 2022, month: 10, day: 1, amount: 5150)
// assert buildTokenFromRec(testInfo,123) == '[new Date(2022,10,1),5150,123],'

def NEW_LINE = "\n"

def buildToken = { infos ->
    return infos.collect { buildTokenFromRec(it) }.join(NEW_LINE)
}

final String SUBSTITUTION_TOKEN = "__DATA_ROWS"

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
def data = buildToken(infos)
writeFile(outputHtml, templateHtml, data)

println "Ready."
