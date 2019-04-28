import 'dart:async';
import 'dart:convert' show json, utf8;
import 'dart:io';
import 'package:multi_pass/cinema_times_response.dart';

class Api {
  final HttpClient _httpClient = HttpClient();
  final String _cinemaBaseUrl = 'api.cinelist.co.uk';

  Future<List<Listing>> getMovieShowings() async {
    CinemaTimesResponse movieShowings;
    final uri = Uri.https(_cinemaBaseUrl, '/get/times/cinema/10713', {'day': '0'});
    final jsonResponse = await _getJson(uri);
    if (jsonResponse == null || jsonResponse['listings'] == null) {
      print('Error retrieving movie listings.');
      return null;
    }

    movieShowings = CinemaTimesResponse.fromJson(jsonResponse);

    return movieShowings.listings;
  }

  Future<Map<String, dynamic>> _getJson(Uri uri) async {
    try {
      final httpRequest = await _httpClient.getUrl(uri);
      final httpResponse = await httpRequest.close();

      if (httpResponse.statusCode != HttpStatus.OK) {
        return null;
      }

      final responseBody = await httpResponse.transform(utf8.decoder).join();
      return json.decode(responseBody);
    } on Exception catch (e) {
      print('$e');
      return null;
    }
  }
}
