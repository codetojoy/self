
@Grapes(
  @Grab(group='joda-time', module='joda-time', version='2.10.10')
)

import org.joda.time.format.*
import org.joda.time.*

def formatter = DateTimeFormat.forPattern("dd-MMM-yyyy")
def regex = /^"(.*?)".*$/

def isHeader = true

def processLine = { def line ->
    def matcher = line =~ regex
    assert matcher.matches()
    def dateStr = matcher[0][1]
    def dt = formatter.parseDateTime(dateStr)
    def year = dt.year().get()
    def weekNum = dt.getWeekOfWeekyear()
    println "year: ${year} week: ${weekNum}"
}

// ------------------ main 

new File(args[0]).eachLine { def line ->
    if (!isHeader) {
        processLine(line)
    } else {
        isHeader = false
    }
}

