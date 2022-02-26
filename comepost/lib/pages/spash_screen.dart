import 'package:ComePost/pages/routing_screen.dart';
import 'package:flutter/material.dart';

import 'package:splashscreen/splashscreen.dart';

class MySplashScreen extends StatefulWidget {
  @override
  _MySplashScreenState createState() => _MySplashScreenState();
}

class _MySplashScreenState extends State<MySplashScreen> {
  @override
  Widget build(BuildContext context) {
    return SplashScreen(
        seconds: 5,
        navigateAfterSeconds: RoutingScreen(),        
        title: Text(
          'Farmer is the only man in our economy who buys everything at retail, sells everything at wholesale, and pays the freight both ways.',
          textAlign: TextAlign.center,
          style: TextStyle(fontStyle: FontStyle.italic, fontSize: 20.0),
        ),
        image: Image.asset('assets/logo.png', height: 180),
        backgroundColor: Colors.white,
        styleTextUnderTheLoader: TextStyle(),
        photoSize: 100.0,
        loaderColor: Colors.blue);
  }
}
