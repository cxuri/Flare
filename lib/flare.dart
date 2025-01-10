
import 'flare_platform_interface.dart';

class Flare{
  String sha256 = "";
  Future<void> main() async {
    try {
      final result = await FlarePlatform.instance.validateAppIntegrity(sha256);
      if (result == true) {
        print("App integrity verified");
      } else {
        print("App integrity verification failed!");
      }
    } catch (e) {
      print('Error while validating app integrity: $e');
    }
  }
}
