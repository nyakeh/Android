import 'dart:async';
import 'dart:convert' show json, utf8;
import 'dart:io';

class Api {
  final HttpClient _httpClient = HttpClient();

  Future<Map<String, dynamic>> getJson(Uri uri) async {
    try {
      final httpRequest = await _httpClient.getUrl(uri);
      final httpResponse = await httpRequest.close();
      if (httpResponse.statusCode != HttpStatus.OK) {
        return null;
      }

      final responseBody = await httpResponse.transform(utf8.decoder).join();
      return json.decode(responseBody);
    } on Exception catch (exception) {
      print('$exception');
      return null;
    }
  }
}
