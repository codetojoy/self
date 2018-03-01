
@Grab(group='joda-time', module='joda-time', version='2.9.9')

import org.joda.time.*

def getDuration = { def start, def end ->
    Days.daysBetween(start.toLocalDate(), end.toLocalDate()).getDays()
}

def s1 = new DateTime(1996, 7, 3, 9, 0)
def e1 = new DateTime(2007, 5, 15, 9, 0)
println "duration 1 in : " + getDuration(s1, e1)

def s2 = e1
def e2 = new DateTime()
println "duration 2 in : " + getDuration(s2, e2)

