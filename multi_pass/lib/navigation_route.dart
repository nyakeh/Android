import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:multi_pass/cinema_route.dart';

class NavigationRoute extends StatefulWidget {
  NavigationRoute() : super();

  final String title = 'Multi Pass';

  @override
  _NavigationRouteState createState() => _NavigationRouteState();
}

class _NavigationRouteState extends State<NavigationRoute> {
  @override
  Widget build(BuildContext context) {
    var tabLabels = List<Tab>();
    var tabContents = List<CinemaRoute>();
    var today = new DateTime.now();
    for (var i = 0; i < 7; i++) {
      var day = today.add(new Duration(days: i));
      tabLabels.add(Tab(text: '${DateFormat('EEEE').format(day)}'));
      tabContents.add(CinemaRoute(i));
    }

    return DefaultTabController(
        length: 7,
        child: Scaffold(
          appBar: AppBar(
            title: Text(widget.title),
            bottom: TabBar(tabs: tabLabels, isScrollable: true),
          ),
          body: TabBarView(children: tabContents),backgroundColor: Color.fromRGBO(18, 18, 18, 1),
        ));
  }
}
