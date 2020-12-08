package net.stouma915.bfinterpreter.interpreter

import java.util.Scanner

import net.stouma915.bfinterpreter.ascii.ASCIIConverter
import net.stouma915.bfinterpreter.exception.UnknownCharacterCodeException
import net.stouma915.bfinterpreter.memory.Memory

class Interpreter {
  private var loopCode = ""

  def run(string: String)(implicit memory: Memory): Option[String] = {
    var result: Option[String] = None
    string.toCharArray.foreach {
      case char: Char if loopCode.nonEmpty =>
        loopCode += char.toString
        val bracesCount = loopCode.toCharArray.filterNot(_ != '[').length
        val closedBracketsCount =
          loopCode.toCharArray.filterNot(_ != ']').length
        if (bracesCount == closedBracketsCount) {
          val interpreter = new Interpreter
          while (memory.getValue != 0) interpreter.run(
            loopCode.drop(1).dropRight(1)) match {
            case Some(string: String) if result.isEmpty =>
              result = Some(string)
            case Some(string: String) if result.nonEmpty =>
              result = Some(result.get.appendedAll(string))
            case None =>
          }
          loopCode = ""
        }
      case '+' =>
        memory.incrementValue()
      case '-' =>
        memory.decrementValue()
      case '>' =>
        if (!memory.increment) throw new OutOfMemoryError("これ以上インクリメントできません。")
      case '<' =>
        if (!memory.decrement) throw new OutOfMemoryError("これ以上デクリメントできません。")
      case '.' =>
        ASCIIConverter.convert(memory.getValue) match {
          case Some(char: Char) if result.isEmpty =>
            result = Some(char.toString)
          case Some(char: Char) if result.nonEmpty =>
            result = Some(result.get.appended(char))
          case None =>
            throw new UnknownCharacterCodeException(memory.getValue.toString)
        }
      case ',' =>
        println("入力が求められました:")
        print("> ")
        val scanner = new Scanner(System.in)
        var input: Option[Short] = None
        while (input.isEmpty) {
          if (scanner.hasNextLine) {
            scanner.nextLine().toShortOption match {
              case Some(short: Short) =>
                println(s"${short}を指定しました。")
                input = Some(short)
              case None =>
                println("数値を指定してください。")
                print("> ")
            }
          }
        }
        if (input.get < 0)
          for (_ <- 0 until math.abs(input.get)) memory.decrementValue()
        else
          for (_ <- 0 until input.get) memory.incrementValue()
      case '[' =>
        loopCode = "["
      case _ =>
    }
    result
  }
}
