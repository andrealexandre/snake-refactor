package snake.utils

import java.util.NoSuchElementException

sealed class FList<out A> {
    abstract val head: A
    abstract val tail: FList<A>

    // utils
    abstract fun getLast(): A
    abstract fun foreach(f: (A) -> Unit)
    abstract fun forall(f: (A) -> Boolean): Boolean
    abstract fun forone(function: (A) -> Boolean): Boolean

    // immutable methods
    abstract fun removeLast(): FList<A>
    abstract fun take(n: Int): FList<A>
    fun <B> mapIndexed(f: (Int, A) -> B): FList<B> = mapIndexed(0, f)
    abstract fun <B> mapIndexed(index: Int, f: (Int, A) -> B): FList<B>
    abstract fun <B> map(f: (A) -> B): FList<B>
}

data class FCons<A>(override val head: A,
                    override val tail: FList<A>) : FList<A>() {

    override fun getLast(): A = if (tail == FNil) head else tail.getLast()

    override fun foreach(f: (A) -> Unit) {
        f(head)
        tail.foreach(f)
    }

    override fun forall(f: (A) -> Boolean): Boolean = f(head) && tail.forall(f)

    override fun forone(f: (A) -> Boolean): Boolean = f(head) || tail.forone(f)

    override fun removeLast(): FList<A> =
            if (tail is FNil) FNil
            else FCons(head, tail.removeLast())

    override fun take(n: Int): FList<A> = if(n <= 0) FNil else FCons(head, tail.take(n - 1))

    override fun <B> mapIndexed(index: Int, f: (Int, A) -> B): FList<B> {
        return FCons(f(index, head), tail.mapIndexed(index + 1, f))
    }

    override fun <B> map(f: (A) -> B): FList<B> = FCons(f(head), tail.map(f))
}

object FNil : FList<Nothing>() {

    override val head: Nothing
        get() = throw NoSuchElementException("no element in this list")

    override val tail: FList<Nothing>
        get() = this

    override fun getLast(): Nothing = head

    override fun foreach(f: (Nothing) -> Unit) { }

    override fun forall(f: (Nothing) -> Boolean): Boolean = true

    override fun forone(function: (Nothing) -> Boolean): Boolean = false

    override fun removeLast(): FList<Nothing> = this

    override fun take(n: Int): FList<Nothing> = FNil

    override fun <B> mapIndexed(index: Int, f: (Int, Nothing) -> B): FList<B> = FNil

    override fun <B> map(f: (Nothing) -> B): FList<B> = FNil

    override fun toString(): String = "FNil"
}

operator fun <A> FList<A>.plus(elem: A): FList<A> = appendLast(elem)
operator fun <A> A.plus(list: FList<A>): FList<A> = list.append(this)

fun <A> FList<A>.append(elem: A): FList<A> = FCons(elem, this)
fun <A> FList<A>.appendLast(elem: A): FList<A> = when(this) {
    is FCons<A> -> FCons(head, tail.appendLast(elem))
    is FNil -> FCons(elem, this)
}
fun <A> FList<A>.insertBefore(elem: A, f: (A) -> Boolean): FList<A> = when(this) {
    is FCons<A> -> if(f(this.head)) FCons(elem, this) else this.tail.insertBefore(elem, f)
    is FNil -> FCons(elem, this)
}
fun <A> FList<A>.paddingIndexed(limit: Int, f: (Int) -> A): FList<A> = paddingIndexed(0, limit, f)
fun <A> FList<A>.paddingIndexed(index: Int, limit: Int, f: (Int) -> A): FList<A> {
    return if(limit <= 0) FNil
    else {
        val head = when(this) {
            is FCons<A> -> head
            is FNil -> f(index)
        }
        FCons(head, tail.paddingIndexed(index + 1, limit - 1, f))
    }
}