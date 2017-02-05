package jetbrains.rsynk

import java.io.InputStream
import java.net.Socket
import java.util.*


/**
 * NOTE:
 * This file helps understand rsync protocol
 * and will be deleted once work is done
 */

fun read(stream: InputStream): ByteArray {
  val available = stream.available()
  if (available == 0) {
    return byteArrayOf()
  }
  val bytes = ByteArray(available)
  val read = stream.read(bytes)
  println("Read $read bytes")
  return bytes
}

fun main(args: Array<String>) {
  val list = ArrayList<String>()
  Socket("localhost", 10364).use { socket ->

    // > write version
    socket.outputStream.write("@RSYNCD: 31.0\n".toByteArray())
    socket.outputStream.flush()

    // < read version
    val version = read(socket.inputStream)
    list.add(String(version))

    // > write desired module
    socket.outputStream.write("sandbox\n".toByteArray())
    socket.outputStream.flush()

    // < authentication
    val authStatus = String(read(socket.inputStream))
    list.add(authStatus)

    // > send args
    socket.outputStream.write("--server --sender -e.LsfxC . sandbox/File  \n".toByteArray())
    socket.outputStream.flush()

    // < read flags and checksum seed
    val flagsAndChecksum = String(read(socket.inputStream))
    list.add(flagsAndChecksum)

    // > write whaat?
    socket.outputStream.write("      ".toByteArray())
    socket.outputStream.flush()
    socket.outputStream.write(byteArrayOf(0,0,0,0))
    socket.outputStream.flush()

    for (i in 1..10) {
      val newLine = String(read(socket.inputStream))
      list.add(newLine)
    }
  }

  println(list)
}
