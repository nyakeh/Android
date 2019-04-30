import 'package:flutter/material.dart';
import 'package:multi_pass/cinema_times_response.dart';
import 'package:multi_pass/web/movie_api.dart';
import 'package:multi_pass/search_movies_response.dart';

class MovieDetailsRoute extends StatefulWidget {
  final Listing movieListing;

  MovieDetailsRoute(this.movieListing);

  @override
  _MovieDetailsRouteState createState() {
    return _MovieDetailsRouteState();
  }
}

class _MovieDetailsRouteState extends State<MovieDetailsRoute> {
  final double _minimumPadding = 5.0;
  SearchMoviesResult _movieDetails = new SearchMoviesResult(null, null, '', null, null, null);

  Widget get movieProfile {
    return Container(
      padding: EdgeInsets.symmetric(vertical: 32.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Text(
            '${widget.movieListing.title}',
            style: TextStyle(fontSize: 32.0),
          ),
          Text(
            widget.movieListing.times.toString(),
            style: TextStyle(fontSize: 20.0),
          ),
          Text(
            _movieDetails.overview,
            style: TextStyle(fontSize: 26.0),
          ),
        ],
      ),
    );
  }

  @override
  Future<void> didChangeDependencies() async {
    super.didChangeDependencies();
    if (_movieDetails.id == null) {
      await _retrieveMovieDetails(widget.movieListing.title);
    }
  }

  Future<void> _retrieveMovieDetails(String movieTitle) async {
    final api = MovieApi();
    final searchResponse = await api.searchMovies(movieTitle);
    if (searchResponse != null && searchResponse.totalResults > 0) {
      searchResponse.results.sort((a, b) => b.releaseDate.compareTo(a.releaseDate));
      setState(() {
        _movieDetails = searchResponse.results.first;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    // This is a new page, so you need a new Scaffold!
    return Scaffold(
      backgroundColor: Colors.black87,
      appBar: AppBar(
        title: Text('${widget.movieListing.title}'),
      ),
      body: Container(
        margin: EdgeInsets.all(_minimumPadding * 2),
        child: movieProfile,
      ),
    );
  }
}
