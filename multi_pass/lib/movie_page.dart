import 'package:flutter/material.dart';
import 'package:multi_pass/cinema_times_response.dart';

class MovieDetailPage extends StatefulWidget {
  final Listing movieListing;

  MovieDetailPage(this.movieListing);

  @override
  _MovieDetailPageState createState(){
    return _MovieDetailPageState();
  }
}

class _MovieDetailPageState extends State<MovieDetailPage> {
  final double _minimumPadding = 5.0;

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
        ],
      ),
    );
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
