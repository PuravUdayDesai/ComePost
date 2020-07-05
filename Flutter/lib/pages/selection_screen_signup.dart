import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

import '../app_localization.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:page_transition/page_transition.dart';

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

class MySelectionScreenSignUp extends StatelessWidget {
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
      title : "Come Post",
      debugShowCheckedModeBanner: false,      
      home: SelectionScreenLogIn(),
      onGenerateRoute : (s){
        switch(s.name){
          case '/selection_screen_login' : 
            return PageTransition(child : MySelectionScreenLogin(),type : PageTransitionType.rightToLeft);
            break;
          case '/selection_screen_signup' : 
            return PageTransition(child : MySelectionScreenSignUp(),type : PageTransitionType.rightToLeft);
            break;
          case '/login' : 
            return PageTransition(child : MyLoginPage(),type : PageTransitionType.rightToLeft);
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


//SelectionScreenSignUp
class SelectionScreenSignUp extends StatefulWidget {
  SelectionScreenSignUp({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _SelectionScreenSignUpState createState() => _SelectionScreenSignUpState();
}

class _SelectionScreenSignUpState extends State<SelectionScreenSignUp> {
  Widget _submitButton() {
    return InkWell(
      onTap: () {
        
        Navigator.pushNamed(context, '/signup_supplier');
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
          AppLocalizations.of(context).translate('loginPgButOne'),
          style: TextStyle(fontSize: 20, color: Colors.red[700]),
        ),
      ),
    );
  }

  Widget _signUpButton() {
    return InkWell(
      onTap: () {
        Navigator.pushNamed(context, '/signup_composter');
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
          AppLocalizations.of(context).translate('loginPgButTwo'),
          style: TextStyle(fontSize: 20, color: Colors.red[700]),
        ),
      ),
    );
  }

  Widget _farmerButton() {
    return InkWell(
      onTap: () {
        Navigator.pushNamed(context, '/signup_farmer');
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
          AppLocalizations.of(context).translate('loginPgButThree'),
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
          text: AppLocalizations.of(context).translate('signUpPgHeadOne'),
          style: GoogleFonts.portLligatSans(
            textStyle: Theme.of(context).textTheme.display1,
            fontSize: 30,
            fontWeight: FontWeight.w700,
            color: Color(0xff33691e),
          ),
          children: [
            TextSpan(
              text: AppLocalizations.of(context).translate('signUpPgHeadTwo'),
              style: TextStyle(color: Colors.black, fontSize: 30),
            ),
            TextSpan(              
              text: AppLocalizations.of(context).translate('signUpPgHeadThree'),
              style: TextStyle(color: Color(0xff33691e), fontSize: 30),
            ),
          ]),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
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
                _farmerButton(),
                //_label()
              ],
            ),
          ),
        ],
      ),
    );
  }
}
