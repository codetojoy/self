/*
All The Light We Cannot See (date unknown)
Endurance (March 2021)
*/

def regex = "(.*)\\((.*)\\).*"
def dateRegex = "(.*)(\\d\\d\\d\\d)"
def q = { s ->
    return '"' + s + '"'
}

new File("list.txt").eachLine{ def line -> 
    def trimLine = line.trim()
    if (trimLine) {
        def matcher = line =~ regex
        assert matcher.matches() 
        def title = matcher[0][1].trim()
        def date = matcher[0][2].trim()
        def dateMatcher = date =~ dateRegex

        if (dateMatcher.matches()) {
            def month = dateMatcher[0][1].trim()
            def year = dateMatcher[0][2] as int
            println "${q(title)},\"author\",${q(year)},${q(month)},${q('')}"
        } else {
            println "${q(title)},\"author\",${q('')},${q('')},${q(date)}"
        }
    }
}
