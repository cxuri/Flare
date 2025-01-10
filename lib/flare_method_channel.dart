
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flare_platform_interface.dart';

/// An implementation of [FlarePlatform] that uses method channels.
class MethodChannelFlare extends FlarePlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flare');

  @override

  Future<bool?> validateAppIntegrity(String sha256) async {
    final valid = await methodChannel.invokeMethod<bool>('validateAppIntegrity', {'sha': sha256});
    return valid;
  } 

}
