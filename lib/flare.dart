
import 'flare_platform_interface.dart';

class Flare{
  String sha256 = "";

  Flare(this.sha256);

  Future<bool> verify() async {
    try {
      final result = await FlarePlatform.instance.validateAppIntegrity(sha256);
      if (result == true) {
        print("App integrity verified");
        return true;
      } else {
        print("App integrity verification failed!");
        return false; 
      }
    } catch (e) {
      print('Error while validating app integrity: $e');
    }
  }
}
