

def regex = /.*\w+(And) .*/

def clean = { s ->
    def result = s
    def matcher = s =~ regex
    if (matcher.matches()) {
        def t = new StringBuilder(s)
        def start = matcher.start(1) as int
        result = t.replace(start, start + 3, "").toString()
    }
    return result.replace("\n", "")
}

// --------- main

def file = new File(args[0])

file.eachLine { line ->
    def newLine = clean(line)
    println newLine
}

