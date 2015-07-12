package uk.co.nyakeh.projectkotlin

fun stuff(args: Array<String>) {
    var result = "This is my text".convertSpacerToUnderscores()
    println(result)
}

fun String.convertSpacerToUnderscores(): String {
    return this.replace(" ", "_")
}