import 'dart:async';
import 'package:multi_pass/web/api.dart';
import 'package:multi_pass/web/response/cinema_times_response.dart';
import 'package:multi_pass/web/cache.dart';

class CinemaApi {
  final String _baseUrl = 'api.cinelist.co.uk';
  final Cache<List<Listing>> cache;

  CinemaApi(this.cache);

  Future<List<Listing>> getMovieShowings(int dayOffset) async {
    var now = new DateTime.now();
    var cacheKey = 'getMovieShowings_' + new DateTime(now.year, now.month, now.day, now.hour).toString();
    if (cache.contains(cacheKey)) {
      return cache.get(cacheKey);
    }

    final uri = Uri.https(_baseUrl, '/get/times/cinema/10713', {'day': '$dayOffset'});
    final api = Api();
    final jsonResponse = await api.getJson(uri);
    if (jsonResponse == null || jsonResponse['listings'] == null) {
      print('Error retrieving movie listings.');
      return null;
    }

    CinemaTimesResponse movieShowings;
    movieShowings = CinemaTimesResponse.fromJson(jsonResponse);

    cache.put(cacheKey, movieShowings.listings);
    return movieShowings.listings;
  }
}
