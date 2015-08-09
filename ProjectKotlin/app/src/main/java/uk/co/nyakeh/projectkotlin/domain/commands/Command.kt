package uk.co.nyakeh.projectkotlin.domain.commands

interface Command<T> {
    fun execute(): T
}