package game.data

import java.util.*
import java.util.function.IntFunction

class SortedLinkedList<T : Comparable<T>?> : MutableList<T> {
    private val list: LinkedList<T> = LinkedList()

    override fun add(element: T): Boolean {
        var index = 0
        while (index < list.size && element!! >= list[index]) {
            index++
        }
        list.add(index, element)
        return true
    }

    override fun remove(element: T): Boolean {
        return list.remove(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return list.containsAll(elements)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        return list.addAll(elements)
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        return list.addAll(index, elements)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return list.removeAll(elements.toSet())
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        return list.retainAll(elements.toSet())
    }

    override fun clear() {
        list.clear()
    }

    override fun get(index: Int): T {
        return list[index]
    }

    override fun set(index: Int, element: T): T {
        return list.set(index, element)
    }

    override fun add(index: Int, element: T) {
        list.add(index, element)
    }

    override fun removeAt(index: Int): T {
        return list.removeAt(index)
    }

    override fun indexOf(element: T): Int {
        return list.indexOf(element)
    }

    override fun lastIndexOf(element: T): Int {
        return list.lastIndexOf(element)
    }

    override fun listIterator(): MutableListIterator<T> {
        return list.listIterator()
    }

    override fun listIterator(index: Int): MutableListIterator<T> {
        return list.listIterator(index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        return list.subList(fromIndex, toIndex)
    }

    override val size: Int
        get() = list.size

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    override operator fun contains(element: T): Boolean {
        return list.contains(element)
    }

    override fun iterator(): MutableIterator<T> {
        return list.iterator()
    }

    override fun <T : Any?> toArray(generator: IntFunction<Array<T>>?): Array<T> {
        return list.toArray(generator)
    }

}
