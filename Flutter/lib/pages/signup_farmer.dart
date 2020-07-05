import "package:flutter/material.dart";
import "package:flutter/services.dart";

import "package:intl/intl.dart";
//import 'package:flutter_form_builder/flutter_form_builder.dart';
import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:geolocator/geolocator.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

//Defined
//import "UserHomePage.dart";
//import "User.dart";
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

class MySignUpFarmer extends StatelessWidget {
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

class SignUpFarmer extends StatefulWidget {
  @override
  _SignUpFarmerState createState() => _SignUpFarmerState();
}
class Farmer
{
  dynamic farmerName,farmerContactNumber,serveyId,password,latitude,longitude,street,area,city,sstate;
  int id;
  Farmer(this.id,this.farmerName,this.farmerContactNumber,this.serveyId,this.password,this.latitude,this.longitude
    ,this.street,this.area,this.city,this.sstate);
  
  /*factory Farmer.fromJson(Map<String,dynamic> json)
  {
    dynamic _first_name = json["name"];
    dynamic _last_name = json["contactNumber"];
    int _company_id = json["emailId"];
    dynamic _company_name = json["registrationNumber"];
    dynamic _designation = json["state"];
    dynamic _email = json["city"];
    dynamic _contact = json["area"];
    dynamic _dob = json["street"];
    dynamic _dob2 = json["password"];
    dynamic _dob3 = json["latitude"];
    dynamic _dob4 = json["longitude"];
    //print(_dob.runtimeType);
    return Composter(0,_first_name,_last_name,_company_id,_company_name,_designation,_email,
      _contact,_dob,_dob2,_dob3,_dob4);
  }*/
  Map toMap() 
  {
    
    var map = new Map<String,dynamic>();
    map["farmerName"] = farmerName;
    map["farmerContact"] = farmerContactNumber;
    map["surveyId"] = int.parse(serveyId);
    map["password"] = password;
    map["latitude"] = latitude;
    map["longitude"] = longitude;
    map["street"] = street;
    map["area"] = area;
    map["city"] = city;
    map["state"] = sstate;
    //map["longitude"] = longitude;
    
    
    return map;
  }
}

class _SignUpFarmerState extends State<SignUpFarmer> {

  int currentCompanyKey;
  var currentCompany;
  bool isLoading = false;
  LatLng _center;
  var companies = new List<String>();
  var companiesMapping = new Map<String, dynamic>();
  final GlobalKey<FormState> _formKey = new GlobalKey<FormState>();
  bool _autoValidate = false;
  TextEditingController _areaController = new TextEditingController();
  TextEditingController _streetController = new TextEditingController();
  TextEditingController _firstnamecontroller = new TextEditingController();
  TextEditingController _stateController = new TextEditingController();
  TextEditingController _Registrationcontroller = new TextEditingController();
  TextEditingController _phonecontroller = new TextEditingController();
  TextEditingController _emailcontroller = new TextEditingController();
  TextEditingController _cityController = new TextEditingController();
  TextEditingController _passwordController = new TextEditingController();

  /*Future<UserClass> createRecord(dataa) async {
    var response1 = await ApiCall.createRecord(Uri.GET_USER, dataa);
    return UserClass.fromJson(response1);
  }*/

  Future<Farmer> createRecord(dataa) async {
    var response1 = await ApiCall.createRecord(Uri.GET_FARMER, dataa);
    //return Composter.fromJson(response1);
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

  
  /*Future _chooseDate(BuildContext context, String initialDateString) async 
  {
    var now = new DateTime.now();
    var initialDate = convertToDate(initialDateString) ?? now;
    initialDate = (initialDate.year >= 1900 && initialDate.isBefore(now)
        ? initialDate
        : now);

    var result = await showDatePicker(
        context: context,
        initialDate: initialDate,
        firstDate: new DateTime(1900),
        lastDate: new DateTime.now());

    if (result == null) return;

    setState(() {
      _controller.text = new DateFormat("dd/MM/yyyy").format(result);
    });
  }*/

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

  Future getCompanies() async {
    // var response1 = await ApiCall.getDataFromApi(Uri.GET_COMPANY);

    // for (int i = 0; i < response1.length; i++) {
    //   int _id = response1[i]["company_id"];
    //   String _comapny = response1[i]["company_name"];
    //   if(i==0)
    //   {
    //     currentCompanyKey = _id;
    //     currentCompany = _comapny;
    //   }

    //   if (!(companies.contains(_comapny) || _comapny == "")) {
    //     companiesMapping["$_id"] = _comapny;
    //     companies.add(_comapny);
    //   }
    // }
    // return companiesMapping;
    // print(companiesMapping);
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

  callSuccess(context) {
    Alert(
      context: context,
      type: AlertType.success,
      title: AppLocalizations.of(context).translate('signPopUpHead'),
      desc: AppLocalizations.of(context).translate('signPopUpFarMsg'),
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

  //dynamic farmerName,farmerContactNumber,serveyId,password,latitude,longitude,street,area,city,sstate;

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
                keyboardType: TextInputType.phone,
              controller : _Registrationcontroller,
              decoration: InputDecoration(
                icon: Icon(Icons.filter_4),
                hintText: AppLocalizations.of(context).translate('signUpHintF10'),
                labelText: AppLocalizations.of(context).translate('signUpF10'),
              ),
              validator: (val) => val.isEmpty ? AppLocalizations.of(context).translate('signUpErrF10') : null,
            ),
              ),

            Padding(
              padding : const EdgeInsets.only(left : 10.0,right : 16.0),
              child : TextFormField(  
              controller : _passwordController,
              decoration: InputDecoration(
                icon: Icon(Icons.lock),
                hintText: AppLocalizations.of(context).translate('signUpHintF2'),
                labelText: AppLocalizations.of(context).translate('signUpF2'),
              ),
              obscureText : false,
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
            
            /*Padding(
              padding : const EdgeInsets.only(left : 10.0,right : 16.0),
              child : TextFormField(
              controller : _emailcontroller,
              decoration: InputDecoration(
                icon: Icon(Icons.email),
                hintText: 'Enter a email address',
                labelText: 'Email',
              ),
              keyboardType: TextInputType.emailAddress,
              validator: (value) => isValidEmail(value)
                  ? (isEmailContains(value)
                      ? null
                      : 'Email must contain @ and .')
                  : 'Please enter a valid email address',
              //onSaved : (val) => newEnquiry.email=val,
            ),
              ),*/
            
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
    Farmer s1 = new Farmer(0,_firstnamecontroller.text,
                        _phonecontroller.text,
                        //_emailcontroller.text,
                        _Registrationcontroller.text,
                        _passwordController.text,
                        55.96,56.32,
                        _streetController.text,
                        _areaController.text,
                        _cityController.text,
                        _stateController.text
                        
                        
                        
                        
                        

                        );
                      print(s1.toMap());
                      Farmer u2 = await createRecord(
                                        s1.toMap());
                                    print("User Created");
                                    setState((){isLoading = false;});
                                    callSuccess(context);
  }


  Widget build(BuildContext context) {
  
    return Scaffold(
      appBar: AppBar(
        title: Text(AppLocalizations.of(context).translate('signUpFarHeader')),
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
      
      body : IgnorePointer(
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
