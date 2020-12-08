package net.stouma915.bfinterpreter

import java.io.{BufferedReader, File, FileReader}

import net.stouma915.bfinterpreter.exception.UnknownCharacterCodeException
import net.stouma915.bfinterpreter.interpreter.Interpreter
import net.stouma915.bfinterpreter.memory.Memory

object BFInterpreter {
  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      println("使用方法: BFInterpreter ソースファイル")
      sys.exit(1)
    }
    val sourceFile = new File(args(0))
    if (!sourceFile.exists()) {
      println("ソースファイルが見つかりませんでした。")
      sys.exit(1)
    }
    var lines = List[String]()
    try {
      val bufferedReader = new BufferedReader(new FileReader(sourceFile))
      var line = ""
      while ({
        line = bufferedReader.readLine()
        line
      } != null) lines = lines.appended(line)
    } catch {
      case e: Exception =>
        e.printStackTrace()
        println("ソースファイルを読み込めませんでした。")
        sys.exit(0)
    }
    var result: Option[String] = None
    var isSuccess = true
    val interpreter = new Interpreter
    implicit val memory: Memory = new Memory
    try result = interpreter.run(lines.mkString(""))
    catch {
      case e: UnknownCharacterCodeException =>
        println(s"エラー: 不明な文字コード(${e.getMessage})です。")
        isSuccess = false
      case e: OutOfMemoryError =>
        println(s"エラー: ${e.getMessage}")
        isSuccess = false
    }
    if (isSuccess) {
      println("実行は正常に終了しました。")
      result match {
        case Some(string: String) =>
          println("")
          println(s"結果: $string")
        case None =>
      }
    } else println("異常終了しました。")
  }
}
