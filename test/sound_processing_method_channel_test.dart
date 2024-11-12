import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:sound_processing/sound_processing_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelSoundProcessing platform = MethodChannelSoundProcessing();
  const MethodChannel channel = MethodChannel('sound_processing');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });

  test('getPlatformVersion', () async {

  });
}
