package jetbrains.rsynk.command

import java.io.InputStream
import java.io.OutputStream

class RsyncCommand() : SSHCommand {
  override fun execute(input: InputStream, output: OutputStream, error: OutputStream) {
    throw UnsupportedOperationException("not implemented")
  }
}