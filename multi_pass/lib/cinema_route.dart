import 'dart:async';
import 'package:flutter/material.dart';
import 'package:multi_pass/web/find_any_film_api.dart';
import 'package:multi_pass/web/movie_showing_schedule.dart';
import 'package:multi_pass/web/cache.dart';
import 'movie_details_route.dart';

class CinemaRoute extends StatefulWidget {
  final int dayOffset;

  CinemaRoute(this.dayOffset);

  @override
  State<StatefulWidget> createState() {
    return _CinemaRouteState();
  }
}

class _CinemaRouteState extends State<CinemaRoute> {
  final _movieShowings = <MovieShowingSchedule>[];
  static final Cache _cache = Cache<List<MovieShowingSchedule>>();

  @override
  Future<void> didChangeDependencies() async {
    super.didChangeDependencies();
    if (_movieShowings.isEmpty) {
      await _retrieveMovieShowings();
    }
  }

  Future<void> _retrieveMovieShowings() async {
    final api = FindAnyFilmApi(_cache);
    final movieShowings = await api.getMovieShowings(this.widget.dayOffset);
    if (movieShowings != null) {
      setState(() {
        _movieShowings.addAll(movieShowings);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    ListTile makeMovieListingTile(MovieShowingSchedule movieListing) => ListTile(
          contentPadding: EdgeInsets.symmetric(horizontal: 20.0, vertical: 10.0),
          title: Text(
            movieListing.title,
            style: TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
          ),
          subtitle: Text(
            '${movieListing.times.join(", ")}',
            style: TextStyle(color: Colors.white70, fontWeight: FontWeight.normal),
          ),
          trailing: Icon(Icons.keyboard_arrow_right, color: Colors.white70, size: 30.0),
          onTap: () {
            Navigator.push(context, MaterialPageRoute(builder: (context) => MovieDetailsRoute(movieListing)));
          },
        );

    Card makeMovieListingCard(MovieShowingSchedule movieListing) => Card(
          elevation: 8.0,
          margin: new EdgeInsets.symmetric(horizontal: 10.0, vertical: 6.0),
          child: Container(
            decoration: BoxDecoration(color: Color.fromRGBO(26, 26, 26, .9)),
            child: makeMovieListingTile(movieListing),
          ),
        );

    return Container(
      child: ListView.builder(
        scrollDirection: Axis.vertical,
        shrinkWrap: true,
        itemCount: _movieShowings.length,
        itemBuilder: (BuildContext context, int index) {
          return makeMovieListingCard(_movieShowings[index]);
        },
      ),
    );
  }
}
