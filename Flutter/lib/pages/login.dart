import 'package:flutter/material.dart';

import 'package:google_fonts/google_fonts.dart';

import '../Widget/bezierContainer.dart';
import "../Api_Services/ApiCall.dart";
import "../Api_Services/Uri.dart";
import "dart:async";
import "package:http/http.dart" as http;
import "dart:convert";
import '../User/current_user.dart';
import 'home_supplier.dart';

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
import 'sct.dart';


class MyLoginPage extends StatelessWidget {
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
          case '/supplier_composter_transaction' : 
            return PageTransition(child : SCT(),type : PageTransitionType.rightToLeft);
            break;
        }
      }
    );
  }
}


class LoginPage extends StatefulWidget {
  

  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  TextEditingController emailController = new TextEditingController() ;
  TextEditingController passwordController = new TextEditingController();
  bool isLoading = false;

  Widget _backButton() {
    return InkWell(
      onTap: () {
        Navigator.pop(context);
      },
      child: Container(
        padding: EdgeInsets.symmetric(horizontal: 10),
        child: Row(
          children: <Widget>[
            Container(
              padding: EdgeInsets.only(left: 0, top: 10, bottom: 10),
              child: Icon(Icons.keyboard_arrow_left, color: Colors.black),
            ),
            Text(AppLocalizations.of(context).translate('backIcon'),
                style: TextStyle(fontSize: 12, fontWeight: FontWeight.w500))
          ],
        ),
      ),
    );
  }

  Widget _entryField(String title,TextEditingController controllerName, {bool isPassword = false}) {
    return Container(
      margin: EdgeInsets.symmetric(vertical: 10),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Text(
            title,
            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 15),
          ),
          SizedBox(
            height: 10,
          ),
          TextField(
              controller : controllerName,
              obscureText: isPassword,
              decoration: InputDecoration(
                  border: InputBorder.none,
                  fillColor: Color(0xfff3f3f4),
                  filled: true))
        ],
      ),
    );
  }

  void checkCredentials() async
  {
    print("inn");
    FocusScope.of(context).unfocus();
    setState((){isLoading = true;});
    String usernametxt,passwordtxt;
        usernametxt = emailController.text;
        passwordtxt = passwordController.text;
        print(usernametxt+" "+passwordtxt); 

        String uri = "http://13.68.186.134:8080/supplier/login?username="+usernametxt+"&password="+passwordtxt;
        print(uri);

          var response = await http.get(
            uri
            );
          //print(response.body);
          var response1 = jsonDecode(response.body);
          print(response1);
          //setState((){isLoading = false;});
          //print(response1["id"]);
          //print(response1["valid"].runtimeType);
        if(response1["valid"].toString()=="true")
        {
          setState((){isLoading = false;});
          print(usernametxt+" in "+passwordtxt);

          CurrentUser.id = response1['id'];
          CurrentUser.name = response1['name'];
          CurrentUser.phone = response1['contactNumber'];
          CurrentUser.regdno = response1['registrationNumber'];
          CurrentUser.email = response1['emailId'];
          Navigator.of(context).push(_createRoute());
      
        }
        
        else 
        {
          setState((){isLoading = false;});
          showError();
        }
          
  }

  Route _createRoute() 
  {
      return PageRouteBuilder(
        transitionDuration: Duration(milliseconds: 900),
      pageBuilder: (context, animation, secondaryAnimation) => HomeSupplier(),
      transitionsBuilder: (context, animation, secondaryAnimation, child) {
      var begin = Offset(1.0, 0.0);
      var end = Offset.zero;
      var curve = Curves.easeOut;

        var tween = Tween(begin: begin, end: end).chain(CurveTween(curve: curve));

        return SlideTransition(
        position: animation.drive(tween),
        child: child,
        );
      },
      );
  }

  void showError()
  {
    showDialog(
        context: context,
        builder: (BuildContext context1){
            return AlertDialog(
              title: Text(AppLocalizations.of(context).translate('popUpHeader'),
                style : TextStyle(
                  color : Colors.red,
                  )
                ),                
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(15.0),
              ),
              content: Text(AppLocalizations.of(context).translate('popUpMsg')),
              actions : <Widget>[
                FlatButton(
                  child : Text(
                    AppLocalizations.of(context).translate('popUpFooter'),
                    
                    style : TextStyle(
                        fontSize : 15.0,
                      )
                    ),
                onPressed: () {
              Navigator.of(context1).pop();
            },
                )
              ],
            );
        }
      );
  }


  Widget _submitButton() {
    return MaterialButton(
      onPressed : checkCredentials,
    child : Container(
      width: MediaQuery.of(context).size.width,
      padding: EdgeInsets.symmetric(vertical: 15),
      alignment: Alignment.center,
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
              begin: Alignment.centerLeft,
              end: Alignment.centerRight,
              colors: [Color(0xff12871b), Color(0xff36ba40)])),
      child: Text(
        AppLocalizations.of(context).translate('loginBut'),
        style: TextStyle(fontSize: 20, color: Colors.white),
      ),
    ));
  }

  Widget _divider() {
    return Container(
      margin: EdgeInsets.symmetric(vertical: 10),
      child: Row(
        children: <Widget>[
          SizedBox(
            width: 20,
          ),
          Expanded(
            child: Padding(
              padding: EdgeInsets.symmetric(horizontal: 10),
              child: Divider(
                thickness: 1,
              ),
            ),
          ),
          //Text('or'),
          Expanded(
            child: Padding(
              padding: EdgeInsets.symmetric(horizontal: 10),
              child: Divider(
                thickness: 1,
              ),
            ),
          ),
          SizedBox(
            width: 20,
          ),
        ],
      ),
    );
  }

  Widget _facebookButton() {
    return Container(
      height: 50,
      margin: EdgeInsets.symmetric(vertical: 20),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.all(Radius.circular(10)),
      ),
      child: Row(
        children: <Widget>[
          Expanded(
            flex: 1,
            child: Container(
              decoration: BoxDecoration(
                color: Color(0xff1959a9),
                borderRadius: BorderRadius.only(
                    bottomLeft: Radius.circular(5),
                    topLeft: Radius.circular(5)),
              ),
              alignment: Alignment.center,
              child: Text('f',
                  style: TextStyle(
                      color: Colors.white,
                      fontSize: 25,
                      fontWeight: FontWeight.w400)),
            ),
          ),
          Expanded(
            flex: 5,
            child: Container(
              decoration: BoxDecoration(
                color: Color(0xff2872ba),
                borderRadius: BorderRadius.only(
                    bottomRight: Radius.circular(5),
                    topRight: Radius.circular(5)),
              ),
              alignment: Alignment.center,
              child: Text('Log in with Facebook',
                  style: TextStyle(
                      color: Colors.white,
                      fontSize: 18,
                      fontWeight: FontWeight.w400)),
            ),
          ),
        ],
      ),
    );
  }

  Widget _createAccountLabel() {
    return Container(
      margin: EdgeInsets.symmetric(vertical: 20),
      alignment: Alignment.bottomCenter,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Text(
            AppLocalizations.of(context).translate('loginFooter'),
            style: TextStyle(fontSize: 13, fontWeight: FontWeight.w600),
          ),
          SizedBox(
            width: 10,
          ),
          InkWell(
            onTap: () {
              Navigator.of(context).pushNamedAndRemoveUntil('/selection_screen_signup', ModalRoute.withName('/'));
            },
            child: Text(
              AppLocalizations.of(context).translate('registerBut'),
              style: TextStyle(
                  color: Color(0xfff5544c),
                  fontSize: 13,
                  fontWeight: FontWeight.bold),
            ),
          )
        ],
      ),
    );
  }

  Widget _title() {
    return RichText(
      textAlign: TextAlign.center,
      text: TextSpan(
          text: '',
          style: GoogleFonts.portLligatSans(
            textStyle: Theme.of(context).textTheme.display1,
            fontSize: 30,
            fontWeight: FontWeight.w700,
            color: Color(0xffe46b10),
          ),
          children: [
            TextSpan(
              text: AppLocalizations.of(context).translate('headerOne'),
              style: TextStyle(color: Colors.black, fontSize: 30),
            ),
            TextSpan(
              text: AppLocalizations.of(context).translate('headerTwo'),
              style: TextStyle(color: Colors.green[600], fontSize: 30),
            ),
          ]),
    );
  }

  Widget _emailPasswordWidget() {
    return Column(
      children: <Widget>[
        Container(
      margin: EdgeInsets.symmetric(vertical: 10),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Text(
            AppLocalizations.of(context).translate('emailSubHeader'),
            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 15),
          ),
          SizedBox(
            height: 10,
          ),
          TextFormField(
              controller : emailController,
              //obscureText: isPassword,
              decoration: InputDecoration(
                  border: InputBorder.none,
                  fillColor: Color(0xfff3f3f4),
                  filled: true))
        ],
      ),
    ),
        Container(
      margin: EdgeInsets.symmetric(vertical: 10),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Text(
            AppLocalizations.of(context).translate('passwordSubHeader'),
            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 15),
          ),
          SizedBox(
            height: 10,
          ),
          TextFormField(
              controller : passwordController,
              obscureText: true,
              decoration: InputDecoration(
                  border: InputBorder.none,
                  fillColor: Color(0xfff3f3f4),
                  filled: true))
        ],
      ),
    ),

        /*_entryField("Email id","emailController"),
        _entryField("Password","passwordController", isPassword: true),*/
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomPadding: false,
      body: IgnorePointer(
        ignoring : isLoading,
        child : Stack(
        children: <Widget>[
          Container(
            padding: EdgeInsets.symmetric(horizontal: 20),
            child: ListView(
              children: <Widget>[
                
                SizedBox(
                  height : 150.0,
                ),
                _title(),
                SizedBox(
                  height: 50,
                ),
                _emailPasswordWidget(),
                SizedBox(
                  height: 20,
                ),
                _submitButton(),
                /*Container(
                  padding: EdgeInsets.symmetric(vertical: 10),
                  alignment: Alignment.centerRight,
                  child: Text('Forgot Password ?',
                      style:
                          TextStyle(fontSize: 14, fontWeight: FontWeight.w500)),
                ),*/
                //_divider(),
                //_facebookButton(),
                
                SizedBox(),
              ],
            ),
          ),
          Align(
            alignment: Alignment.bottomCenter,
            child: _createAccountLabel(),
          ),
          Positioned(top: 40, left: 0, child: _backButton()),
          Positioned(
              top: -MediaQuery.of(context).size.height * .10,
              right: -MediaQuery.of(context).size.width * .7,
              child: BezierContainer()
          ),
          isLoading ? Center(child : CircularProgressIndicator()) : Center(child : Container()),
        ],
      )
      ),
    );
  }
}
