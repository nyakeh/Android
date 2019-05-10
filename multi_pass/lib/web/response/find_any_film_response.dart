class FindAnyFilmResponse {
  FindAnyFilmCinema cinema;

  FindAnyFilmResponse(this.cinema);

  factory FindAnyFilmResponse.fromJson(Map<String, dynamic> parsedJson) {
    var cinema = FindAnyFilmCinema.fromJson(parsedJson['10713']);
    return new FindAnyFilmResponse(cinema);
  }
}

class FindAnyFilmCinema {
  List<FindAnyFilmShowings> films;

  FindAnyFilmCinema(this.films);

  factory FindAnyFilmCinema.fromJson(Map<String, dynamic> parsedJson) {
    var filmsJson = parsedJson['films'];
    var films = new List<FindAnyFilmShowings>();
    for(final filmShowings in filmsJson.keys){
      var temp = FindAnyFilmShowings.fromJson(filmsJson[filmShowings]);
      films.add(temp);
    }
    //var temp = FindAnyFilmShowings.fromJson(filmsJson['162171']);
    //var films = new List<FindAnyFilmShowings>.from(filmsJson.map((value) => FindAnyFilmShowings.fromJson(value)));
    return new FindAnyFilmCinema(films);
  }
}

class FindAnyFilmShowings {
  List<FindAnyFilmShowing> showings;
  FindAnyFilmMovieData filmData;

  FindAnyFilmShowings(this.showings, this.filmData);

  factory FindAnyFilmShowings.fromJson(Map<String, dynamic> parsedJson) {
    var filmData = FindAnyFilmMovieData.fromJson(parsedJson['film_data']);
    var showingsJson = parsedJson['showings'];
    var showings = new List<FindAnyFilmShowing>.from(showingsJson.map((value) => FindAnyFilmShowing.fromJson(value)));
    return new FindAnyFilmShowings(showings, filmData);
  }
}

class FindAnyFilmShowing {
  String showtime;
  String link;

  FindAnyFilmShowing(this.showtime, this.link);

  factory FindAnyFilmShowing.fromJson(Map<String, dynamic> parsedJson) {
    return new FindAnyFilmShowing(parsedJson['display_showtime'], parsedJson['ticketing_link']);
  }
}

class FindAnyFilmMovieData {
  String title;

  FindAnyFilmMovieData(this.title);

  factory FindAnyFilmMovieData.fromJson(Map<String, dynamic> parsedJson) {
    return new FindAnyFilmMovieData(parsedJson['film_title']);
  }
}
