import 'dart:async';
import 'package:flutter/material.dart';
import 'package:multi_pass/cinema_times_response.dart';
import 'api.dart';

class CinemaRoute extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _CinemaRouteState();
  }
}

class _CinemaRouteState extends State<CinemaRoute> {
  final _movieShowings = <Listing>[];
  final double _minimumPadding = 5.0;

  @override
  Future<void> didChangeDependencies() async {
    super.didChangeDependencies();
    if (_movieShowings.isEmpty) {
      await _retrieveApiCategory();
    }
  }

  Future<void> _retrieveApiCategory() async {
    final api = Api();
    final movieShowings = await api.getMovieShowings();
    if (movieShowings != null) {
      setState(() {
        _movieShowings.addAll(movieShowings);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('What\'s showing today'),
      ),

      body: Container(
        margin: EdgeInsets.all(_minimumPadding * 2),
        child: ListView(
          children: buildMovieListUI(_movieShowings),
        ),
      ),
    );
  }

  List<Widget> buildMovieListUI(List<Listing> movieShowings) {
    TextStyle textStyle = Theme.of(context).textTheme.title;

    var movies = <Widget>[];

    movieShowings.forEach((movieShowing) {
      movies.add(Row(children: <Widget>[
        Expanded(
            child: Padding(
          padding: EdgeInsets.all(_minimumPadding * 2),
          child: Text(
            movieShowing.title,
            style: textStyle,
          ),
        )),
        Container(
          width: _minimumPadding * 5,
        ),
        Expanded(
            child: Padding(
          padding: EdgeInsets.all(_minimumPadding * 2),
          child: Text(
            movieShowing.times.toString(),
            style: textStyle,
          ),
        )),
      ]));
    });

    return movies;
  }
}
