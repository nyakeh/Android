import 'dart:async';
import 'package:flutter/material.dart';
import 'package:multi_pass/web/response/cinema_times_response.dart';
import 'package:multi_pass/web/cache.dart';
import 'movie_details_route.dart';
import 'package:multi_pass/web/cinema_api.dart';

class CinemaRoute extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _CinemaRouteState();
  }
}

class _CinemaRouteState extends State<CinemaRoute> {
  final _movieShowings = <Listing>[];
  static final Cache _cache = Cache<List<Listing>>();

  @override
  Future<void> didChangeDependencies() async {
    super.didChangeDependencies();
    if (_movieShowings.isEmpty) {
      await _retrieveMovieShowings();
    }
  }

  Future<void> _retrieveMovieShowings() async {
    final api = CinemaApi(_cache);
    final movieShowings = await api.getMovieShowings();
    if (movieShowings != null) {
      setState(() {
        _movieShowings.addAll(movieShowings);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    ListTile makeMovieListingTile(Listing movieListing) => ListTile(
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

    Card makeMovieListingCard(Listing movieListing) => Card(
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
