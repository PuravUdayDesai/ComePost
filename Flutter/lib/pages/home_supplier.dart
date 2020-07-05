import 'package:flutter/material.dart';
import 'dart:io';
import 'dart:convert';
import 'dart:core';

import 'package:rflutter_alert/rflutter_alert.dart';
import 'package:image_picker/image_picker.dart';
import 'package:http/http.dart' as http;

import 'package:dio/dio.dart';
import '../Utils/util.dart';

//import 'SignUpSupplier.dart';
import "../Api_Services/ApiCall.dart";
import "../Api_Services/Uri.dart";
//import 'package:flutter_form_builder/flutter_form_builder.dart';
import '../User/current_user.dart';

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


class MyHomeSupplier extends StatelessWidget {
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

class HomeSupplier extends StatefulWidget
{
	@override
  _HomeSupplierState createState() => _HomeSupplierState();
}

class SupplierClass
{
  int id;
  dynamic date;
  dynamic dryWaste,wetWaste;
  dynamic dateString;
  bool index;
  String description;
  SupplierClass(this.id,this.date,this.dryWaste,this.wetWaste,this.index,this.description,this.dateString);
  SupplierClass.toPost(this.id,this.date,this.dryWaste,this.wetWaste,this.index,this.description);


  Map toMap()
  {
    var map = new Map<String, dynamic>();
    map["id"] = this.id;
    map["date"] = this.date;
    map["dryWaste"] = this.dryWaste;
    map["wetWaste"] = this.wetWaste;
    map['description'] = this.description;
    map['dateString'] = this.dateString;
    return map;
  }
}

List<SupplierClass> suppliers = [];
class _HomeSupplierState extends State<HomeSupplier>
{
	TextEditingController wetController = new TextEditingController();
  TextEditingController dryController = new TextEditingController();
  TextEditingController description;
  bool _autovalidate = false;
  GlobalKey<FormState> _fbKey;
  GlobalKey<ScaffoldState> _scaffoldKey;
  bool isLoading = false;
  //bool showUserDetails = false;

  Future<File> file;
  String status = '';
  String base64Image;
  File tmpFile;
  String errMessage = 'Error Uploading Image';


  Future<SupplierClass> updateRecord(dataa) async {
    var response1 = await ApiCall.updateRecord(Uri.GET_SUPPLIER+"/product/add", dataa);
    //return SupplierClass.fromJson(response1);
  }

  @override
  void initState() {
    super.initState();
    //_cityController = new TextEditingController();
    _fbKey = GlobalKey<FormState>();
    _scaffoldKey = new GlobalKey<ScaffoldState>();
    
  }


  refresh(context) {
    //print("andar to aaya");
    return FutureBuilder(
      future: getData(),
      builder: (BuildContext context, AsyncSnapshot snapshot) {
        //print("idar ab kya!");
        if (snapshot.data == null) {
          //print("gocchiii");
          return Container(
            child: Center(
              child: CircularProgressIndicator(
                semanticsLabel: "Loading 1..",
                semanticsValue: "Loading 2..",
              ),
            ),
          );
        }
        else if(snapshot.data.length==0)
        {
          return Center(
            child : Text(AppLocalizations.of(context).translate('mtMsg'),textScaleFactor:1.7,textAlign: TextAlign.center),
            );
        }
         else {
          //pringetDatat("Trying.........");
          return ListView.builder(
            itemCount: snapshot.data.length,
            itemBuilder: (BuildContext context, int index) {
              return makeCard(snapshot.data[index].date,
                  snapshot.data[index].dryWaste.toString(), snapshot.data[index].wetWaste.toString(),
                  snapshot.data[index].index
                  );
            },
          );
        }
      },
    );
  }


   getData() async {
    var response1 = await ApiCall.getDataFromApi(Uri.GET_SUPPLIER+"/"+CurrentUser.id.toString());
    //print("data arrived   ${response1.length}");
    //print(response1.length);
    if(suppliers.length!=0){
      suppliers.clear();
    }


    if(response1=='nothing')
    {
      return suppliers;
    }
    for (int i = 0; i < response1.length; i++) {
      int _id = response1[i]["id"];
      dynamic _name = response1[i]["dateString"];
      double _phone = response1[i]["dryWaste"];
      double _email = response1[i]["wetWaste"];
      bool _regd = response1[i]["addOrSub"];
      dynamic _desc = response1[i]["description"];
       SupplierClass obj = new SupplierClass(_id,_name,_phone,
        _email,_regd,_desc,"");
      suppliers.add(obj);
    }
    //print("dekh le!");
    return suppliers;
  }

  callSuccess(context) {
    Alert(
      context: context,
      type: AlertType.success,
      title: AppLocalizations.of(context).translate('signPopUpHead'),
      desc: AppLocalizations.of(context).translate('addPopUpMsg'),
      buttons: [
        DialogButton(
          child: Text(
            AppLocalizations.of(context).translate('popUpFooter'),
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          //onPressed: () => Navigator.pushNamed(context, '/HomeSupplier(main_id)'),
          onPressed : () {
            setState((){});
            Navigator.of(context, rootNavigator: true).pop();
            } ,
          width: 120,
        )
      ],
    ).show();
  }

  addData() async
  {
    print("Inn");
    setState((){isLoading = true;});
    SupplierClass s1 = new SupplierClass(CurrentUser.id,
                DateTime.now().toString().substring(0,19).trim(),
                dryController.text,wetController.text,true,description.text,"");
              print(s1.toMap());
              SupplierClass s2 = await updateRecord(s1.toMap());              
              setState((){isLoading = false;});
  }

  Widget makeCard(String title, String subtitle, String sub2,bool index) {
    return Column(
      children: [
        Card(
            elevation: 7,
            child: ListTile(
                dense: false,
                isThreeLine: true,
                contentPadding: EdgeInsets.only(left: 10.0, top: 10.0),
                title: Text(title,style: TextStyle(fontWeight : FontWeight.bold,
                  color : Colors.indigo)),
                subtitle: Text("\n\t\t\t\t"+AppLocalizations.of(context).translate('cardTxtOne')+" = "+subtitle+" Kg.\t\t\t\t"+AppLocalizations.of(context).translate('cardTxtTwo')+" = "+
                  sub2+" Kg.",
                  style : TextStyle(
                    color : Colors.brown,
                    fontSize : 13, 
                    //background : paint,
                    )
                  ),
                trailing : IconButton(
                  icon : index ? Icon(Icons.arrow_upward,color : Colors.greenAccent[400]) : Icon(Icons.arrow_downward,color : Colors.red), 
                  iconSize : 20.0,
                  ),
                )
            ),
        Divider(),
      ],
    );
  }

  bool validateWaste(String val){
    try{
      double d = double.parse(val);
      if(d>25){
        return false;
      }
      return true;
    }
    catch(e){
      return false;
    }
  }

  Future<bool> addWaste(){
    wetController = new TextEditingController();
    dryController = new TextEditingController();
    description = TextEditingController();
    return showDialog(
      context : context,
      builder : (c){
        return StatefulBuilder(
          builder : (c1,st){
            return Dialog(
              shape : RoundedRectangleBorder(
                borderRadius : BorderRadius.all(Radius.circular(16.0)),
              ),
              child : Container(
                padding : const EdgeInsets.all(16.0),
                height : 300.0,
                width : 200.0,
                decoration : BoxDecoration(
                  borderRadius : BorderRadius.circular(20.0),
                ), 
                child : Stack(
                  children : <Widget>[
                    ListView(
                      children : <Widget>[
                        Padding(
                          padding : const EdgeInsets.only(top : 16.0),
                          child : Center(child : Text(AppLocalizations.of(context).translate('addPopUpBut'),style : TextStyle(fontWeight : FontWeight.bold,fontSize : 30.0,color : Colors.green[700]))),
                        ),
                        Form(
                          key : _fbKey,
                          autovalidate :_autovalidate,
                          child : Column(
                            children : <Widget>[
                              TextFormField(
                                controller : wetController,
                                decoration : InputDecoration(
                                  labelText : AppLocalizations.of(context).translate('addPopUpWCol'),
                                  hintText : AppLocalizations.of(context).translate('addPopUpWHint'),
                                  icon : Icon(Icons.add_shopping_cart),
                                ),
                                validator : (v) => validateWaste(v) ? null : AppLocalizations.of(context).translate('addPopUpExecMsg'),
                              ),
                              TextFormField(
                                controller : dryController,
                                decoration : InputDecoration(
                                  labelText : AppLocalizations.of(context).translate('addPopUpDCol'),
                                  hintText : AppLocalizations.of(context).translate('addPopUpDHint'),
                                  icon : Icon(Icons.add_shopping_cart),
                                ),
                                validator : (v) => validateWaste(v) ? null : AppLocalizations.of(context).translate('addPopUpExecMsg'),
                              ),
                              TextFormField(
                                controller : description,
                                decoration : InputDecoration(
                                  labelText : AppLocalizations.of(context).translate('addPopUpLast'),
                                  hintText : AppLocalizations.of(context).translate('addPopUpLHint'),
                                ),
                                validator : (v) => v.isEmpty ? AppLocalizations.of(context).translate('addPopUpExecD') : null,
                              ),
                              SizedBox(height : 30.0),
                              InkWell(
                                onTap : (){
                                  if(_fbKey.currentState.validate()){
                                    addData();
                                    Navigator.of(c).pop();
                                    _autovalidate=false;
                                  }
                                  else{
                                    st((){
                                      _autovalidate = true;
                                    });
                                  }
                                },
                                child : Container(
                                  width : 175.0,
                                  height : 50.0,
                                  decoration : BoxDecoration(
                                    color : Color(0xfff5544c),
                                    borderRadius : BorderRadius.circular(10.0),
                                  ),
                                  child : Center(child : Text(AppLocalizations.of(context).translate('addPopUpBut'),style : TextStyle(color : Colors.white,fontSize : 20.0,)))
                                ),
                              ),
                            ]
                          ),
                        ),
                      ]
                    ),
                    Positioned(
                      right : 5.0,
                      //top : 5.0,
                      child : IconButton(
                        icon : Icon(Icons.clear,color : Colors.grey),
                        onPressed : (){
                          Navigator.of(c).pop();
                        },
                      ),
                    ),
                  ]
                ),
              ),
            );
          }
        );
      }
    );
  }

  void showOptionsList(){
    showModalBottomSheet(
      context : context,
      backgroundColor : Colors.transparent,
      shape : RoundedRectangleBorder(
        borderRadius : BorderRadius.all(Radius.circular(16.0)),
      ),
      builder : (c1){
        return Container(
          height : MediaQuery.of(context).size.height * 0.3,
          width : double.infinity,
          margin : const EdgeInsets.all(8.0),
          decoration : BoxDecoration(
            borderRadius : BorderRadius.circular(16.0),
            color : Colors.grey[300],
          ),
          child : Column(
            
            children : <Widget>[
              Expanded(
              child : Row(
                //crossAxisAlignment : CrossAxisAlignment.center,
                children : <Widget>[
                Expanded(
                  child : Column(
                    mainAxisAlignment : MainAxisAlignment.center,
                    children : <Widget>[
                      IconButton(
                        icon : Icon(Icons.photo_camera),
                        onPressed : () async{
                          tmpFile = await ImagePicker.pickImage(source: ImageSource.camera);  
                          if (tmpFile == null){
                            print("choose file");
                          }                          
                          else {
                            Navigator.of(context).push(
                              MaterialPageRoute(
                                builder: (_) => DisplayImage(imageFile: tmpFile,user: 'Supplier'),
                              ),
                            );
                          }   
                        },
                      ),
                      Text(AppLocalizations.of(context).translate('cameraIcon')),
                    ]
                  ),
                ),
                VerticalDivider(),
                Expanded(
                  child : Column(
                    mainAxisAlignment : MainAxisAlignment.center,
                    children : <Widget>[
                      IconButton(
                        icon : Icon(Icons.photo_library),
                        onPressed : () async{
                          tmpFile = await ImagePicker.pickImage(source: ImageSource.gallery);  
                          if (tmpFile == null){
                            print("choose file");
                          }                          
                          else {
                            Navigator.of(context).push(
                              MaterialPageRoute(
                                builder: (_) => DisplayImage(imageFile: tmpFile,user: 'Supplier'),
                              ),
                            );
                          }                        
                        },
                      ),
                      Text(AppLocalizations.of(context).translate('addIcon')),
                    ]
                  ),
                ),
                  
                  
                ]
              )),
              Divider(),
              InkWell(
                onTap : (){
                  Navigator.pop(c1);
                },
                child : Container(
                  padding : const EdgeInsets.all(8.0),
                  child : Center(child : Text(AppLocalizations.of(context).translate('cancelIcon'))),
                ),
              ),
            ]
          ),
        );
      }
    );
  }

  // Widget _buildDrawerList(){
  //   return Container(
  //     height:20,
  //     width: double.infinity,
  //     child: ListTile(
  //       title: Text("My Transactions", textScaleFactor:1.2),
  //       trailing: Icon(Icons.arrow_forward),
  //     ),
  //   );  
  // }
    
  // Widget _buildUserDetails(){
  //   List<ListTile> tiles = [];
  //   tiles.add(ListTile(
  //           title: Text("Name: "+ CurrentUser.name),
  //         ));
  //   tiles.add(ListTile(
  //           title: Text("Contact number: "+ CurrentUser.phone),
  //         ));
  //   tiles.add(ListTile(
  //           title: Text("Registration number: "+ CurrentUser.regdno),
  //         ));
  //   tiles.add(ListTile(
  //           title: Text("State name: "+ CurrentUser.state),
  //         ));
  //   tiles.add(ListTile(
  //           title: Text("City name: "+ CurrentUser.city),
  //         ));
  //   tiles.add(ListTile(
  //           title: Text("Area name: "+ CurrentUser.area),
  //         ));
  //   tiles.add(ListTile(
  //           title: Text("Street name: "+ CurrentUser.street),
  //         ));
  //   return SingleChildScrollView(
  //     child: Column(
  //       children: tiles,
  //     ),
  //   );
  // }
  Widget optionsButton(context){    
    return PopupMenuButton<int>(
      onSelected : (s){
        if(s==1){
          showSearch(context : context , delegate : DataSearch());
        }
        if(s==2){
          Navigator.pushNamed(context,'/supplier_composter_transaction');
        }
      },
      itemBuilder : (c) =>
      <PopupMenuItem<int>>[
        PopupMenuItem<int>(
          value : 1,
          child : Text(AppLocalizations.of(context).translate('optionsT1')),
        ),
        PopupMenuItem<int>(
          value : 2,
          child : Text(AppLocalizations.of(context).translate('optionsT2')),
        ),
      ]
    );
  }

	@override
	Widget build(BuildContext context) {
    return Scaffold(
//      key: _scaffoldKey,
	appBar: AppBar(
      backgroundColor : Colors.green[700],
      leading: IconButton(
        icon : Icon(Icons.power_settings_new),
        iconSize : 20.0,
        color : Colors.white,
        tooltip: 'Log out',
        onPressed: (() {
           Navigator.of(context).pop();            
        }),
      ),
      centerTitle: true,
      title: Text(AppLocalizations.of(context).translate('supHeading')),
      actions : <Widget>[
        optionsButton(context),
      ]
                 
      ),	
      
      body : IgnorePointer(
        ignoring : isLoading,
        child : Stack(
          children : <Widget>[
            refresh(context),
            isLoading ? Center(child : CircularProgressIndicator()) : Center(child : Container()),
          ]
        ),
      ),
      floatingActionButton : Row(
        mainAxisAlignment : MainAxisAlignment.end,
        children : <Widget>[
          FloatingActionButton(
            elevation : 1.0,
            backgroundColor : Color(0xfff5544c),
            onPressed : (){
              addWaste();
            },
            child : Icon(Icons.add),
            shape : RoundedRectangleBorder(
              borderRadius : BorderRadius.all(Radius.circular(16.0)),
            ),
          ),
          SizedBox(width : 10.0),
          InkWell(
            onTap : (){
              showOptionsList();
            },
            child : Container(
              width : 55.0,
              height : 55.0,
              decoration : BoxDecoration(
                color : Color(0xfff5544c),
                borderRadius : BorderRadius.circular(16.0),
              ),
              child : Icon(Icons.camera,color : Colors.white,),
            ),
          ),  

        ]
      ),
    );
  }
}




class DataSearch extends SearchDelegate<String>
{
 List<Widget> buildActions(BuildContext context)
  {
    return [
    IconButton(icon : Icon(Icons.clear),onPressed:(){
      query="";
      })
    ];
  }

  Widget buildLeading(BuildContext context)
  {
    return IconButton(
      icon : AnimatedIcon(
          icon : AnimatedIcons.menu_arrow,
          progress : transitionAnimation,
        ),
      onPressed : (){
        close(context,null);
      }
      );
  }

  Widget buildResults(BuildContext context)
  {
    return Center(
      child : Container(
        height : 100.0,
        width : 100.0,
        child : Card(
          color : Colors.red,
          child : Center(
            child : Text(query),
          ),
        )
      ),
    );
  }

  Widget buildSuggestions(BuildContext context)
  {
dynamic date;
  dynamic dryWaste,wetWaste;
  dynamic dateString;
  bool index;
  String description;
          
    final List<SupplierClass> suggestionList = query.isEmpty?suppliers:suppliers.where((p)=>p.date.startsWith(query)||
      p.dryWaste.toString().startsWith(query)||p.date.contains(query)||p.wetWaste.toString().startsWith(query)||p.dateString.startsWith(query)).toList();
    return ListView.builder(
      itemCount : suggestionList.length,
      itemBuilder : (context,index){
        return Column(
      children: [
        Card(
            elevation: 7,
            child: ListTile(
                dense: false,
                isThreeLine: true,
                title : RichText(
                  text : TextSpan(
                    text : suggestionList[index].date.substring(
                      0,
                      query.length >= suggestionList[index].date.length ? suggestionList[index].date.length : query.length
                      ),
                      style : TextStyle(
                      color : Colors.black,
                      fontWeight : FontWeight.bold,
                    ),
                    children : [
                      TextSpan(
                        text : suggestionList[index].date.substring(
                          query.length >= suggestionList[index].date.length ? suggestionList[index].date.length : query.length
                          ),
                        style : TextStyle(color : suggestionList[index].date.startsWith(query) ? Colors.grey : Colors.black),
                      ),
                    ],
                  )
                ),
                subtitle: Text("\n\t\t\t\t"+AppLocalizations.of(context).translate('cardTxtOne')+" = "+suggestionList[index].dryWaste.toString()+" Kg.\t\t\t\t"+AppLocalizations.of(context).translate('cardTxtTwo')+" = "+
                  suggestionList[index].wetWaste.toString()+" Kg.",
                  style : TextStyle(
                    color : Colors.brown,
                    fontSize : 13, 
                    )
                  ),
                trailing : IconButton(
                  icon : suggestionList[index].index ? Icon(Icons.arrow_upward,color : Colors.greenAccent[400]) : Icon(Icons.arrow_downward,color : Colors.red), 
                  iconSize : 20.0,
                  onPressed : (){},
                ),
                onTap: () {
                  //updateCountry(suggestionList[index],context);
                })),
        Divider(),
      ],
    );
      }
    );
  }
}
