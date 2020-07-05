import 'package:flutter/material.dart';


import '../User/current_user.dart';
import '../Api_Services/ApiCall.dart';
import '../Api_Services/Uri.dart';

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

class MySCT extends StatelessWidget {
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
      home: CFT(),
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


class CFT extends StatefulWidget{
	@override
	_CFTState createState() => _CFTState();
}

class CFTClass{
	int incrementId;
	  int composterCompostInitId;
	  int composterId;
	  int farmerId;
	  String farmerName;
	  String farmerContact;
	  String dateAndTime;
	  String dateString;
	  String category;
	  String grade;
	  dynamic price;
	  dynamic compostWeigth;
	  dynamic totalPrice;


	  CFTClass(
	  	this.incrementId,
      this.composterCompostInitId,
      this.composterId,
      this.farmerId,
      this.farmerName,
      this.farmerContact,
      this.dateAndTime,
      this.dateString,
      this.category,
      this.grade,
      this.price,
      this.compostWeigth,
      this.totalPrice
	  );
}

class _CFTState extends State<CFT>{


	refresh(context){
		return FutureBuilder(
			future : getData(),
			builder : (c,s){
				if(s.data==null){
					return Center(
						child : CircularProgressIndicator(),
					);
				}
				else{
					if(s.data.length==0){
						return Center(child:Text(AppLocalizations.of(context).translate('mtMsg'),textScaleFactor:1.7),);
					}
					else{
						return ListView.builder(
							itemCount : s.data.length,
							itemBuilder : (c,i){
								return makeCard(s.data[i]);
							}
						);
					}
				}
			}
		);
	}


	Future<List<CFTClass>> getData() async{
		var json = await ApiCall.getDataFromApi(Uri.GET_COMPOSTER_FARMER_TRANSACTION+'/${CurrentUser.id}');
		List<CFTClass> obj=[];

		if(json=='nothing'){
			return obj;
		}
		print(json);

		for(int i=0;i<json.length;i++){
			int _incrementId = json[i]['incrementId'];
		    int _composterCompostInitId = json[i]['composterCompostInitId'];
		    int _composterId = json[i]['composterId'];
		    int _farmerId = json[i]['farmerId'];
		    String _farmerName = json[i]['farmerName'];
		    String _farmerContact = json[i]['farmerContact'];
		    String _dateAndTime = json[i]['dateAndTime'];
		    String _dateString = json[i]['dateString'];
		    String _category = json[i]['category'];
		    String _grade = json[i]['grade'];
		    dynamic _price = json[i]['price'];
		    dynamic _compostWeigth = json[i]['compostWeigth'];
		    dynamic _totalPrice = json[i]['totalPrice'];

		    CFTClass o = CFTClass(
		    	_incrementId,
				_composterCompostInitId,
				_composterId,
				_farmerId,
				_farmerName,
				_farmerContact,
				_dateAndTime,
				_dateString,
				_category,
				_grade,
				_price,
				_compostWeigth,
				_totalPrice
		    );

		    obj.add(o);
		}
		return obj;
	}


	Widget makeCard(var obj){
		return Column(
			children : <Widget>[
				Card(
					elevation : 7.0,
					child : ListTile(
						isThreeLine : true,
						title : Text(AppLocalizations.of(context).translate('signUpF1')+': '+obj.farmerName),
						subtitle : Text(AppLocalizations.of(context).translate('tileP1') +" = "+ obj.totalPrice.toString() +" Rs.\n"+ AppLocalizations.of(context).translate('tileP2') +" = "+ obj.compostWeigth.toString() +" Kg."),												
					),
				),
				Divider(),
			]
		);
	}




	@override
	Widget build(BuildContext context){
		return Scaffold(
			appBar : AppBar(
				backgroundColor : Colors.green[700],
				title : Text(AppLocalizations.of(context).translate('transcHeadC'), textScaleFactor:0.9),
			),
			body : refresh(context),
		);
	}
}