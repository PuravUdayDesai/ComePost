import "package:flutter/material.dart";
import "package:flutter/services.dart";

import "package:intl/intl.dart";
//import 'package:flutter_form_builder/flutter_form_builder.dart';
import 'package:geolocator/geolocator.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

import "../Api_Services/ApiCall.dart";
import "../Api_Services/Uri.dart";


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

class MySignUpSupplier extends StatelessWidget {
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
        }
      }
    );
  }
}


class SignUpSupplier extends StatefulWidget {
  @override
  _SignUpSupplierState createState() => _SignUpSupplierState();
}
class Supplier
{
  int id;
  dynamic name,phone,email,regd,stateName,cityName,areaName,streetName,password,latitude,longitude;
  Supplier(this.id,this.name,this.phone,this.email,this.regd,this.stateName,
    this.cityName,this.areaName,this.streetName,this.password
    ,this.latitude,this.longitude);
  /*factory Supplier.fromJson(Map<String,dynamic> json)
  {
    dynamic _first_name = json["firstname"];
    dynamic _last_name = json["lastname"];
    int _company_id = json["company_id"];
    dynamic _company_name = json["company_name"];
    dynamic _designation = json["designation"];
    dynamic _email = json["email"];
    dynamic _contact = json["contact"];
    dynamic _dob = json["dob"];
    print(_dob.runtimeType);
    return UserClass(0,_first_name,_last_name,_company_id,_company_name,_designation,_email,_contact,_dob);
  }*/
  Map toMap() 
  {
    var map = new Map<String,dynamic>();
    map["name"] = name;
    map["contactNumber"] = phone;
    map["emailId"] = email;
    map["registrationNumber"] = regd;
    map["state"] = stateName;
    map["city"] = cityName;
    map["area"] = areaName;
    map["street"] = streetName;
    map["password"] = password;
    map["latitude"] = latitude;
    map["longitude"] = longitude;
    
    
    return map;
  }
}

class _SignUpSupplierState extends State<SignUpSupplier> {

  int currentCompanyKey;
  var currentCompany;
  var companies = new List<String>();
  var companiesMapping = new Map<String, dynamic>();
  final GlobalKey<FormState> _formKey = new GlobalKey<FormState>();
  bool _autoValidate = false;
  LatLng _center;
  bool isLoading = false;
  TextEditingController _areaController = new TextEditingController();
  TextEditingController _streetController = new TextEditingController();
  TextEditingController _firstnamecontroller = new TextEditingController();
  TextEditingController _stateController = new TextEditingController();
  TextEditingController _Registrationcontroller = new TextEditingController();
  TextEditingController _phonecontroller = new TextEditingController();
  TextEditingController _emailcontroller = new TextEditingController();
  TextEditingController _cityController = new TextEditingController();
  TextEditingController _passwordController = new TextEditingController();

  Future<Supplier> createRecord(dataa) async {
    var response1 = await ApiCall.createRecord(Uri.GET_SUPPLIER, dataa);
    //return Supplier.fromJson(response1);
  }


  DateTime convertToDate(String input) 
  {
    List<String> parse = input.split("/");
    print("date = "+input);
    try {
      var d = new DateFormat.yMd().parseStrict(parse[1]+"/"+parse[0]+"/"+parse[2]);
      print(d);
      return d;
    } catch (e) {
      print(e);
    }
  }

  callSuccess(context) {
    Alert(
      context: context,
      type: AlertType.success,
      title: AppLocalizations.of(context).translate('signPopUpHead'),
      desc: AppLocalizations.of(context).translate('signPopUpSupMsg'),
      buttons: [
        DialogButton(
          child: Text(
            AppLocalizations.of(context).translate('popUpFooter'),
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () {
           Navigator.of(context, rootNavigator: true).pop();
            Navigator.of(context).pushNamedAndRemoveUntil('/selection_screen_login', ModalRoute.withName('/'));
            },

          width: 120,
        )
      ],
    ).show();
  }

  
  

  bool isValidDob(String dob) {
    if (dob.isEmpty) return true;
    var d = convertToDate(dob);
    return d != null && d.isBefore(new DateTime.now())&&d.isAfter(new DateTime(1900));
  }


  bool isValidEmail(String input) {
    final RegExp regex = new RegExp(
        r"^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?)*$");
    return regex.hasMatch(input);
  }

  bool isEmailContains(String input) {
    return input.contains("@") && input.contains(".");
  }

  bool isValidPhoneNumber(String input) {
    final RegExp regex = new RegExp(r'^\d\d\d\d\d\d\d\d\d\d$');
    return regex.hasMatch(input);
  }

  

  @override
  void initState(){
  getCurrentLocation();
  super.initState();
  }
  void getCurrentLocation() async{
  var currentLocation = await Geolocator()
  .getCurrentPosition(desiredAccuracy: LocationAccuracy.best);
  _center=LatLng(currentLocation.latitude, currentLocation.longitude);
  //print(_center.latitude);
  }


  makeForm(context)
  { 
    return ListView.builder(
              itemCount : 1,
              itemBuilder : (BuildContext context,int index)
              {
                return Form(
        key: _formKey,
        autovalidate: _autoValidate,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
            
          children: <Widget>[
            Padding(
              padding :const EdgeInsets.only(left: 10.0,right : 16.0),
              child : TextFormField(
              controller : _firstnamecontroller,
              decoration: InputDecoration(
                icon: Icon(Icons.person_add),
                hintText: AppLocalizations.of(context).translate('signUpHintF1'),
                labelText: AppLocalizations.of(context).translate('signUpF1'),
              ),
              validator: (val) => val.isEmpty ? AppLocalizations.of(context).translate('signUpErrF1') : null,
            )),

            Padding(
              padding : const EdgeInsets.only(left : 10.0,right : 16.0),
              child : TextFormField(  
              controller : _passwordController,
              decoration: InputDecoration(
                icon: Icon(Icons.lock),
                hintText: AppLocalizations.of(context).translate('signUpHintF2'),
                labelText: AppLocalizations.of(context).translate('signUpF2'),
              ),
              validator: (val) => val.isEmpty ? AppLocalizations.of(context).translate('signUpErrF2') : null,
            ),
              ),
            
            
            Padding(
              padding : const EdgeInsets.only(left : 10.0,right : 16.0),
              child : TextFormField(
              controller : _phonecontroller,
              decoration: InputDecoration(
                icon: Icon(Icons.phone),
                hintText: AppLocalizations.of(context).translate('signUpHintF3'),
                labelText: AppLocalizations.of(context).translate('signUpF3'),
              ),
              keyboardType: TextInputType.phone,
              inputFormatters: [
                LengthLimitingTextInputFormatter(10),
                WhitelistingTextInputFormatter(new RegExp(r'^[()\d -]{1,15}$')),
              ],
              //maxLength: ,
              validator: (value) => isValidPhoneNumber(value)
                  ? null
                  : AppLocalizations.of(context).translate('signUpErrF3'),
              //onSaved: (val) => newEnquiry.phone=val,
            ),
              ),
            
            Padding(
              padding : const EdgeInsets.only(left : 10.0,right : 16.0),
              child : TextFormField(
              controller : _emailcontroller,
              decoration: InputDecoration(
                icon: Icon(Icons.email),
                hintText: AppLocalizations.of(context).translate('signUpHintF4'),
                labelText: AppLocalizations.of(context).translate('signUpF4'),
              ),
              keyboardType: TextInputType.emailAddress,
              validator: (value) => isValidEmail(value)
                  ? (isEmailContains(value)
                      ? null
                      : AppLocalizations.of(context).translate('signUpErr1F4'))
                  : AppLocalizations.of(context).translate('signUpErr2F4'),
              //onSaved : (val) => newEnquiry.email=val,
            ),
              ),
            Padding(
              padding : const EdgeInsets.only(left : 10.0,right : 16.0),
              child : TextFormField(
                keyboardType: TextInputType.phone,
              controller : _Registrationcontroller,
              decoration: InputDecoration(
                icon: Icon(Icons.filter_4),
                hintText: AppLocalizations.of(context).translate('signUpHintF5'),
                labelText: AppLocalizations.of(context).translate('signUpF5'),
              ),
              validator: (val) => val.isEmpty ? AppLocalizations.of(context).translate('signUpErrF5') : null,
            ),
              ),
            Padding(
              padding : const EdgeInsets.only(left : 10.0,right : 16.0),
              child : TextFormField(
              controller : _stateController,
              decoration: InputDecoration(
                icon: Icon(Icons.business),
                hintText: AppLocalizations.of(context).translate('signUpHintF6'),
                labelText: AppLocalizations.of(context).translate('signUpF6'),
              ),
              validator: (val) => val.isEmpty ? AppLocalizations.of(context).translate('signUpErrF6') : null,
            ),
              ),
            Padding(
              padding : const EdgeInsets.only(left : 10.0,right : 16.0),
              child : TextFormField(
              controller : _cityController,
              decoration: InputDecoration(
                icon: Icon(Icons.business),
                hintText: AppLocalizations.of(context).translate('signUpHintF7'),
                labelText: AppLocalizations.of(context).translate('signUpF7'),
              ),
              validator: (val) => val.isEmpty ? AppLocalizations.of(context).translate('signUpErrF7') : null,
            ),
              ),
            Padding(
              padding : const EdgeInsets.only(left : 10.0,right : 16.0),
              child : TextFormField(
              controller : _areaController,
              decoration: InputDecoration(
                icon: Icon(Icons.business),
                hintText: AppLocalizations.of(context).translate('signUpHintF8'),
                labelText: AppLocalizations.of(context).translate('signUpF8'),
              ),
              validator: (val) => val.isEmpty ? AppLocalizations.of(context).translate('signUpErrF8') : null,
            ),
              ),
            Padding(
              padding : const EdgeInsets.only(left : 10.0,right : 16.0),
              child : TextFormField(
              controller : _streetController,
              decoration: InputDecoration(
                icon: Icon(Icons.business),
                hintText: AppLocalizations.of(context).translate('signUpHintF9'),
                labelText: AppLocalizations.of(context).translate('signUpF9'),
              ),
              validator: (val) => val.isEmpty ? AppLocalizations.of(context).translate('signUpErrF9') : null,
            ),
              ),
            
            Container(
              padding: EdgeInsets.only(left: 60.0, top: 40.0, right: 60.0),
              height: 85.0,
              child: FlatButton(
                  textColor: Colors.black,
                  color: Colors.white,
                  shape: RoundedRectangleBorder(
                    borderRadius: new BorderRadius.circular(4.0),
                    side: BorderSide(color: Colors.green),
                  ),
                  padding: const EdgeInsets.all(8.0),
                  child: Text(
                    AppLocalizations.of(context).translate('submitBut'),
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 20.0,
                    ),
                  ),
                  onPressed: () async{
                    if (_formKey.currentState.validate()) {

                      //print("Ok");   
                      addData();
                     
                    } else {
                      setState(() {
                        _autoValidate = true;
                      });
                    }
                  }),
            ),
            Container(
              height: 20.0,
            ),
          ],
        ),
      );
              }
              );
          
        
  }

  addData() async{
    setState((){isLoading = true;});
     Supplier s1 = new Supplier(0,_firstnamecontroller.text,
                        _phonecontroller.text,
                        _emailcontroller.text,
                        _Registrationcontroller.text,
                        _stateController.text,
                        _cityController.text,
                        _areaController.text,
                        _streetController.text,
                        _passwordController.text,
                        //27.2038,77.5011
                        _center.latitude.toString(),
                        _center.longitude.toString()
                        );
                      //print(s1.toMap());
                      //getLocation();
                      Supplier s2 = await createRecord(s1.toMap());
                      setState((){isLoading = false;});
                      print('done here');
                      callSuccess(context);
  }


  Widget build(BuildContext context) {
  
    return Scaffold(
      appBar: AppBar(
        title: Text(AppLocalizations.of(context).translate('signUpSupHeader')),
        flexibleSpace: Container(
          decoration: BoxDecoration(
            gradient: LinearGradient(
              begin: Alignment.topLeft,
                end: Alignment.bottomRight,
                colors: <Color>[
              Color(0xff43a047),
              Color(0xff2e7d32)
            ])          
         ),        
     ),      
 ),
      body: IgnorePointer(
        ignoring : isLoading,
        child : Stack(
          children : <Widget>[
            makeForm(context),
            isLoading ? Center(child : CircularProgressIndicator()) : Center(child : Container()),  
          ]
        ),
      ),
    );
  }
}
