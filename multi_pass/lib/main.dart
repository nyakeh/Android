import 'package:flutter/material.dart';
import 'package:multi_pass/navigation_route.dart';

void main() {
  runApp(MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'MultiPass',
      home: NavigationRoute(),
      theme: ThemeData(
          brightness: Brightness.dark,
          primaryColor: Color.fromRGBO(51, 41, 64, 1),
          accentColor: Color.fromRGBO(31, 27, 36, 1))
      ));
  }