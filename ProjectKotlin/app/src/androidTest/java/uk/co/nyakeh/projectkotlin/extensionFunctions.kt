package uk.co.nyakeh.projectkotlin
// You can create you're own extension methods for any class or type!!
fun stuff(args: Array<String>) {
    var result = "This is my text".convertSpacerToUnderscores()
    println(result)
}

fun String.convertSpacerToUnderscores(): String {
    return this.replace(" ", "_")
}