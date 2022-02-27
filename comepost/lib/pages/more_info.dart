import 'package:ComePost/pages/router.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';

import '../app_localization.dart';

class MoreInfo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
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
        title: "Come Post",
        debugShowCheckedModeBanner: false,
        home: MoreInfoScreen(),
        onGenerateRoute: MyRouter().routeSettings);
  }
}

class MoreInfoScreen extends StatefulWidget {
  @override
  _MoreInfoScreenState createState() => _MoreInfoScreenState();
}

class _MoreInfoScreenState extends State<MoreInfoScreen> {
  bool _isLoading = false;

  @override
  Widget build(BuildContext context) {
    return _isLoading
        ? Scaffold(body: CircularProgressIndicator())
        : Scaffold(
            appBar: AppBar(
              title: Text(
                  AppLocalizations.of(context).translate('transactionsTitle')),
              centerTitle: true,
              iconTheme: IconThemeData(color: Colors.black),
            ),
            body: Container(),
          );
  }
}
