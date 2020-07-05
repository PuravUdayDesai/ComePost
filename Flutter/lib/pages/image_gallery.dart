import 'package:http/http.dart' as http;
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'dart:io';
import "dart:async";
import 'dart:convert';
import 'dart:core';
import '../User/current_user.dart';
import '../Api_Services/ApiCall.dart';

 
import '../app_localization.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:page_transition/page_transition.dart';

import 'display_img.dart';
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
import '../Utils/util.dart';

class MyImageGallery extends StatelessWidget {
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
      theme : Util.theme,
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


class ImageGallery extends StatefulWidget{
  int id;
  dynamic date;
  String user;

  ImageGallery(this.id, this.date, this.user);

	@override
  	DisplayImageGallery createState() => DisplayImageGallery();
}

class DisplayImageGallery extends State<ImageGallery>{

    @override
    Widget build(BuildContext context) {
      return Scaffold(
        appBar: AppBar(
          title: Text(AppLocalizations.of(context).translate('picsHead')),
          centerTitle: true,          
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
        body: FutureBuilder(
          future: getImageUrls(),
          builder: (BuildContext context, AsyncSnapshot snapshot) {
            if (snapshot.data == null) {
              return Container(
                child: Center(
                  child: CircularProgressIndicator(
                    semanticsLabel: "Loading 1..",
                    semanticsValue: "Loading 2..",
                  ),
                ),
              );
            }  
            else {    
            if(snapshot.data=='nothing'){
              return Center(child : Text(AppLocalizations.of(context).translate('mtMsg'),textScaleFactor:1.7,textAlign: TextAlign.center));
            }          
              return ListView.builder(
                itemCount: snapshot.data.length,
                itemBuilder: (BuildContext context, int index) {
                  return Image.network(
                    snapshot.data[index]['imageURL'],
                    width: double.infinity,
                    height: 300,                    
                  );
                },
              );
            }            
          }
        ),
      );
    }

    getImageUrls() async{   
      if(widget.user == "farmer"){
        String url = 'http://13.68.186.134:8080/compost/'+widget.id.toString();
        var resp = await ApiCall.getDataFromApi(url);        
        print("RESP--------------");        
        print(resp);
        String Newurl = "http://13.68.186.134:8080/compost/composterCompostImage/${widget.id.toString()}?init_id=${resp[0]['id']}&date=${widget.date.toString().substring(0,10)}";
        print("MAIN URL--------------");
        print(Newurl);
        var respon = await ApiCall.getDataFromApi(Newurl);
        print("OUTPUT--------------");
        print(respon);
        return respon; 
      } else{
        String url = "http://13.68.186.134:8080/supplier/display/supplierWasteImages/"+widget.id.toString()+"?date="+widget.date;
        print(url);
        var res = await ApiCall.getDataFromApi(url);
        print('res = $res');
        return res; 
      }
          
    }
}