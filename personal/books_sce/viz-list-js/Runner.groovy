
import groovy.transform.ToString
import java.time.*
import java.time.format.*

/*
input:
"Title","Author","Rating","Year","Review"

output:
 <tr>
            <td class="title">Daylight</td>
            <td class="author">David Baldacci</td>
            <td class="rating">3.0</td>
            <td class="year">2021</td>
            <td class="review"></td>
 </tr>

*/

def format = 
"""
<tr>
  <td class="title">%s</td>
  <td class="author">%s</td>
  <td class="rating">%s</td>
  <td class="year">%s</td>
  <td class="review">%s</td>
</tr>
"""

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

def deQuote(s) {
    return s.replaceAll(/"/, "")
}

def abbreviateTitle(s) {
    def result = s
    def maxLength = 40
    if (s.contains(":") && s.length() > maxLength) {
        result = s[0..maxLength]
    }
    return result
}

def abbreviateReview(s) {
    def result = s
    def maxLength = 60
    if (s.length() > maxLength) {
        def stem = s[0..maxLength]
        result = stem + " ..."
    }
    return result
}

def getDataFromLine = { line -> 
    def tmpTokens = line.trim().split(",")
    def tokens = tmpTokens.collect { deQuote(it) }
    def title = abbreviateTitle(tokens[0])
    def author = tokens[1]
    def rating = cleanNumber(tokens[2])
    def year = cleanNumber(tokens[3])
    def review = abbreviateReview(tokens[4])

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
    def result = String.format(format, rec.title, rec.author, rec.rating, rec.year, rec.review)
    return result
}

def buildToken = { infos ->
    return infos.collect { buildTokenFromRec(it) }.join("")
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
