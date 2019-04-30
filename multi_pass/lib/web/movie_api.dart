import 'dart:async';
import 'package:multi_pass/web/api.dart';
import 'package:multi_pass/web/response/search_movies_response.dart';
import 'package:multi_pass/web/cache.dart';
import 'secrets.dart';

class MovieApi {
  final String _baseUrl = 'api.themoviedb.org';
  final String _apiKey = Secrets().getTheMovieDatabaseApiKey();
  final Cache<SearchMoviesResponse> _searchCache;

  MovieApi(this._searchCache);

  Future<SearchMoviesResponse> searchMovies(String searchQuery) async {
    var now = new DateTime.now();
    var cacheKey = 'searchMovies_$searchQuery' + new DateTime(now.year, now.month, now.day, now.hour).toString();
    if (_searchCache.contains(cacheKey)) {
      return _searchCache.get(cacheKey);
    }

    final uri = Uri.https(_baseUrl, '/3/search/movie', {'api_key': _apiKey, 'language': 'en-US', 'query': searchQuery, 'page': '1'});
    final api = Api();
    final jsonResponse = await api.getJson(uri);
    if (jsonResponse == null || jsonResponse['results'] == null) {
      print('Error searching movies for query \'$searchQuery\'.');
      return null;
    }

    SearchMoviesResponse searchResponse;
    searchResponse = SearchMoviesResponse.fromJson(jsonResponse);

    _searchCache.put(cacheKey, searchResponse);
    return searchResponse;
  }
}
