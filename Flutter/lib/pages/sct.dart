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
      home: SCT(),
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


class SCT extends StatefulWidget{
	@override
	_SCTState createState() => _SCTState();
}

class SCTClass{
	int incrementId;
	  int supplierWasteId;
	  int supplierId;
	  int composterId;
	  String composterName;
	  String composterEmailId;
	  String composterContactNumber;
	  String dateAndTime;
	  String dateString;
	  dynamic dryWaste;
	  dynamic wetWaste;


	  SCTClass(
	  	this.incrementId,
      this.supplierWasteId,
      this.supplierId,
      this.composterId,
      this.composterName,
      this.composterEmailId,
      this.composterContactNumber,
      this.dateAndTime,
      this.dateString,
      this.dryWaste,
      this.wetWaste
	  );
}

class _SCTState extends State<SCT>{


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


	Future<List<SCTClass>> getData() async{
		var json = await ApiCall.getDataFromApi(Uri.GET_SUPPLIER_COMPOSTER_TRANSACTION+'/${CurrentUser.id}');
		List<SCTClass> obj=[];

		if(json=='nothing'){
			return obj;
		}
		print(json);

		for(int i=0;i<json.length;i++){
			int _incrementId = json[i]['incrementId'];
		    int _supplierWasteId = json[i]['supplierWasteId'];
		    int _supplierId = json[i]['supplierId'];
		    int _composterId = json[i]['composterId'];
		    String _composterName = json[i]['composterName'];
		    String _composterEmailId = json[i]['composterEmailId'];
		    String _composterContactNumber = json[i]['composterContactNumber'];
		    String _dateAndTime = json[i]['dateAndTime'];
		    String _dateString = json[i]['dateString'];
		    dynamic _dryWaste = json[i]['dryWaste'];
		    dynamic _wetWaste = json[i]['wetWaste'];

		    SCTClass o = SCTClass(
		    	_incrementId,
				_supplierWasteId,
				_supplierId,
				_composterId,
				_composterName,
				_composterEmailId,
				_composterContactNumber,
				_dateAndTime,
				_dateString,
				_dryWaste,
				_wetWaste
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
						title : Text(obj.composterName + ' (${obj.composterContactNumber})'),
						subtitle : Text(AppLocalizations.of(context).translate('addPopUpDCol')+' = '+obj.dryWaste.toString()+' Kg.\t\t\t\n'+AppLocalizations.of(context).translate('addPopUpWCol')+' = '+obj.wetWaste.toString()+' Kg.\n'+obj.dateString),
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
				title : Text(AppLocalizations.of(context).translate('transcHead'), textScaleFactor:0.9),
			),
			body : refresh(context),
		);
	}
}