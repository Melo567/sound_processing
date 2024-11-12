package com.pierre.dev.sound.sound_processing

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

/** SoundProcessingPlugin */
class SoundProcessingPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  val jLibrosa: JLibrosa = JLibrosa()

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "sound_processing")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    when (call.method) {
      "getFeatureMatrix" -> {
        val values = call.argument<ArrayList<Double>>("values")
        val sampleRate = call.argument<Int>("sample_rate") ?: 0
        val mfcc = call.argument<Int>("mfcc") ?: 0
        val nFft = call.argument<Int>("n_fft") ?: 0
        val nMels = call.argument<Int>("n_mels") ?: 0
        val hopLength = call.argument<Int>("hop_length") ?: 0

        if (values != null) {
          result.success(getFeatureMatrix(values, sampleRate, mfcc, nFft, nMels, hopLength))
        } else {
          result.error("INVALID_ARGUMENT", "Values list is null", null)
        }
      }
      else -> result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  private fun getFeatureMatrix(
    values: ArrayList<Double>,
    sampleRate: Int,
    mfcc: Int,
    nFft: Int,
    nMels: Int,
    hopLength: Int
  ): DoubleArray {
    val magValues = FloatArray(values.size) { i -> values[i].toFloat() }

    val floatMatrix = jLibrosa.generateMFCCFeatures(
      magValues,
      sampleRate,
      mfcc,
      nFft,
      nMels,
      hopLength
    )

    val matrixLength = floatMatrix.size
    val matrixWidth = floatMatrix[0].size
    val framePredDaArray = DoubleArray(matrixWidth * matrixLength)

    for (i in 0 until matrixLength) {
      for (j in 0 until matrixWidth) {
        framePredDaArray[i + matrixLength * j] = floatMatrix[i][j].toDouble()
      }
    }

    return framePredDaArray
  }
}
