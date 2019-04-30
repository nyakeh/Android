import 'dart:async';
import 'package:multi_pass/web/api.dart';
import 'package:multi_pass/movie_details_response.dart';
import 'package:multi_pass/search_movies_response.dart';
import 'secrets.dart';

class MovieApi {
  final String _baseUrl = 'api.themoviedb.org';
  final String _apiKey = Secrets().getTheMovieDatabaseApiKey();

  Future<SearchMoviesResponse> searchMovies(String searchQuery) async {
    final uri = Uri.https(_baseUrl, '/3/search/movie', {'api_key': _apiKey, 'language': 'en-US', 'query': searchQuery, 'page': '1'});
    final api = Api();
    final jsonResponse = await api.getJson(uri);
    if (jsonResponse == null || jsonResponse['results'] == null) {
      print('Error searching movies for query \'$searchQuery\'.');
      return null;
    }

    SearchMoviesResponse searchResponse;
    searchResponse = SearchMoviesResponse.fromJson(jsonResponse);
    return searchResponse;
  }

  Future<MovieDetailsResponse> getMovieDetails(String movieId) async {
    final uri = Uri.https(_baseUrl, '/3/movie/$movieId', {'api_key': _apiKey, 'language': 'en-US'});
    final jsonResponse = await Api().getJson(uri);
    if (jsonResponse == null || jsonResponse['title'] == null) {
      print('Error retrieving movie details for movie id \'$movieId\'.');
      return null;
    }

    MovieDetailsResponse movieShowings;
    movieShowings = MovieDetailsResponse.fromJson(jsonResponse);
    return movieShowings;
  }
}
