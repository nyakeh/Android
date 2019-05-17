
class GetMovieResponse {
  String imdbLink;
  int runtime;

  GetMovieResponse(this.imdbLink, this.runtime);

  factory GetMovieResponse.fromJson(Map<String, dynamic> parsedJson) {
    var imdbLink = 'https://www.imdb.com/title/${parsedJson['imdb_id']}/';
    return new GetMovieResponse(imdbLink, parsedJson['runtime']);
  }
}
