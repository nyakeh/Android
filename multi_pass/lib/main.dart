import 'dart:io';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:multi_pass/api.dart';

void main() {
  runApp(MaterialApp(
    debugShowCheckedModeBanner: false,
    title: 'MultiPass',
    home: MyHomePage(),
    theme: ThemeData(
        brightness: Brightness.dark,
        primaryColor: Colors.indigo,
        accentColor: Colors.indigoAccent),
  ));
}

class MyHomePage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _HomePageFormState();
  }
}

class _HomePageFormState extends State<MyHomePage> {
//  var _movieShowings = [
//    Listing('Spiderman', ['12:00']),
//    Listing('LEGO', ['14:30', '16:00'])
//  ];
  final _movieShowings = <Listing>[];
  final double _minimumPadding = 5.0;

  @override
  Future<void> didChangeDependencies() async {
    super.didChangeDependencies();
    // We have static unit conversions located in our
    // assets/data/regular_units.json
    // and we want to also obtain up-to-date Currency conversions from the web
    // We only want to load our data in once
    if (_movieShowings.isEmpty) {
      await _retrieveApiCategory();
    }
  }

  Future<void> _retrieveApiCategory() async {
    final api = Api();
    final movieShowings = await api.getMovieShowings();
    // If the API errors out or we have no internet connection, this category
    // remains in placeholder mode (disabled)
    if (movieShowings != null) {
      setState(() {
        _movieShowings.addAll(movieShowings);
      });
    }
  }
  @override
  Widget build(BuildContext context) {
    TextStyle textStyle = Theme
        .of(context)
        .textTheme
        .title;

    return Scaffold(
//			resizeToAvoidBottomPadding: false,
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
    TextStyle textStyle = Theme
        .of(context)
        .textTheme
        .title;

    var movies = <Widget>[];

    movieShowings.forEach((movieShowing) {
      movies.add(Row(
          children: <Widget>[
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

class CinemaTimesResponse {
  List<Listing> listings;

  CinemaTimesResponse(this.listings);

  factory CinemaTimesResponse.fromJson(Map<String, dynamic> parsedJson) {
    var listings = parsedJson['listings'];
    var list = new List<Listing>.from(
        listings.map((value) => Listing.fromJson(value)));
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
