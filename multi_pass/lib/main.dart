import 'package:flutter/material.dart';
import 'package:multi_pass/cinema_route.dart';

void main() {
  runApp(MaterialApp(
    debugShowCheckedModeBanner: false,
    title: 'MultiPass',
    home: CinemaRoute(),
    theme: ThemeData(
        brightness: Brightness.dark,
        primaryColor: Colors.indigo,
        accentColor: Colors.indigoAccent),
  ));
}
