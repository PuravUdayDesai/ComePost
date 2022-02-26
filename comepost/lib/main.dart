import 'dart:io';

import 'package:ComePost/pages/spash_screen.dart';
import 'package:flutter/material.dart';

import 'package:flutter_localizations/flutter_localizations.dart';

import 'app_localization.dart';
import 'Utils/util.dart';

void main() {
  HttpOverrides.global = MyHttpOverrides();
  runApp(new MaterialApp(
    supportedLocales: [
      Locale('en', ''),
      Locale('hi', ''),
      Locale('gu', ''),
    ],
    localizationsDelegates: [
      AppLocalizations.delegate,
      GlobalMaterialLocalizations.delegate,
      GlobalWidgetsLocalizations.delegate,
    ],
    debugShowCheckedModeBanner: false,
    home: MySplashScreen(),
    theme: Util.theme,
    title: "Come Post",
  ));
}

class MyHttpOverrides extends HttpOverrides{
  @override
  HttpClient createHttpClient(SecurityContext context){
    return super.createHttpClient(context)
      ..badCertificateCallback = (X509Certificate cert, String host, int port)=> true;
  }
}