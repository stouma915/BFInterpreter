package net.stouma915.bfinterpreter.interpreter

import net.stouma915.bfinterpreter.memory.Memory
import org.scalatest.diagrams.Diagrams
import org.scalatest.flatspec.AnyFlatSpec

class InterpreterSpec extends AnyFlatSpec with Diagrams {
  private val interpreter = new Interpreter

  "Interpreter.run" should "Run Brainf*ck code" in {
    assert(
      interpreter.run(
        "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++.")(
        new Memory) === Some("A"))
    assert(
      interpreter.run("++++++[>++++++++++<-]>+++++.")(new Memory) === Some("A"))
  }
}
