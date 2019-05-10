import 'package:intl/intl.dart';
import 'package:multi_pass/web/secrets.dart';

class SearchMoviesResponse {
  int page;
  int totalResults;
  List<SearchMoviesResult> results;

  SearchMoviesResponse(this.page, this.totalResults, this.results);

  factory SearchMoviesResponse.fromJson(Map<String, dynamic> parsedJson) {
    var resultsJson = parsedJson['results'];
    var resultsList = new List<SearchMoviesResult>.from(resultsJson.map((value) => SearchMoviesResult.fromJson(value)));
    return new SearchMoviesResponse(parsedJson['page'], parsedJson['total_results'], resultsList);
  }
}

class SearchMoviesResult {
  int id;
  String title;
  String overview;
  String posterPath;
  DateTime releaseDate;
  List<String> genres;

  SearchMoviesResult(this.id, this.title, this.overview, this.posterPath, this.releaseDate, this.genres);

  factory SearchMoviesResult.fromJson(Map<String, dynamic> parsedJson) {
    var genreIdsJson = parsedJson['genre_ids'];
    var genreIdsList = new List<int>.from(genreIdsJson);
    var genreMap = {
      28: "Action",
      12: "Adventure",
      16: "Animation",
      35: "Comedy",
      80: "Crime",
      99: "Documentary",
      18: "Drama",
      10751: "Family",
      14: "Fantasy",
      36: "History",
      27: "Horror",
      10402: "Music",
      9648: "Mystery",
      10749: "Romance",
      878: "Science Fiction",
      10770: "TV Movie",
      53: "Thriller",
      10752: "War",
      37: "Western",
    };
    var genresList = new List<String>();
    genresList.addAll(genreIdsList.map((id) => genreMap[id]));

    DateFormat format = new DateFormat("yyyy-MM-dd");
    var releaseDate = new DateTime(1972);
    RegExp regex = new RegExp("(\d{4}-\d{2}-\d{2})");
    if (regex.hasMatch(parsedJson['release_date'])) {
      releaseDate = format.parse(parsedJson['release_date']);
    }
    var posterImagePath = parsedJson['poster_path'] ?? '';
    var posterPath = Secrets().getTheMovieDatabaseImageBaseUrl() + posterImagePath;
    return new SearchMoviesResult(parsedJson['id'], parsedJson['title'], parsedJson['overview'], posterPath, releaseDate, genresList);
  }
}
