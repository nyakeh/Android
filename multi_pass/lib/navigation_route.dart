import 'package:flutter/material.dart';
import 'package:multi_pass/cinema_route.dart';
import 'package:multi_pass/configuration_route.dart';

class NavigationRoute extends StatefulWidget {
  NavigationRoute() : super();

  final String title = 'What\'s showing today';

  @override
  _NavigationRouteState createState() => _NavigationRouteState();
}

class _NavigationRouteState extends State<NavigationRoute> {
  int currentTabIndex = 0;
  List<Widget> tabs = [
    CinemaRoute(),
    TabScreen(Colors.blue)
  ];
  onTapped(int index) {
    setState(() {
      currentTabIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: tabs[currentTabIndex],
      bottomNavigationBar: BottomNavigationBar(
        onTap: onTapped,
        currentIndex: currentTabIndex,
        items: [
          BottomNavigationBarItem(
            icon: Icon(Icons.local_movies),
            title: Text("Movies"),
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.account_circle),
            title: Text("Configuration"),
          )
        ],
      ),
    );
  }
}