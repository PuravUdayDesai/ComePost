import 'package:flutter/material.dart';

import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:splashscreen/splashscreen.dart';
import 'package:page_transition/page_transition.dart';
import 'package:provider/provider.dart';
import 'app_language.dart';

import 'app_localization.dart';

import 'pages/welcome_page.dart';
import 'pages/selection_screen_login.dart';
import 'pages/selection_screen_signup.dart';
import 'pages/login.dart';
import 'pages/login_composter.dart';
import 'pages/login_farmer.dart';
import 'pages/signup_supplier.dart';
import 'pages/signup_composter.dart';
import 'pages/signup_farmer.dart';
import 'pages/DisplayMap.dart';
import 'Utils/util.dart';
import 'pages/sct.dart';



void main(){
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
    home: new MyApp(),
    theme : Util.theme,
    title : "Come Post", 
  ));
}


class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return new SplashScreen(
      seconds: 2,
      navigateAfterSeconds: new MyApp2(),
      title: new Text(AppLocalizations.of(context).translate('welcomeMessage'),
      style: new TextStyle(
        fontWeight: FontWeight.bold,
        fontSize: 28.0,
        color : Color(0xff33691E),
      ),),
      //image: new Image.network('https://i.imgur.com/TyCSG9A.png'),
      backgroundColor: Colors.white,
      styleTextUnderTheLoader: new TextStyle(),
      //photoSize: 100.0,
      onClick: ()=>print("Hello"),
      loaderColor: Colors.red
    );
  }
}

class MyApp2 extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {    
    final textTheme = Theme.of(context).textTheme;
    return MaterialApp(
      title: AppLocalizations.of(context).translate('title'),
      theme: ThemeData(
         primarySwatch: Colors.blue,
         textTheme:GoogleFonts.latoTextTheme(textTheme).copyWith(
           body1: GoogleFonts.montserrat(textStyle: textTheme.body1),
         ),
      ),
      debugShowCheckedModeBanner: false,
      home: MyWelcomePage(),
      onGenerateRoute : (s){
        switch(s.name){
          case '/selection_screen_login' : 
            return PageTransition(child : MySelectionScreenLogin(),type : PageTransitionType.rightToLeft);
            break;
          case '/selection_screen_signup' : 
            return PageTransition(child : SelectionScreenSignUp(),type : PageTransitionType.rightToLeft);
            break;
          case '/login' : 
            return PageTransition(child : LoginPage(),type : PageTransitionType.rightToLeft);
            break;
          case '/login_composter' : 
            return PageTransition(child : LoginComposterPage(),type : PageTransitionType.rightToLeft);
            break;
          case '/login_farmer' : 
            return PageTransition(child : LoginFarmerPage(),type : PageTransitionType.rightToLeft);
            break;
          case '/signup_supplier' : 
            return PageTransition(child : SignUpSupplier(),type : PageTransitionType.rightToLeft);
            break;
          case '/signup_composter' : 
            return PageTransition(child : SignUpComposter(),type : PageTransitionType.rightToLeft);
            break;
          case '/signup_farmer' : 
            return PageTransition(child : SignUpFarmer(),type : PageTransitionType.rightToLeft);
            break;
          case '/maps' : 
            return PageTransition(child : DisplayMap(),type : PageTransitionType.rightToLeft);
            break;
          case '/supplier_composter_transaction' : 
            return PageTransition(child : SCT(),type : PageTransitionType.rightToLeft);
            break;
        }
      },
      routes : {
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

