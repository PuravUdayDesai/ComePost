import 'package:flutter/material.dart';

import 'package:rflutter_alert/rflutter_alert.dart';
//import 'package:flutter_form_builder/flutter_form_builder.dart';

//import 'SignUpSupplier.dart';
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
import '../User/current_user.dart';
import 'package:toast/toast.dart';

import 'package:image_picker/image_picker.dart';
import 'image_gallery.dart';


class MyHomeFarmer extends StatelessWidget {
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


class HomeFarmer extends StatefulWidget
{
	@override
  _HomeFarmerState createState() => _HomeFarmerState();
}

class FarmerClass
{
  int id;
  int initId;
  String name;
  String contact;
  String email;
  String regNo;
  bool compostAddSub;
  dynamic price;
  dynamic weight;
  String dateTime;
  String latitude;
  String longitude;
  String street;
  String area;
  String city;
  String state;
  String dateString;

  dynamic prices,weights;
  String descriptions,grades,categorys;
  FarmerClass(
      this.id,
      this.initId,
      this.name,
      this.contact,
      this.email,
      this.regNo,
      this.compostAddSub,
      this.price,
      this.weight,
      this.dateTime,
      this.latitude,
      this.longitude,
      this.street,
      this.area,
      this.city,
      this.state,
      this.dateString
    );

  FarmerClass.bookCompost(
    this.prices,
    this.weights,
    this.descriptions,
    this.grades,
    this.categorys
  );

}

List<FarmerClass> suppliers = [];
class _HomeFarmerState extends State<HomeFarmer>
{
	TextEditingController wetController = new TextEditingController();
  TextEditingController dryController = new TextEditingController();
  bool _autovalidate = false;
  GlobalKey<FormState> _fbKey;
  GlobalKey<ScaffoldState> _scaffoldKey;
  String mainDate=DateTime.now().toString().substring(0,10);

  TextEditingController price,weight,category,grade,description;


  Future<FarmerClass> updateRecord(dataa) async {
    var response1 = await ApiCall.updateRecord(Uri.GET_SUPPLIER+"/product/add", dataa);
    //return SupplierClass.fromJson(response1);
}

  refresh(context) {
    print("andar to aaya");
    return FutureBuilder(
      future: getData(),
      builder: (BuildContext context, AsyncSnapshot snapshot) {
        print(snapshot.data);
        if (snapshot.data == null) {
          print("gocchiii");
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
            child : Text(AppLocalizations.of(context).translate('emptyMsg'),textScaleFactor:1.7,textAlign: TextAlign.center),
            );
        }
         else {
          print("Trying.........");
          return ListView.builder(
            itemCount: snapshot.data.length,
            itemBuilder: (BuildContext context, int index) {
              return makeCard(snapshot.data[index]);
            },
          );
        }
      },
    );
  }


   getData() async {   
    var json = await ApiCall.getDataFromApi(Uri.GET_COMPOSTER+"/display?date=$mainDate");

    print(json);
    
    if(suppliers.length!=0){
      suppliers.clear();
    }
    if(json == 'nothing')
    {
      print("Nooo");
      return suppliers;
    }
    for (int i = 0; i < json.length; i++) {
      int _id = json[i]['id'];
      int _initId = json[i]['initId'];
      String _name = json[i]['name'];
      String _contact = json[i]['contact'];
      String _email = json[i]['email'];
      String _regNo = json[i]['reg_no'];
      bool _compostAddSub = json[i]['compost_add_sub'];
      dynamic _price = json[i]['price'];
      dynamic _weight = json[i]['weight'];
      String _dateTime = json[i]['date_time'];
      String _latitude = json[i]['latitude'];
      String _longitude = json[i]['longitude'];
      String _street = json[i]['street'];
      String _area = json[i]['area'];
      String _city = json[i]['city'];
      String _state = json[i]['state'];
      String _dateString = json[i]['dateString'];

      

       FarmerClass obj =FarmerClass(
        _id,
        _initId,
        _name,
        _contact,
        _email,
        _regNo,
        _compostAddSub,
        _price,
        _weight,
        _dateTime,
        _latitude,
        _longitude,
        _street,
        _area,
        _city,
        _state,
        _dateString
        );
       print('fhieh');
      suppliers.add(obj);
    }
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
          //onPressed: () => Navigator.pushNamed(context, '/`(main_id)'),
          onPressed : () {
            setState((){});
            Navigator.of(context, rootNavigator: true).pop();
            } ,
          width: 120,
        )
      ],
    ).show();
  }

/*  AddData() async
  {
    SupplierClass s1 = new SupplierClass(main_id,
                DateTime.now().toString().substring(0,19).trim(),
                dryController.text,wetController.text,true);
              print(s1.toMap());
              SupplierClass s2 = await updateRecord(s1.toMap());
             
              setState((){});
  }*/

  Widget makeCard(var obj) {
    return Column(
      children: [
        Card(
            elevation: 7,
            child: ListTile(
              onTap : ()
                {
                  bookCompostPopup(obj);
                },
                dense: false,
                isThreeLine: true,
                contentPadding: EdgeInsets.only(left: 10.0, top: 10.0),
                title: Text(""+obj.name,style: TextStyle(fontWeight : FontWeight.bold,
                  color : Colors.indigo)),
                subtitle: Text("\n\t\t\t"+AppLocalizations.of(context).translate('priceLab')+" = "+obj.price.toString()+"/Kg.\t "+AppLocalizations.of(context).translate('quanLab')+" = "+
                  obj.weight.toString()+" Kg.",
                  style : TextStyle(
                    color : Colors.brown,
                    //background : paint,
                    )),
                trailing : IconButton(
                      icon : Icon(Icons.photo_library),
                      iconSize : 30.0,
                      onPressed :(){  
                        print("Images will be shown");
                        Navigator.of(context).push(
                          MaterialPageRoute(
                            builder: (_) => ImageGallery(obj.id,DateTime.now().toString().substring(0,19),"farmer"),
                          ),
                        );
                      }
                    ),
                )),
        Divider(),
      ],
    );
  }

  @override
  void initState() {
    super.initState();
    //_cityController = new TextEditingController();
    _fbKey = GlobalKey<FormState>();
    _scaffoldKey = new GlobalKey<ScaffoldState>();
    
  }

  func(var obj)
  {
    //wetController.text = subtitle;
    //dryController.text = sub2;
    String maxWeight = obj.weight.toString();
    wetController = new TextEditingController();
    dryController = new TextEditingController();
    Alert(
        context: context,
        title: AppLocalizations.of(context).translate('farmHead'),
        content:Form(
        key: _fbKey,
          autovalidate: _autovalidate, 
        child : Column(
          children: <Widget>[
            TextFormField(
              keyboardType: TextInputType.phone,
              controller : wetController, 
              decoration: InputDecoration(
                icon: Icon(Icons.add_shopping_cart),
                labelText: 'Compost Needed',
                hintText : ' Max $maxWeight (in Kg)'
              ),
              
            ),
            /*FormBuilderTextField(
              keyboardType: TextInputType.phone,
              controller : dryController,
              decoration: InputDecoration(
                icon: Icon(Icons.add_shopping_cart),
                labelText: 'Dry Waste',
                hintText : 'Max $sub2 (in Kg)'
              ),
              validators: [FormBuilderValidators.required()],
            ),*/
          ],
        )),
        buttons: [
          DialogButton(
            onPressed: () {
              
              
              //  try
              // {
              //   double a=double.parse(wetController.text);
              //   double b=double.parse(subtitle);
              //   double c=double.parse(dryController.text);
              //   double d=double.parse(sub2);

              // //   ComposterClass2 c1 = new ComposterClass2(0,su_id,
              // //     DateTime.now().toString().substring(0,19),
              // //   double.parse(dryController.text),double.parse(wetController.text));
              // // print(c1.toMap());
              // // ComposterClass2 c2 = await createRecord(c1.toMap());
              // // //Navigator.pop(context);
              // // callSuccess(context);
              // if(double.parse(wetController.text)<=double.parse(subtitle)&&
              //   double.parse(dryController.text)<=double.parse(sub2))
              // {
              //   AddData(su_id);
              //   Navigator.of(context, rootNavigator: true)
              //                           .pop();

              //   callSuccess(context);
              //   _autovalidate = false;
              // }
              // else
              // {
              //   callError(context,"Please Enter Weight that is available..");
              // }
              // }
              // catch(e)
              // {
              //   //_autovalidate = true;
              //   callError(context,"Please Enter all fields or (numeric only) ..");
              // }
              

              
              },
            child: Text(
              "Buy now",
              style: TextStyle(color: Colors.white, fontSize: 20),
            ),
          )
        ]).show();
  }

   Future<bool> bookCompostPopup(obj){
    _autovalidate = false;
    price = TextEditingController();
    weight = TextEditingController();
    category = TextEditingController();
    grade = TextEditingController();
    description = TextEditingController();
    price.text = obj.price.toString();
    price.text = price.text + ' /Kg.';
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
                          child : Center(child : Text(AppLocalizations.of(context).translate('addIcon'),style : TextStyle(fontWeight : FontWeight.bold,fontSize : 30.0,color: Colors.green[700]))),
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
                                enabled : false,
                                controller : price,
                                decoration : InputDecoration(
                                  labelText : AppLocalizations.of(context).translate('priceLab'),
                                  hintText : AppLocalizations.of(context).translate('priceHint'),
                                ),
                              ),
                              TextFormField(
                                controller : weight,
                                decoration : InputDecoration(
                                  labelText : AppLocalizations.of(context).translate('weightLab'),
                                  hintText : AppLocalizations.of(context).translate('weightHint')+'(Max ${obj.weight} Kg).',
                                ),
                                validator : (v){
                                  try{
                                    double w = double.parse(v);
                                    if(w<=obj.weight){
                                      return null;
                                    }
                                    else{
                                      return 'Max ${obj.weight} Kg.';
                                    }
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
                                    bookCompost(obj);
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
                                  child : Center(child : Text(AppLocalizations.of(context).translate('bookBut'),style : TextStyle(color : Colors.white,fontSize : 20.0,)))
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

  bookCompost(obj) async{

    var data = Map<String,dynamic>();
    data['id'] = obj.id;
    data['dateAndTime'] = DateTime.now().toString().substring(0,19);
    data['price'] = obj.price;
    data['compostWeight'] = weight.text;
    data['category'] = category.text;
    data['grade'] = grade.text;
    data['description'] = description.text;
    data['dateString'] = "";
    var r = await bookCompostRecord(data,obj.initId);
  }

  bookCompostRecord(data,id) async{
    print("Data");
    print(data);
    var bill = await ApiCall.updateRecord(Uri.GET_COMPOSTER+'/compost/sub/${id}?farmerId=${CurrentUser.id}&searchDate=${mainDate}',data);
    print("Bill");
    print(bill);
    Toast.show("Your bill is "+bill.toString(), context,
          duration: Toast.LENGTH_LONG, gravity: Toast.BOTTOM);
      setState((){});
  }
  //

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
    //refresh(context,datee);
    setState((){
      mainDate=datee;
      });
    print(result);
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
          else{
            showSearch(context : context , delegate : DataSearch());
          }
        },
        itemBuilder : (c)=>
          <PopupMenuItem<int>>[ 
            PopupMenuItem<int>(
              value : 1,
              child : Text(AppLocalizations.of(context).translate('dateIconSel')),
            ),
            PopupMenuItem<int>(
              value : 2,
              child : Text(AppLocalizations.of(context).translate('optionsT1')),
            ),
          ]
      ),
      ],
      centerTitle: true,
        title: Text(AppLocalizations.of(context).translate('supHeading')),
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
      body: refresh(context),

      
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

    int id;
  int initId;
  String name;
  String contact;
  String email;
  String regNo;
  bool compostAddSub;
  dynamic price;
  dynamic weight;
  String dateTime;
  String latitude;
  String longitude;
  String street;
  String area;
  String city;
  String state;
  String dateString;

          
    final List<FarmerClass> suggestionList = query.isEmpty?suppliers:suppliers.where((p)=>p.name.startsWith(query)||
      p.contact.toString().startsWith(query)||p.dateString.contains(query)||
      p.price.toString().startsWith(query)||
      p.weight.toString().startsWith(query)||
      p.city.startsWith(query)||
      p.state.startsWith(query)||
      p.area.startsWith(query)||
      p.street.startsWith(query)||
      p.dateString.startsWith(query)).toList();
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
                subtitle: Text("\n\t\t\t"+AppLocalizations.of(context).translate('priceLab')+" = "+suggestionList[index].price.toString()+"/Kg.\t "+AppLocalizations.of(context).translate('quanLab')+" = "+
                  suggestionList[index].weight.toString()+" Kg.",
                  style : TextStyle(
                    color : Colors.brown,
                    //background : paint,
                    )),
                trailing : IconButton(
                      icon : Icon(Icons.photo_library),
                      iconSize : 30.0,
                      onPressed :(){  
                        print("Images will be shown");
                        Navigator.of(context).push(
                          MaterialPageRoute(
                            builder: (_) => ImageGallery(suggestionList[index].id,DateTime.now().toString().substring(0,19),"farmer"),
                          ),
                        );
                      }
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

