import 'package:flutter/material.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:multi_pass/web/movie_showing_schedule.dart';
import 'package:multi_pass/web/cache.dart';
import 'package:multi_pass/web/movie_api.dart';
import 'package:multi_pass/web/response/get_movie_response.dart';
import 'package:multi_pass/web/response/search_movies_response.dart';

class MovieDetailsRoute extends StatefulWidget {
  final MovieShowingSchedule movieListing;

  MovieDetailsRoute(this.movieListing);

  @override
  _MovieDetailsRouteState createState() {
    return _MovieDetailsRouteState();
  }
}

class _MovieDetailsRouteState extends State<MovieDetailsRoute> {
  final double _minimumPadding = 5.0;
  static final Cache _searchMoviesCache = Cache<SearchMoviesResponse>();
  static final Cache _getMovieCache = Cache<GetMovieResponse>();
  SearchMoviesResult _movieDetails = new SearchMoviesResult(null, null, '', '', null, new List<String>());
  String _movieLink = "";

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
                '${widget.movieListing.times.map((showing) => showing.time).join(", ")}',
                style: TextStyle(fontSize: 20.0, color: Colors.white70),
              )),
          Container(
            padding: EdgeInsets.all(4.0),
            margin: EdgeInsets.all(4.0),
            decoration: BoxDecoration(border: BorderDirectional(top: BorderSide(color: Color.fromRGBO(26, 26, 26, .9)))),
            child: Text(
              '${_movieDetails.genres.join(" | ")}',
              style: TextStyle(fontSize: 14.0, color: Colors.white54),
            ),
          ),
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
    final api = MovieApi(_searchMoviesCache, _getMovieCache);
    final searchResponse = await api.searchMovies(movieTitle);
    if (searchResponse != null && searchResponse.totalResults > 0) {
      searchResponse.results.sort((a, b) => b.releaseDate.compareTo(a.releaseDate));
      setState(() {
        _movieDetails = searchResponse.results.first;
      });
      _retrieveMovieLink(searchResponse.results.first.id);
    }
  }

  Future<void> _retrieveMovieLink(int movieId) async {
    final api = MovieApi(_searchMoviesCache, _getMovieCache);
    final getResponse = await api.getMovie(movieId);
    if (getResponse != null) {
      setState(() {
        _movieLink = getResponse.imdbLink;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black87,
      appBar: AppBar(
        title: Text('${widget.movieListing.title}'),
        actions: <Widget>[
          IconButton(
            icon: Icon(Icons.public),
            tooltip: 'Open film in Imdb',
            onPressed: () {
              launch(_movieLink);
            },
          )
        ],
      ),
      body: SingleChildScrollView(
        child: Container(margin: EdgeInsets.all(_minimumPadding * 2), child: movieProfile),
      ),
    );
  }
}
