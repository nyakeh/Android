import 'dart:async';
import 'package:intl/intl.dart';
import 'package:multi_pass/web/api.dart';
import 'package:multi_pass/web/movie_showing_schedule.dart';
import 'package:multi_pass/web/cache.dart';
import 'package:multi_pass/web/response/find_any_film_response.dart';

class FindAnyFilmApi {
  final String _baseUrl = 'findanyfilm.com';
  final Cache<List<MovieShowingSchedule>> cache;

  FindAnyFilmApi(this.cache);

  Future<List<MovieShowingSchedule>> getMovieShowings(int dayOffset) async {
    var today = new DateTime.now();
    var day = today.add(new Duration(days: dayOffset));
    var cacheKey = 'getMovieShowings_' + new DateTime(day.year, day.month, day.day).toString();
    if (cache.contains(cacheKey)) {
      return cache.get(cacheKey);
    }

    var formattedDay = new DateFormat('yyyy-MM-dd').format(day);
    final uri = Uri.http(_baseUrl, '/api/screenings/by_venue_id/venue_id/10713/date_from/$formattedDay');
    final api = Api();
    final jsonResponse = await api.getJson(uri);
    if (jsonResponse == null || jsonResponse['10713'] == null) {
      print('Error retrieving movie listings from Find Any Film.');
      return null;
    }

    FindAnyFilmResponse movieShowings;
    movieShowings = FindAnyFilmResponse.fromJson(jsonResponse);

    var movieShowingSchedule = new List<MovieShowingSchedule>();
    for (final film in movieShowings.cinema.films) {
      var showtimes = new List<MovieShowing>();
      for (final showing in film.showings) {
        showtimes.add(new MovieShowing(showing.showtime, showing.link));
      }
      movieShowingSchedule.add(new MovieShowingSchedule(film.filmData.title, showtimes));
    }

    cache.put(cacheKey, movieShowingSchedule);
    return movieShowingSchedule;
  }
}
