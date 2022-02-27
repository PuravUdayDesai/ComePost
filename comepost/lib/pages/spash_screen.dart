import 'package:ComePost/Utils/util.dart';
import 'package:ComePost/app_localization.dart';
import 'package:ComePost/pages/router.dart';
import 'package:ComePost/pages/routing_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';

import 'package:splashscreen/splashscreen.dart';

class CustomSplashScreen extends StatelessWidget {  
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        supportedLocales: [
          Locale('en', ''),
          Locale('hi', ''),
          Locale('gu', ''),
        ],
        theme: Util.theme,
        localizationsDelegates: [
          AppLocalizations.delegate,
          GlobalMaterialLocalizations.delegate,
          GlobalWidgetsLocalizations.delegate,
        ],
        title: "Come Post",
        debugShowCheckedModeBanner: false,
        home: MySplashScreen(),
        onGenerateRoute: MyRouter().routeSettings);
  }
}

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
          AppLocalizations.of(context).translate('splashTitle'),
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
