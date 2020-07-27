
@Grab(group='joda-time', module='joda-time', version='2.9.9')

import org.joda.time.*

def printDiff = { prefix, start, end ->
    def numDays = Days.daysBetween(start.toLocalDate(), end.toLocalDate()).getDays()
    println "${prefix} duration (# days) : " + numDays
}

// cars
printDiff("Miata", new DateTime(1996, 7, 3, 9, 00), new DateTime(2007, 5, 15, 9, 00))
printDiff("Civic Si", new DateTime(2007, 5, 15, 9, 00), new DateTime())

println ""

// phones
printDiff("Nokia", new DateTime(2003, 5, 1, 9, 00), new DateTime(2010, 6, 1, 9, 00))
printDiff("iPhone 4S", new DateTime(2011, 10, 1, 9, 00), new DateTime(2020, 7, 27, 9, 00))

println ""

// abodes
printDiff("Pheasant Run", new DateTime(1999, 11, 1, 9, 00), new DateTime(2009, 11, 1, 9, 00))
printDiff("Elm Towers", new DateTime(2010, 8, 1, 9, 00), new DateTime())

