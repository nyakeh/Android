package uk.co.nyakeh.projectkotlin

fun main(args: Array<String>) {
    println("Hello World")
    var name = "Joe"
    name = "Jack"
    val lastName = "Bloggs"
    var middleName : String = "Bloggs"

    var firstName : String
    firstName = "Johnny"
    println(name)

    addTwoNumbers(4,7)
    addTwoNumbers(first = 5,second = 2)
    addTwoNumbers(second = 3,first = 8)

    addNumbers(3,5,8,1)
}

fun addTwoNumbers(first: Int, second: Int): Int {
    return first + second
}

fun addNumbers(vararg numbers: Int): Int {
    return numbers.sum()
}