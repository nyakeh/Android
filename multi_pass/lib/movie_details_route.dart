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
    var imageUrl2 = Secrets().getTheMovieDatabaseImageBaseUrl() + _movieDetails.posterPath;
    return Container(
      padding: EdgeInsets.symmetric(vertical: 32.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          CachedNetworkImage(
            imageUrl: imageUrl2,
          ),
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
      body: Container(
        margin: EdgeInsets.all(_minimumPadding * 2),
        child: movieProfile,
      ),
    );
  }
}
