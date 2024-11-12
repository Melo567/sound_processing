import 'dart:typed_data';

import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'sound_processing_method_channel.dart';

abstract class SoundProcessingPlatform extends PlatformInterface {
  /// Constructs a SoundProcessingPlatform.
  SoundProcessingPlatform() : super(token: _token);

  static final Object _token = Object();

  static SoundProcessingPlatform _instance = MethodChannelSoundProcessing();

  /// The default instance of [SoundProcessingPlatform] to use.
  ///
  /// Defaults to [MethodChannelSoundProcessing].
  static SoundProcessingPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [SoundProcessingPlatform] when
  /// they register themselves.
  static set instance(SoundProcessingPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<Float64List?> getFeatureMatrix({
    required List<double> signals,
    required int sampleRate,
    required int hopLength,
    required int nMels,
    required int fftSize,
    required int mfcc,
  });
}
