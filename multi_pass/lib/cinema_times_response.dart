
class CinemaTimesResponse {
  List<Listing> listings;

  CinemaTimesResponse(this.listings);

  factory CinemaTimesResponse.fromJson(Map<String, dynamic> parsedJson) {
    var listings = parsedJson['listings'];
    var list = new List<Listing>.from(listings.map((value) => Listing.fromJson(value)));
    return new CinemaTimesResponse(list);
  }
}

class Listing {
  String title;
  List<String> times;

  Listing(this.title, this.times);

  factory Listing.fromJson(Map<String, dynamic> parsedJson) {
    var times = parsedJson['times'];
    var list = new List<String>.from(times);
    return new Listing(parsedJson['title'], list);
  }
}
