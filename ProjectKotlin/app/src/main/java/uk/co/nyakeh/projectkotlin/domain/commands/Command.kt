package uk.co.nyakeh.projectkotlin.domain.commands

public interface Command<T> {
    fun execute(): T
}