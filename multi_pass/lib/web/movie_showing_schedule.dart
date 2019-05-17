class MovieShowingSchedule {
  String title;
  List<MovieShowing> times;

  MovieShowingSchedule(this.title, this.times);
}

class MovieShowing {
  String time;
  String link;

  MovieShowing(this.time, this.link);
}
