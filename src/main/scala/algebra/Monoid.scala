package algebra

import scala.{ specialized => sp }

/**
 * A monoid is a semigroup with an identity. A monoid is a specialization of a
 * semigroup, so its operation must be associative. Additionally,
 * `op(x, id) == op(id, x) == x`. For example, if we have `Monoid[String]`,
 * with `op` as string concatenation, then `id = ""`.
 */
trait Monoid[@sp(Boolean, Byte, Short, Int, Long, Float, Double) A] extends Semigroup[A] {
  def id: A
}

object Monoid {
  @inline final def apply[A](implicit m: Monoid[A]): Monoid[A] = m

  /**
   * If an implicit `AdditiveMonoid[A]` exists, then it is converted to a plain
   * `Monoid[A]`.
   */
  @inline final def additive[A](implicit A: AdditiveMonoid[A]) = A.additive

  /**
   * If an implicit `MultiplicativeMonoid[A]` exists, then it is converted to a
   * plain `Monoid[A]`.
   */
  @inline final def multiplicative[A](implicit A: MultiplicativeMonoid[A]) = A.multiplicative

  /**
   * Return `a` appended to itself `n` times.
   */
  final def sumn[@sp(Boolean, Byte, Short, Int, Long, Float, Double) A](a: A, n: Int)(implicit A: Monoid[A]): A = {
    if (n > 0) Semigroup.sumn(a, n)
    else if (n == 0) A.id
    else throw new IllegalArgumentException("Repeated summation for monoids must have reptitions >= 0")
  }
}

/**
 * CMonoid represents a commutative monoid.
 * 
 * A monoid is commutative if for all x and y, x |+| y === y |+| x.
 */
trait CMonoid[@sp(Boolean, Byte, Short, Int, Long, Float, Double) A] extends Monoid[A]

object CMonoid {
  @inline final def apply[A](implicit ev: CMonoid[A]): CMonoid[A] = ev
  @inline final def additive[A](implicit A: AdditiveCMonoid[A]): CMonoid[A] =  A.additive
  @inline final def multiplicative[A](implicit A: MultiplicativeCMonoid[A]): CMonoid[A] = A.multiplicative
}
