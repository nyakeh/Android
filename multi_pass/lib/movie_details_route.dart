import 'package:flutter/material.dart';
import 'package:multi_pass/web/response/cinema_times_response.dart';
import 'package:multi_pass/web/cache.dart';
import 'package:multi_pass/web/movie_api.dart';
import 'package:multi_pass/web/response/search_movies_response.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:multi_pass/web/secrets.dart';

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
  static final Cache _cache = Cache<SearchMoviesResponse>();
  SearchMoviesResult _movieDetails = new SearchMoviesResult(null, null, '', '', null, null);

  Widget get movieProfile {
    return Container(
      padding: EdgeInsets.symmetric(vertical: 8.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          CachedNetworkImage(
            imageUrl: _movieDetails.posterPath,
          ),
          Container(
              padding: EdgeInsets.all(8.0),
              child: Text(
                '${widget.movieListing.title}',
                style: TextStyle(fontSize: 32.0),
              )),
          Container(
              padding: EdgeInsets.all(4.0),
              margin: EdgeInsets.all(4.0),
              decoration: BoxDecoration(border: BorderDirectional(top: BorderSide(color: Color.fromRGBO(26, 26, 26, .9)))),
              child: Text(
                '${widget.movieListing.times.join(", ")}',
                style: TextStyle(fontSize: 20.0, color: Colors.white70),
              )),
          Container(
            padding: EdgeInsets.all(4.0),
            margin: EdgeInsets.all(4.0),
            decoration: BoxDecoration(border: BorderDirectional(top: BorderSide(color: Color.fromRGBO(26, 26, 26, .9)))),
            child: Text(
              _movieDetails.overview,
              style: TextStyle(
                fontSize: 20.0,
              ),
            ),
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
    final api = MovieApi(_cache);
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
      body: SingleChildScrollView(
        child: Container(margin: EdgeInsets.all(_minimumPadding * 2), child: movieProfile),
      ),
    );
  }
}
