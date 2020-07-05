import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import '../app_localization.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:page_transition/page_transition.dart';
import '../app_language.dart';
import '../main.dart';

import 'welcome_page.dart';
import 'selection_screen_login.dart';
import 'selection_screen_signup.dart';
import 'login.dart';
import 'login_composter.dart';
import 'login_farmer.dart';
import 'signup_supplier.dart';
import 'signup_composter.dart';
import 'signup_farmer.dart';
import 'DisplayMap.dart';
import '../Utils/util.dart';

class MyWelcomePage extends StatelessWidget {

  // @override
  // Widget build(BuildContext context) {
  //       return MaterialApp(
  //         locale: model.appLocal,
  //         supportedLocales: [
  //           Locale('en', ''),
  //           Locale('hi', ''),
  //         ],
  //         localizationsDelegates: [
  //           AppLocalizations.delegate,
  //           GlobalMaterialLocalizations.delegate,
  //           GlobalWidgetsLocalizations.delegate,
  //         ],
  //         debugShowCheckedModeBanner: false,
  //         home: WelcomePage(),
  //         onGenerateRoute : (s){
  //           switch(s.name){
  //             case '/selection_screen_login' : 
  //               return PageTransition(child : MySelectionScreenLogin(),type : PageTransitionType.rightToLeft);
  //               break;
  //             case '/selection_screen_signup' : 
  //               return PageTransition(child : SelectionScreenSignUp(),type : PageTransitionType.rightToLeft);
  //               break;
  //             case '/login' : 
  //               return PageTransition(child : LoginPage(),type : PageTransitionType.rightToLeft);
  //               break;
  //             case '/login_composter' : 
  //               return PageTransition(child : LoginComposterPage(),type : PageTransitionType.rightToLeft);
  //               break;
  //             case '/login_farmer' : 
  //               return PageTransition(child : LoginFarmerPage(),type : PageTransitionType.rightToLeft);
  //               break;
  //             case '/signup_supplier' : 
  //               return PageTransition(child : SignUpSupplier(),type : PageTransitionType.rightToLeft);
  //               break;
  //             case '/signup_composter' : 
  //               return PageTransition(child : SignUpComposter(),type : PageTransitionType.rightToLeft);
  //               break;
  //             case '/signup_farmer' : 
  //               return PageTransition(child : SignUpFarmer(),type : PageTransitionType.rightToLeft);
  //               break;
  //           }
  //         }
  //       );
  // }

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
      title : "Come Post", 
      debugShowCheckedModeBanner: false,      
      home: WelcomePage(),
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
        }
      }
    );
  }
}

class WelcomePage extends StatefulWidget {
  @override
  _WelcomePageState createState() => _WelcomePageState();
}

class _WelcomePageState extends State<WelcomePage> {

  Widget _submitButton() {
    return InkWell(
      onTap: () {
        Navigator.pushNamed(context, '/selection_screen_login');
      }
        ,
      child: Container(
        width: MediaQuery.of(context).size.width,
        padding: EdgeInsets.symmetric(vertical: 13),
        alignment: Alignment.center,
        decoration: BoxDecoration(
            borderRadius: BorderRadius.all(Radius.circular(5)),
            boxShadow: <BoxShadow>[
              BoxShadow(
                  color: Colors.black.withAlpha(100),
                  offset: Offset(2, 4),
                  blurRadius: 8,
                  spreadRadius: 2)
            ],
            color: Colors.white),
        child: Text(
          AppLocalizations.of(context).translate('loginBut'),
          style: TextStyle(fontSize: 20, color: Colors.red[700]),
        ),
      ),
    );
  }

  Widget _signUpButton() {
    return InkWell(
      onTap: () {
        Navigator.pushNamed(context, '/selection_screen_signup');
      }
        ,
      child: Container(
        width: MediaQuery.of(context).size.width,
        padding: EdgeInsets.symmetric(vertical: 13),
        alignment: Alignment.center,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.all(Radius.circular(5)),
          border: Border.all(color: Colors.white, width: 2),
        ),
        child: Text(
          AppLocalizations.of(context).translate('registerBut'),          
          style: TextStyle(fontSize: 20, color: Colors.red[700]),
        ),
      ),
    );
  }

  // Widget _label() {
  //   return Container(
  //       margin: EdgeInsets.only(top: 40, bottom: 20),
  //       child: Column(
  //         children: <Widget>[
  //           Text(
  //             'Quick login with Touch ID',
  //             style: TextStyle(color: Colors.white, fontSize: 17),
  //           ),
  //           SizedBox(
  //             height: 20,
  //           ),
  //           Icon(Icons.fingerprint, size: 90, color: Colors.white),
  //           SizedBox(
  //             height: 20,
  //           ),
  //           Text(
  //             'Touch ID',
  //             style: TextStyle(
  //               color: Colors.white,
  //               fontSize: 15,
  //               decoration: TextDecoration.underline,
  //             ),
  //           ),
  //         ],
  //       ));
  // }

  Widget _title() {
    return RichText(
      textAlign: TextAlign.center,
      text: TextSpan(
          text: ' ',
          style: GoogleFonts.portLligatSans(
            textStyle: Theme.of(context).textTheme.display1,
            fontSize: 30,
            fontWeight: FontWeight.w700,
            color: Colors.white,
          ),
          children: [
            TextSpan(
              text: AppLocalizations.of(context).translate('headerOne'),
              style: TextStyle(color: Colors.green[600], fontSize: 30,fontWeight: FontWeight.bold),
            ),
            TextSpan(
              text: AppLocalizations.of(context).translate('headerTwo'),
              style: TextStyle(color: Color(0xff3e2723), fontSize: 30),
            ),
          ]),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body:  Stack(
        children: <Widget>[
          Container(
            padding: EdgeInsets.symmetric(horizontal: 20),
            height: MediaQuery.of(context).size.height,
            decoration: BoxDecoration(
                borderRadius: BorderRadius.all(Radius.circular(5)),
                boxShadow: <BoxShadow>[
                  BoxShadow(
                      color: Colors.grey.shade200,
                      offset: Offset(2, 4),
                      blurRadius: 5,
                      spreadRadius: 2)
                ],
                gradient: LinearGradient(
                    begin: Alignment.topCenter,
                    end: Alignment.bottomCenter,
                    colors: [Color(0xfff1f8e9),Color(0xffaed581)])),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                _title(),
                SizedBox(
                  height: 80,
                ),
                _submitButton(),
                SizedBox(
                  height: 20,
                ),
                _signUpButton(),
                SizedBox(
                  height: 20,
                ),                
              ],
            ),
          ),
        ],
      ),    
      );    
  }
}




