
package expo.modules.nativelocalserver

import android.util.Log
import fi.iki.elonen.NanoHTTPD
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class SimpleHttpServer(private val documentRoot: File) : NanoHTTPD(8080) {

  override fun serve(session: IHTTPSession): Response {
    val uri = session.uri.trimStart('/')
    val requestedFile = File(documentRoot, uri)

    return if (requestedFile.exists() && requestedFile.isFile) {
      try {
        val fileInputStream = FileInputStream(requestedFile)
        newChunkedResponse(Response.Status.OK, getMimeType(uri), fileInputStream)
      } catch (e: IOException) {
        Log.e("SimpleHttpServer", "Error serving file: ${e.localizedMessage}", e)
        newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "Internal Server Error")
      }
    } else {
      Log.w("SimpleHttpServer", "File not found: $uri")
      newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "File Not Found")
    }
  }

  private fun getMimeType(fileName: String?): String {
    if (fileName == null) return "application/octet-stream"

    return when {
      fileName.endsWith(".html", ignoreCase = true) -> "text/html"
      fileName.endsWith(".htm", ignoreCase = true) -> "text/html"
      fileName.endsWith(".js", ignoreCase = true) -> "application/javascript"
      fileName.endsWith(".css", ignoreCase = true) -> "text/css"
      fileName.endsWith(".png", ignoreCase = true) -> "image/png"
      fileName.endsWith(".jpg", ignoreCase = true) -> "image/jpeg"
      fileName.endsWith(".jpeg", ignoreCase = true) -> "image/jpeg"
      fileName.endsWith(".svg", ignoreCase = true) -> "image/svg+xml"
      fileName.endsWith(".json", ignoreCase = true) -> "application/json"
      else -> "application/octet-stream"
    }
  }

  fun startServer() {
    try {
      start(SOCKET_READ_TIMEOUT, false)
      Log.d("SimpleHttpServer", "✅ Server started on port 8080")
    } catch (e: IOException) {
      Log.e("SimpleHttpServer", "❌ Could not start server: ${e.localizedMessage}", e)
    }
  }

  fun stopServer() {
    stop()
    Log.d("SimpleHttpServer", "🛑 Server stopped")
  }
}
