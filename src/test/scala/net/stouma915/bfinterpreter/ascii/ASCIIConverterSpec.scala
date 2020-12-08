package net.stouma915.bfinterpreter.ascii

import org.scalatest.diagrams.Diagrams
import org.scalatest.flatspec.AnyFlatSpec

class ASCIIConverterSpec extends AnyFlatSpec with Diagrams {
  "ASCIIConverter.convert" should "Convert Short to ASCII" in {
    assert(ASCIIConverter.convert(64) === Some('@'))
    assert(ASCIIConverter.convert(65) === Some('A'))
  }

  it should "Return None if failed" in {
    assert(ASCIIConverter.convert(1).getOrElse(None) === None)
    assert(ASCIIConverter.convert(0).getOrElse(None) === None)
  }
}
