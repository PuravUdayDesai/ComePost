import 'package:ComePost/pages/DisplayMap.dart';
import 'package:ComePost/pages/login.dart';
import 'package:ComePost/pages/login_composter.dart';
import 'package:ComePost/pages/login_farmer.dart';
import 'package:ComePost/pages/sct.dart';
import 'package:ComePost/pages/selection_screen_login.dart';
import 'package:ComePost/pages/selection_screen_signup.dart';
import 'package:ComePost/pages/signup_composter.dart';
import 'package:ComePost/pages/signup_farmer.dart';
import 'package:ComePost/pages/signup_supplier.dart';
import 'package:ComePost/pages/welcome_page.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:page_transition/page_transition.dart';

import '../app_localization.dart';

class RoutingScreen extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return MaterialApp(
      title: AppLocalizations.of(context).translate('title'),
      theme: ThemeData(
        primarySwatch: Colors.blue,
        textTheme: GoogleFonts.latoTextTheme(textTheme).copyWith(
          bodyText1: GoogleFonts.montserrat(textStyle: textTheme.bodyText1),
        ),
      ),
      debugShowCheckedModeBanner: false,
      home: MyWelcomePage(),
      onGenerateRoute: (s) {
        switch (s.name) {
          case '/selection_screen_login':
            return PageTransition(
                child: MySelectionScreenLogin(),
                type: PageTransitionType.rightToLeft);

          case '/selection_screen_signup':
            return PageTransition(
                child: SelectionScreenSignUp(),
                type: PageTransitionType.rightToLeft);

          case '/login':
            return PageTransition(
                child: LoginPage(), type: PageTransitionType.rightToLeft);

          case '/login_composter':
            return PageTransition(
                child: LoginComposterPage(),
                type: PageTransitionType.rightToLeft);

          case '/login_farmer':
            return PageTransition(
                child: LoginFarmerPage(), type: PageTransitionType.rightToLeft);

          case '/signup_supplier':
            return PageTransition(
                child: SignUpSupplier(), type: PageTransitionType.rightToLeft);

          case '/signup_composter':
            return PageTransition(
                child: SignUpComposter(), type: PageTransitionType.rightToLeft);

          case '/signup_farmer':
            return PageTransition(
                child: SignUpFarmer(), type: PageTransitionType.rightToLeft);

          case '/maps':
            return PageTransition(
                child: DisplayMap(), type: PageTransitionType.rightToLeft);

          case '/supplier_composter_transaction':
            return PageTransition(
                child: SCT(), type: PageTransitionType.rightToLeft);

          default:
            return PageTransition(
                child: Text('No path exist'),
                type: PageTransitionType.leftToRight);
        }
      },
      routes: {
        // '/signup' : (context) => SignUpPage(),
        // '/login' : (context) => LoginPage(),
        // '/login_composter' : (context) => LoginComposterPage(),
        // '/selection_screen_signup' : (context) =>  SelectionScreenSignUp(),
        // '/selection_screen_login' : (context) => SelectionScreenLogIn(),
        // '/home_supplier' : (context) => HomeSupplier(0),
        // '/signup_supplier' : (context) => SignUpSupplier(),
        // '/signup_composter' : (context) => SignUpComposter(),
        // '/home_composter' : (context) => HomeComposter(0),
        // '/maps' : (context) => DisplayMap(),
        // '/login_farmer' : (context) => LoginFarmerPage(),
        // '/signup_farmer' : (context) => SignUpFarmer(),
      },
    );
  }
}
