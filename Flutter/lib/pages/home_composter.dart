import 'package:flutter/material.dart';

import 'package:rflutter_alert/rflutter_alert.dart';
//import 'package:flutter_form_builder/flutter_form_builder.dart';
//import 'package:flutter_custom_clippers/flutter_custom_clippers.dart';
import "../Api_Services/ApiCall.dart";
import "../Api_Services/Uri.dart";

import 'package:image_picker/image_picker.dart';
import '../app_localization.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:page_transition/page_transition.dart';

import 'image_gallery.dart';
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
import '../User/current_user.dart';
import 'display_img.dart';
import 'dart:io';


//Login aj nathi thatu
 //kemke server band 4 vagi gaya 
 //naa ruk ek sec

class MyHomeComposter extends StatelessWidget {
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
          case '/maps' : 
            return PageTransition(child : DisplayMap(),type : PageTransitionType.rightToLeft);
            break;
        }
      }
    );
  }
}


class HomeComposter extends StatefulWidget
{
	@override
  _HomeComposterState createState() => _HomeComposterState();

}

class ComposterClass
{
  int id;
  dynamic name;
  dynamic dryWaste,wetWaste;
  bool index;
  String description;
  dynamic contactNumber,emailId,registrationNumber,date_time,sstate,city,latitude,longitude,dateString;
  ComposterClass(this.id,this.name,this.dryWaste,this.wetWaste,this.index,this.contactNumber,this.emailId,this.registrationNumber
    ,this.date_time,this.sstate,this.city,this.latitude,this.longitude,this.dateString,this.description);
 

  Map toMap()
  {
    var map = new Map<String, dynamic>();
    map["name"] = name;
    map["dryWaste"] = dryWaste;
    map["wetWaste"] = wetWaste;
    return map;
  }
}

class ComposterClass2
{
  int id,su_id;
  dynamic date;
  dynamic dryWaste,wetWaste;

  dynamic prices,weights,dateString;
  String descriptions,grades,categorys;

  ComposterClass2.addCompost(this.prices,this.weights,this.descriptions,this.grades,this.categorys,this.dateString);

  ComposterClass2(this.su_id,this.date,this.dryWaste,this.wetWaste,this.descriptions,this.dateString);

  factory ComposterClass2.fromJson(Map<String,dynamic> json)
  {
    dynamic _date=json["name"];
    double _dryWaste = json["dryWaste"];
    double _wetWaste = json["wetWaste"];

  }

  Map toMap()
  {
    var map = new Map<String, dynamic>();
    map["id"] = su_id;
    map["date"] = date;
    map["dryWaste"] = dryWaste;
    map["wetWaste"] = wetWaste;
    map["description"] = descriptions;
    map["dateString"] = dateString;
    return map;
  }
}

List<ComposterClass> composters = [];

class _HomeComposterState extends State<HomeComposter>
{
	TextEditingController wetController = new TextEditingController() ;
  TextEditingController dryController = new TextEditingController();
  bool _autovalidate = false;
  bool isLoading = false;
  GlobalKey<FormState> _fbKey;
  GlobalKey<ScaffoldState> _scaffoldKey;
  String ddate=DateTime.now().toString().substring(0,10);
  File tmpFile;

  TextEditingController price,weight,category,grade,description;

  Future<ComposterClass2> createRecord(dataa) async {
    var response1 = await ApiCall.updateRecord(Uri.GET_SUPPLIER+"/product/sub?composterId=${CurrentUser.id}&searchDate=${ddate}", dataa);
    //return ComposterClass2.fromJson(response1);
  } 

  refresh(context,date) {
    print("andar to aaya");
    return FutureBuilder(
      future: getData(date),
      builder: (BuildContext context, AsyncSnapshot snapshot) {
        print("idar ab kya!");

        if(snapshot.data == null) {
          print("gocchiii");
          return Container(
            child: Center(
              child: CircularProgressIndicator(
                semanticsLabel: "Loading 1..",
                semanticsValue: "Loading 2..",
              ),
            ),
          );
          print("out");
        }
        else if(snapshot.data.length == 0){
          return Center(
            child : Text(AppLocalizations.of(context).translate('emptyMsg'),textScaleFactor:1.7, textAlign: TextAlign.center),
            );
        }         
        else{
          print("Trying.........");
          return ListView.builder(
            itemCount: snapshot.data.length,
            itemBuilder: (BuildContext context, int index) {
              return makeCard(snapshot.data[index],date);
            },
          );
        }
      },
    );
  }


  Future<List<ComposterClass>> getData(date) async {
    var response1 = await ApiCall.getDataFromApi(Uri.GET_SUPPLIER+"/display?date="+
      date);

    if(composters.length!=0){
      composters.clear();
    }
    if(response1 == "nothing"){
      return composters;
    }
    for (int i = 0; i < response1.length; i++) {
      int _id = response1[i]["id"];
      dynamic _name = response1[i]["name"];
      double _phone = response1[i]["dryWaste"];
      double _email = response1[i]["wetWaste"];
      bool _regd = response1[i]["addOrSub"];
      //dynamic contactNumber,emailId,registrationNumber,date_time,sstate,city,latitude,longitude;
      dynamic _contactNumber = response1[i]["contactNumber"];
      dynamic _emailId = response1[i]["emailId"];
      dynamic _registrationNumber = response1[i]["registrationNumber"];
      dynamic _date_time = response1[i]["date_time"];
      dynamic _sstate = response1[i]["state"];
      dynamic _city = response1[i]["city"];
      dynamic _latitude = response1[i]["latitude"];
      dynamic _longitude = response1[i]["longitude"];
      dynamic _dateString = response1[i]["dateString"];
      String d= response1[i]['description'];
       ComposterClass obj = new ComposterClass(_id,_name,_phone,
        _email,_regd,_contactNumber,_emailId,_registrationNumber,_date_time,_sstate,_city,_latitude,_longitude,_dateString,d);
      composters.add(obj);
    }
    print("dekh le!");
    return composters;
  }


  callError(context,str) {
    Alert(
      context: context,
      type: AlertType.error,
      title: "Err.!!",
      desc: str,
      buttons: [
        DialogButton(
          child: Text(
            "Okay",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () => Navigator.of(context, rootNavigator: true).pop(),
          width: 120,
        )
      ],
    ).show();
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
          onPressed: () 
          {
            setState((){});
            Navigator.of(context, rootNavigator: true).pop();
          } ,
          width: 120,
        )
      ],
    ).show();
  }
//String title, String subtitle, String sub2,int su_id
  Widget makeCard(var obj, date) {
    return Column(
      children: [
        Card(
            elevation: 7,
            
            child: ListTile(
                dense: false,
                trailing : Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    IconButton(
                      icon : Icon(Icons.photo_library),
                      iconSize : 30.0,
                      onPressed :(){  
                        print("Images will be shown");
                        Navigator.of(context).push(
                          MaterialPageRoute(
                            builder: (_) => ImageGallery(obj.id,date,""),
                          ),
                        );
                      }
                    ),
                    IconButton(
                      icon : Icon(Icons.info_outline),
                      iconSize : 30.0,
                      onPressed :(){ 
                        _settingModalBottomSheet(context,obj);
                      }
                    ),
                  ],
                ),                              
                onTap : ()
                {
                  buyWaste(obj.name,obj.dryWaste,obj.wetWaste,obj.id,obj.description);
                },
                isThreeLine: true,
                contentPadding: EdgeInsets.only(left: 10.0, top: 10.0),
                title: Text(obj.name,style: TextStyle(fontWeight : FontWeight.bold,
                  color : Colors.indigo)),
                subtitle: Text("\n\t\t\t\t"+AppLocalizations.of(context).translate('cardTxtOne')+" = "+obj.dryWaste.toString()+" Kg.\t\t\t\t"+AppLocalizations.of(context).translate('cardTxtTwo')+" = "+
                  obj.wetWaste.toString()+" Kg.",
                  style : TextStyle(
                    color : Colors.brown,
                    fontSize: 13,
                    )
                  ),
                
                )),
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

  Future<bool> buyWaste(name,dry,wet,id,description){
    wetController = new TextEditingController();
    dryController = new TextEditingController();
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
                          child : Center(child : Text(name,style : TextStyle(fontWeight : FontWeight.bold,fontSize : 26.0,))),
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
                                  hintText : AppLocalizations.of(context).translate('addPopUpWHint')+" (max $wet kg).",
                                  icon : Icon(Icons.add_shopping_cart),
                                ),
                                
                                validator : (v){
                                  bool validate = validateWaste(v);
                                  if(validate){
                                    if(double.parse(v)>wet){
                                      return 'Max $wet Kg.';
                                    }
                                    else{
                                      return null;
                                    }
                                  }
                                  else{
                                    return AppLocalizations.of(context).translate('errMsg');
                                  }
                                },
                              ),
                              TextFormField(
                                controller : dryController,
                                decoration : InputDecoration(
                                  labelText : AppLocalizations.of(context).translate('addPopUpDCol'),
                                  hintText : AppLocalizations.of(context).translate('addPopUpDHint')+" (max $dry kg).",
                                  icon : Icon(Icons.add_shopping_cart),
                                ),
                                validator : (v){
                                  bool validate = validateWaste(v);
                                  if(validate){
                                    if(double.parse(v)>dry){
                                      return 'Max $dry Kg.';
                                    }
                                    else{
                                      return null;
                                    }
                                  }
                                  else{
                                    return AppLocalizations.of(context).translate('errMsg');
                                  }
                                },
                              ),
                              SizedBox(height : 30.0),
                              InkWell(
                                onTap : (){
                                  if(_fbKey.currentState.validate()){
                                    addData(id,description);
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
                                  width : 190.0,
                                  height : 50.0,
                                  decoration : BoxDecoration(
                                    color : Theme.of(context).accentColor,
                                    borderRadius : BorderRadius.circular(5.0),
                                  ),
                                  child : Center(child : Text(AppLocalizations.of(context).translate('comPopUpHead'),textAlign: TextAlign.center, style : TextStyle(color : Colors.white,fontSize : 20.0,)))
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

  void _settingModalBottomSheet(context,obj){
    showModalBottomSheet(
      context: context,
      builder: (BuildContext bc){
          return SingleChildScrollView(
          child : Container(
            child: new Wrap(
            children: <Widget>[
            new ListTile(
            //leading: new Icon(Icons.music_note),
            title: new Text(AppLocalizations.of(context).translate('signUpF1')+": "+obj.name),
            onTap: () => {}          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('addPopUpDCol')+": "+obj.dryWaste.toString()),
            onTap: () => {},          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('addPopUpWCol')+": "+obj.wetWaste.toString()),
            onTap: () => {},          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('signUpF3')+": "+obj.contactNumber.toString()),
            onTap: () => {},          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('signUpF4')+": "+obj.emailId.toString()),
            onTap: () => {},          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('signUpF5')+": "+obj.registrationNumber.toString()),
            onTap: () => {},          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('lastAddDate')+": "+obj.dateString.toString()),
            onTap: () => {},          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('signUpF7')+": "+obj.city.toString()),
            onTap: () => {},          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('signUpF6')+": "+obj.sstate.toString()),
            onTap: () => {},          
          ),
          
          RaisedButton(
              onPressed: () {},
              color: Theme.of(context).accentColor,
              child: Padding(
                padding: EdgeInsets.fromLTRB(
                    10,
                    15,
                    10,
                    15),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(
                      AppLocalizations.of(context).translate('mapDisp'),
                      style: TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.w700,
                        color: Colors.white,
                      ),
                    ),
                    IconButton(
                      icon : Icon(Icons.location_on),
                      color: Colors.red ,
                      iconSize : 40.0,
                      onPressed : (){
                        //print("Hi");
                        Navigator.pushNamed(context, '/maps',arguments : obj);
                        },
                    )
                  ],
                ),
              ),
            ),

          new Container(
            height : 10.0,
            ),
          
            ],
          ),
          ));
      }
    );
}
//dynamic contactNumber,emailId,registrationNumber,date_time,sstate,city,latitude,longitude;
  @override
  void initState() {
    super.initState();
    //_cityController = new TextEditingController();
    _fbKey = GlobalKey<FormState>();
    _scaffoldKey = new GlobalKey<ScaffoldState>();
    
  }

  addData(su_id,description) async
  {
    setState((){isLoading = true;});
    ComposterClass2 c1 = new ComposterClass2(su_id,
                  DateTime.now().toString().substring(0,19),
                double.parse(dryController.text),double.parse(wetController.text),description,"");
              print(c1.toMap());
              ComposterClass2 c2 = await createRecord(c1.toMap());
              setState((){isLoading = false;});
  }

  

  Future _chooseDate(BuildContext context) async 
  {
    var now = new DateTime.now();
    var result = await showDatePicker(
        context: context,
        initialDate: now,
        firstDate: new DateTime(1900),
        lastDate: new DateTime.now());

    if (result == null) return;
    String datee=result.toString().substring(0,10);
    refresh(context,datee);
    setState((){
      ddate=datee;
      });
    print(result);
  }

  Future<bool> addWaste(){
    _autovalidate = false;
    price = TextEditingController();
    weight = TextEditingController();
    category = TextEditingController();
    grade = TextEditingController();
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
                          child : Center(child : Text(AppLocalizations.of(context).translate('addIcon'),style : TextStyle(fontWeight : FontWeight.bold,fontSize : 30.0, color: Colors.green[600]))),
                        ),
                        Form(
                          key : _fbKey,
                          autovalidate :_autovalidate,
                          child : Column(
                            children : <Widget>[
                              TextFormField(
                                controller : category,
                                decoration : InputDecoration(
                                  labelText : AppLocalizations.of(context).translate('categoryLab'),
                                  hintText : AppLocalizations.of(context).translate('categoryHint'),
                                ),
                                validator : (v) => v.isEmpty ? AppLocalizations.of(context).translate('categoryVal') : null,
                              ),
                              TextFormField(
                                controller : grade,
                                decoration : InputDecoration(
                                  labelText : AppLocalizations.of(context).translate('gradeLab'),
                                  hintText : AppLocalizations.of(context).translate('gradeHint'),
                                ),
                                validator : (v) => v.isEmpty ? AppLocalizations.of(context).translate('gradeVal') : null,
                              ),
                              TextFormField(
                                controller : price,
                                decoration : InputDecoration(
                                  labelText : AppLocalizations.of(context).translate('priceLab'),
                                  hintText : AppLocalizations.of(context).translate('priceHint'),
                                ),
                                validator : (v){
                                  try{
                                    double w = double.parse(v);
                                    return null;
                                  }
                                  catch(e){
                                    return AppLocalizations.of(context).translate('errMsg');
                                  }
                                },
                              ),
                              TextFormField(
                                controller : weight,
                                decoration : InputDecoration(
                                  labelText : AppLocalizations.of(context).translate('weightLab'),
                                  hintText : AppLocalizations.of(context).translate('weightHint'),
                                ),
                                validator : (v){
                                  try{
                                    double w = double.parse(v);
                                    return null;
                                  }
                                  catch(e){
                                    return AppLocalizations.of(context).translate('errMsg');
                                  }
                                },
                              ),
                              TextFormField(
                                controller : description,
                                decoration : InputDecoration(
                                  labelText : AppLocalizations.of(context).translate('addPopUpLast'),
                                  hintText : AppLocalizations.of(context).translate('descHint'),
                                ),
                                validator : (v) => v.isEmpty ? AppLocalizations.of(context).translate('addPopUpExecD') : null,
                              ),
                              SizedBox(height : 30.0),
                              InkWell(
                                onTap : (){
                                  if(_fbKey.currentState.validate()){
                                    addCompost();
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
                                    color : Color(0xfff55443),
                                    borderRadius : BorderRadius.circular(10.0),
                                  ),
                                  child : Center(child : Text(AppLocalizations.of(context).translate('floatButSuj'), style : TextStyle(color : Colors.white,fontSize : 20.0,)))
                                ),
                              ),
                            ]
                          ),
                        ),
                      ]
                    ),
                    Positioned(
                      right : 0.1,
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

  addCompost() async{
    ComposterClass2 obj = ComposterClass2.addCompost(
      double.parse(price.text),
      double.parse(weight.text),
      description.text,
      grade.text,
      category.text,
      ""
    );

    var data = Map<String,dynamic>();
    data['id'] = CurrentUser.id;
    data['dateAndTime'] = DateTime.now().toString().substring(0,19);
    data['price'] = obj.prices;
    data['compostWeight'] = obj.weights;
    data['category'] = obj.categorys;
    data['grade'] = obj.grades;
    data['description'] = obj.descriptions;
    data['dateString'] = obj.dateString;
    var r = await addCompostRecord(data);
  }

  addCompostRecord(data) async{
    var r = await ApiCall.createRecord(Uri.GET_COMPOSTER+'/compost/add',data);
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
                                builder: (_) => DisplayImage(imageFile: tmpFile,user: 'Composter'),
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
                                builder: (_) => DisplayImage(imageFile: tmpFile,user: 'Composter'),
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


	@override
	Widget build(BuildContext context) {
    return Scaffold(
//      key: _scaffoldKey,
	appBar: AppBar(
      leading: IconButton(
        icon : Icon(Icons.power_settings_new),
        iconSize : 20.0,
        color : Colors.white,
        tooltip: 'Log out',
        onPressed: (() {
           Navigator.of(context).pop();            
        }),
      ),
      actions : <Widget>[
      PopupMenuButton<int>(
        onSelected : (s){
          if(s==1){
            _chooseDate(context);
          }
          else if(s==2){
            showSearch(context : context , delegate : DataSearch());
          }
          else{
            Navigator.pushNamed(context,'/composter_farmer_transaction');
          }

        },
        itemBuilder : (c) =>
        <PopupMenuItem<int>>[
          PopupMenuItem<int>(
            value : 1,
            child : Text(AppLocalizations.of(context).translate('dateIconSel')),
          ),
          PopupMenuItem<int>(
            value : 2,
            child : Text(AppLocalizations.of(context).translate('optionsT1')),
          ),
          PopupMenuItem<int>(
            value : 3,
            child : Text(AppLocalizations.of(context).translate('optionsT2')),
          ),
        ]
      ),
      ],
      centerTitle: true,
        title: Text(AppLocalizations.of(context).translate('comHeading')),
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
            refresh(context,ddate),
            isLoading ? Center(child : CircularProgressIndicator()) : Center(child : Container()),
          ]
        ),
      ),
      floatingActionButton : Row(
        mainAxisAlignment : MainAxisAlignment.end,
        children : <Widget>[
          FloatingActionButton(
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
                color :  Color(0xfff5544c),
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
          color : Colors.white,
          child : Center(
            child : Text(query),
          ),
        )
      ),
    );
  }

  void _settingModalBottomSheet(context,obj){
    showModalBottomSheet(
      context: context,
      builder: (BuildContext bc){
          return SingleChildScrollView(
          child : Container(
            child: new Wrap(
            children: <Widget>[
            new ListTile(
            //leading: new Icon(Icons.music_note),
            title: new Text(AppLocalizations.of(context).translate('signUpF1')+": "+obj.name),
            onTap: () => {}          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('addPopUpDCol')+": "+obj.dryWaste.toString()),
            onTap: () => {},          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('addPopUpWCol')+": "+obj.wetWaste.toString()),
            onTap: () => {},          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('signUpF3')+": "+obj.contactNumber.toString()),
            onTap: () => {},          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('signUpF4')+": "+obj.emailId.toString()),
            onTap: () => {},          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('signUpF5')+": "+obj.registrationNumber.toString()),
            onTap: () => {},          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('lastAddDate')+": "+obj.dateString.toString()),
            onTap: () => {},          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('signUpF7')+": "+obj.city.toString()),
            onTap: () => {},          
          ),
          new ListTile(
            //leading: new Icon(Icons.videocam),
            title: new Text(AppLocalizations.of(context).translate('signUpF6')+": "+obj.sstate.toString()),
            onTap: () => {},          
          ),
          
          RaisedButton(
              onPressed: () {},
              color: Theme.of(context).accentColor,
              child: Padding(
                padding: EdgeInsets.fromLTRB(
                    10,
                    15,
                    10,
                    15),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(
                      AppLocalizations.of(context).translate('mapDisp'),
                      style: TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.w700,
                        color: Colors.white,
                      ),
                    ),
                    IconButton(
                      icon : Icon(Icons.location_on),
                      color: Colors.red ,
                      iconSize : 40.0,
                      onPressed : (){
                        //print("Hi");
                        Navigator.pushNamed(context, '/maps',arguments : obj);
                        },
                    )
                  ],
                ),
              ),
            ),

          new Container(
            height : 10.0,
            ),
          
            ],
          ),
          ));
      }
    );
}

  Widget buildSuggestions(BuildContext context)
  {
  dynamic name;
  dynamic dryWaste,wetWaste;
  bool index;
  dynamic contactNumber,emailId,registrationNumber,date_time,sstate,city,latitude,longitude,dateString;
          
    final List<ComposterClass> suggestionList = query.isEmpty?composters:composters.where((p)=>p.name.startsWith(query)||
      p.dryWaste.toString().startsWith(query)||p.contactNumber.startsWith(query)||p.wetWaste.toString().startsWith(query)||
      p.dateString.contains(query)||p.emailId.startsWith(query)||p.city.startsWith(query)||p.sstate.startsWith(query)).toList();
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
                    text : suggestionList[index].name.substring(
                      0,
                      query.length >= suggestionList[index].name.length ? suggestionList[index].name.length : query.length
                      ),
                      style : TextStyle(
                      color : Colors.black,
                      fontWeight : FontWeight.bold,
                    ),
                    children : [
                      TextSpan(
                        text : suggestionList[index].name.substring(
                          query.length >= suggestionList[index].name.length ? suggestionList[index].name.length : query.length
                          ),
                        style : TextStyle(color : suggestionList[index].name.startsWith(query) ? Colors.grey : Colors.black),
                      ),
                    ],
                  )
                ),
                subtitle: Text("\n\t\t\t\t"+AppLocalizations.of(context).translate('cardTxtOne')+" = "+suggestionList[index].dryWaste.toString()+" Kg.\t\t\t\t"+AppLocalizations.of(context).translate('cardTxtTwo')+" = "+
                  suggestionList[index].wetWaste.toString()+" Kg.",
                  style : TextStyle(
                    color : Colors.brown,
                    fontSize: 13,
                    )
                  ),
                trailing : Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    IconButton(
                      icon : Icon(Icons.photo_library),
                      iconSize : 30.0,
                      onPressed :(){  
                        print("Images will be shown");
                        Navigator.of(context).push(
                          MaterialPageRoute(
                            builder: (_) => ImageGallery(suggestionList[index].id,DateTime.now().toString().substring(0,19),""),
                          ),
                        );
                      }
                    ),
                    IconButton(
                      icon : Icon(Icons.info_outline),
                      iconSize : 30.0,
                      onPressed :(){ 
                        _settingModalBottomSheet(context,suggestionList[index]);
                      }
                    ),
                  ],
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
