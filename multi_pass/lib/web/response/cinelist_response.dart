class CinelistResponse {
  List<CinelistListing> listings;

  CinelistResponse(this.listings);

  factory CinelistResponse.fromJson(Map<String, dynamic> parsedJson) {
    var listings = parsedJson['listings'];
    var list = new List<CinelistListing>.from(listings.map((value) => CinelistListing.fromJson(value)));
    return new CinelistResponse(list);
  }
}

class CinelistListing {
  String title;
  List<String> times;

  CinelistListing(this.title, this.times);

  factory CinelistListing.fromJson(Map<String, dynamic> parsedJson) {
    var times = parsedJson['times'];
    var list = new List<String>.from(times);
    return new CinelistListing(parsedJson['title'], list);
  }
}
