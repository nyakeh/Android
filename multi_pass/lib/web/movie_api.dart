import 'dart:async';
import 'package:multi_pass/web/api.dart';
import 'package:multi_pass/web/response/get_movie_response.dart';
import 'package:multi_pass/web/response/search_movies_response.dart';
import 'package:multi_pass/web/cache.dart';
import 'secrets.dart';

class MovieApi {
  final String _baseUrl = 'api.themoviedb.org';
  final String _apiKey = Secrets().getTheMovieDatabaseApiKey();
  final Cache<SearchMoviesResponse> _searchCache;
  final Cache<GetMovieResponse> _getCache;
  final Api _api = Api();

  MovieApi(this._searchCache, this._getCache);

  Future<SearchMoviesResponse> searchMovies(String searchQuery) async {
    var now = new DateTime.now();
    var cacheKey = 'searchMovies_$searchQuery' + new DateTime(now.year, now.month, now.day).toString();
    if (_searchCache.contains(cacheKey)) {
      return _searchCache.get(cacheKey);
    }

    final uri = Uri.https(_baseUrl, '/3/search/movie', {'api_key': _apiKey, 'language': 'en-US', 'query': searchQuery, 'page': '1'});
    final jsonResponse = await _api.getJson(uri);
    if (jsonResponse == null || jsonResponse['results'] == null) {
      print('Error searching movies for query \'$searchQuery\'.');
      return null;
    }

    var searchMoviesResponse = SearchMoviesResponse.fromJson(jsonResponse);

    _searchCache.put(cacheKey, searchMoviesResponse);
    return searchMoviesResponse;
  }

  Future<GetMovieResponse> getMovie(int theMovieDatabaseId) async {
    var now = new DateTime.now();
    var cacheKey = 'getMovie_$theMovieDatabaseId' + new DateTime(now.year, now.month, now.day).toString();
    if (_getCache.contains(cacheKey)) {
      return _getCache.get(cacheKey);
    }

    final uri = Uri.https(_baseUrl, '/3/movie/$theMovieDatabaseId', {'api_key': _apiKey, 'language': 'en-US'});
    final jsonResponse = await _api.getJson(uri);
    if (jsonResponse == null) {
      print('Error retreiving movie by id \'$theMovieDatabaseId\'.');
      return null;
    }

    var getMovieResponse = GetMovieResponse.fromJson(jsonResponse);

    _getCache.put(cacheKey, getMovieResponse);
    return getMovieResponse;
  }
}
