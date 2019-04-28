
class MovieDetailsResponse {
  String title;
  String overview;
  String posterPath;
  List<Genre> genres;

  MovieDetailsResponse(this.title, this.overview, this.posterPath, this.genres);

  factory MovieDetailsResponse.fromJson(Map<String, dynamic> parsedJson) {
    var genresJson = parsedJson['genres'];
    var genresList = new List<Genre>.from(genresJson.map((value) => Genre.fromJson(value)));
    return new MovieDetailsResponse(parsedJson['title'], parsedJson['overview'], parsedJson['poster_path'], genresList);
  }
}

class Genre {
  int id;
  String name;

  Genre(this.id, this.name);

  factory Genre.fromJson(Map<String, dynamic> parsedJson) {
    return new Genre(parsedJson['id'], parsedJson['name']);
  }
}
