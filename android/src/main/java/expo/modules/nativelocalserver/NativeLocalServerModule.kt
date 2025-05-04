package expo.modules.nativelocalserver

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import java.net.URL
import java.io.File
import android.util.Log

class NativeLocalServerModule : Module() {
  private var server: SimpleHttpServer? = null
  // Each module class must implement the definition function. The definition consists of components
  // that describes the module's functionality and behavior.
  // See https://docs.expo.dev/modules/module-api for more details about available components.
  override fun definition() = ModuleDefinition {
    // Sets the name of the module that JavaScript code will use to refer to the module. Takes a string as an argument.
    // Can be inferred from module's class name, but it's recommended to set it explicitly for clarity.
    // The module will be accessible from `requireNativeModule('NativeLocalServer')` in JavaScript.
    Name("NativeLocalServer")

    // Sets constant properties on the module. Can take a dictionary or a closure that returns a dictionary.
    Constants(
      "PI" to Math.PI
    )

    // Defines event names that the module can send to JavaScript.
    Events("onChange")

    // Defines a JavaScript synchronous function that runs the native code on the JavaScript thread.
    Function("hello") {
      "Hello world! 👋"
    }

    // Defines a JavaScript function that always returns a Promise and whose native code
    // is by default dispatched on the different thread than the JavaScript runtime runs on.
    AsyncFunction("setValueAsync") { value: String ->
      // Send an event to JavaScript.
      sendEvent("onChange", mapOf(
        "value" to value
      ))
    }

    AsyncFunction("startServer") { documentPath: String ->
      Log.d("ExpoLocalServer", "Trying to start server... with path: $documentPath")

      return@AsyncFunction try {
        if (server == null) {
          val documentDir = File(documentPath)
          server = SimpleHttpServer(documentDir)
          server?.startServer()
          Log.d("ExpoLocalServer", "✅ Native: Server started on http://127.0.0.1:8080")
        } else {
          Log.d("ExpoLocalServer", "⚠️ Server already running.")
        }
        "http://127.0.0.1:8080"
      } catch (e: Exception) {
        Log.e("ExpoLocalServer", "❌ Failed to start server: ${e.localizedMessage}", e)
        ""
      }
    }

    AsyncFunction("stopServer") {
      Log.d("ExpoLocalServer", "🛑 Attempting to stop server...")

      try {
        server?.stopServer()
        server = null
        Log.d("ExpoLocalServer", "✅ Server stopped successfully.")
      } catch (e: Exception) {
        Log.e("ExpoLocalServer", "❌ Failed to stop server: ${e.localizedMessage}", e)
      }

      Unit
    }

    // Enables the module to be used as a native view. Definition components that are accepted as part of
    // the view definition: Prop, Events.
    View(NativeLocalServerView::class) {
      // Defines a setter for the `url` prop.
      Prop("url") { view: NativeLocalServerView, url: URL ->
        view.webView.loadUrl(url.toString())
      }
      // Defines an event that the view can send to JavaScript.
      Events("onLoad")
    }
  }
}
