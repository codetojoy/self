
@Grab(group='joda-time', module='joda-time', version='2.9.9')

import org.joda.time.*

def printDiff = { prefix, start, end ->
    def numDays = Days.daysBetween(start.toLocalDate(), end.toLocalDate()).getDays()
    println "${prefix} duration (# days) : " + numDays
    return numDays
}

final def NOW = new DateTime()
final def DATE_MAY_2007 = new DateTime(2007, 5, 15, 9, 00)
final def DATE_NOV_2024 = new DateTime(2024, 11, 11, 9, 00)
final def DATE_MAR_2025 = new DateTime(2025, 3, 23, 9, 00)

// cars
def miataNumDays = printDiff("Miata", new DateTime(1996, 7, 3, 9, 00), DATE_MAY_2007)
def hondaNumDays = printDiff("Civic Si", DATE_MAY_2007, DATE_NOV_2024)
def chevNumDays = printDiff("Chev", DATE_NOV_2024, NOW)

println ""

// car mileage

int miataDiff = 216000 - 53000
float miataRate = miataDiff / miataNumDays 
def miataStr = String.format("Miata mileage rate: %.2f miles per day", miataRate)
println miataStr 

int hondaDiff = 134400 - 11000
float hondaRate = hondaDiff / hondaNumDays 
def hondaStr = String.format("Honda mileage rate: %.2f miles per day", hondaRate)
println hondaStr 

println ""

// phones
printDiff("Nokia 3650", new DateTime(2003, 5, 1, 9, 00), new DateTime(2010, 6, 1, 9, 00))
printDiff("iPhone 4S", new DateTime(2011, 10, 1, 9, 00), new DateTime(2020, 7, 27, 9, 00))
printDiff("iPhone SE", new DateTime(2020, 7, 27, 9, 00), NOW)

println ""

// abodes
printDiff("Pheasant R", new DateTime(1999, 11, 1, 9, 00), new DateTime(2009, 11, 1, 9, 00))
printDiff("Elm T", new DateTime(2010, 8, 1, 9, 00), NOW)

println ""

// computers
printDiff("old MBP", new DateTime(2008, 4, 18, 9, 00), NOW)
printDiff("new MBP", new DateTime(2015, 5, 1, 9, 00), DATE_MAR_2025)
printDiff("M3 MBP", DATE_MAR_2025, NOW) 

