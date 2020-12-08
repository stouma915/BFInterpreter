package net.stouma915.bfinterpreter.memory

import scala.collection.mutable

class Memory {
  private var index = 0
  private val memory = mutable.HashMap[Int, Short]()

  def increment: Boolean = {
    if (index >= Int.MaxValue) return false
    index += 1
    true
  }

  def decrement: Boolean = {
    if (index <= 0) return false
    index -= 1
    true
  }

  def incrementValue(): Unit = {
    val newValue = (oldValue: Short) =>
      (if (oldValue >= 255) 0 else oldValue + 1).toShort
    memory.update(index, newValue(memory.getOrElse(index, 0)))
  }

  def decrementValue(): Unit = {
    val newValue = (oldValue: Short) =>
      (if (oldValue <= 0) 255 else oldValue - 1).toShort
    memory.update(index, newValue(memory.getOrElse(index, 0)))
  }

  def getValue: Short = memory.getOrElse(index, 0)
}
