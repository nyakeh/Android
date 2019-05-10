import 'dart:async';
import 'package:multi_pass/web/api.dart';
import 'package:multi_pass/web/response/cinelist_response.dart';
import 'package:multi_pass/web/movie_showing_schedule.dart';
import 'package:multi_pass/web/cache.dart';

class CinelistApi {
  final String _baseUrl = 'api.cinelist.co.uk';
  final Cache<List<MovieShowingSchedule>> cache;

  CinelistApi(this.cache);

  Future<List<MovieShowingSchedule>> getMovieShowings(int dayOffset) async {
    var now = new DateTime.now();
    var cacheKey = 'getMovieShowings_' + new DateTime(now.year, now.month, now.day).toString();
    if (cache.contains(cacheKey)) {
      return cache.get(cacheKey);
    }

    final uri = Uri.https(_baseUrl, '/get/times/cinema/10713', {'day': '$dayOffset'});
    final api = Api();
    final jsonResponse = await api.getJson(uri);
    if (jsonResponse == null || jsonResponse['listings'] == null) {
      print('Error retrieving movie listings from Cinelist.');
      return null;
    }

    CinelistResponse movieShowings;
    movieShowings = CinelistResponse.fromJson(jsonResponse);

    var movieShowingSchedule = new List<MovieShowingSchedule>();
    for (final listing in movieShowings.listings) {
      movieShowingSchedule.add(new MovieShowingSchedule(listing.title, listing.times));
    }

    cache.put(cacheKey, movieShowingSchedule);
    return movieShowingSchedule;
  }
}
