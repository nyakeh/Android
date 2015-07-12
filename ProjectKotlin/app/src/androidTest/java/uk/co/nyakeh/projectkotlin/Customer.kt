package uk.co.nyakeh.projectkotlin

public class Customer(customerId: Int) {

    val id = customerId;
    public val type: String = "Dealer"

    fun doSomething() {
    println("Some Code")}
}

data class Employee(val id:Int, val name: String)

fun method(args: Array<String>) {
    val customer1 = Customer(7)
    val customer2 = Customer(7)
    customer1.doSomething()

    val employee1 = Employee(3,"Steve")
    val employee2 = Employee(3,"Steve")
    println(employee1.name)

    println(customer1)
    println(employee1)

    println(customer1.equals(customer2))
    println(employee1.equals(employee2))
}