import 'dart:async';
import 'package:multi_pass/api.dart';
import 'package:multi_pass/cinema_times_response.dart';

class CinemaApi {
  final String _baseUrl = 'api.cinelist.co.uk';

  Future<List<Listing>> getMovieShowings() async {
    final uri = Uri.https(_baseUrl, '/get/times/cinema/10713', {'day': '0'});
    final api = Api();
    final jsonResponse = await api.getJson(uri);
    if (jsonResponse == null || jsonResponse['listings'] == null) {
      print('Error retrieving movie listings.');
      return null;
    }

    CinemaTimesResponse movieShowings;
    movieShowings = CinemaTimesResponse.fromJson(jsonResponse);
    return movieShowings.listings;
  }
}
