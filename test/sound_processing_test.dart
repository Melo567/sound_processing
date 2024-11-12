import 'package:flutter_test/flutter_test.dart';
import 'package:sound_processing/sound_processing.dart';
import 'package:sound_processing/sound_processing_platform_interface.dart';
import 'package:sound_processing/sound_processing_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';



void main() {
  final SoundProcessingPlatform initialPlatform = SoundProcessingPlatform.instance;

  test('$MethodChannelSoundProcessing is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelSoundProcessing>());
  });

  test('getPlatformVersion', () async {});
}
