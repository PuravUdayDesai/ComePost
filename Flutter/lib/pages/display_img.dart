import 'package:http/http.dart' as http;
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'dart:io';
import 'dart:convert';
import 'dart:core';
import '../User/current_user.dart';
import 'package:toast/toast.dart';

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

class MyDisplayImage extends StatelessWidget {
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

class DisplayImage extends StatefulWidget{
	final File imageFile;
	final String user;
	DisplayImage({Key key, this.imageFile,this.user}) : super(key: key);

	@override
  	_DisplayImage createState() => _DisplayImage();
}

class _DisplayImage extends State<DisplayImage>{	
	// To track the file uploading state
	bool _isUploading = false;
	bool isLoading = false;

	Widget _buildUploadBtn() {
		Widget btnWidget = Container();
		if (_isUploading) {
		  // File is being uploaded then show a progress indicator
		  btnWidget = Container(
		      margin: EdgeInsets.only(top: 10.0),
		      child: CircularProgressIndicator());
		} else if (!_isUploading && widget.imageFile != null) {
		  // If image is picked by the user then show a upload btn_buildUploadBtn
		  print("Inn");
		  btnWidget = Container(
		  	height: 35,
		  	width: 120,
		    margin: EdgeInsets.only(top: 10.0),
		    child: RaisedButton(
		      child: Text(AppLocalizations.of(context).translate('uploadBut')),
		      onPressed: () {
		        _startUploading();
		      },
		      color: Colors.redAccent,
		      textColor: Colors.white,
		    ),
		  );
		}
		return btnWidget;
    }

    void _startUploading() async {

    	Map<String, MultipartFile> fileMap = {};
   	    Map<String,File> files = Map<String,File>();
		Map<String,dynamic> data = Map<String,dynamic>();
		files['file'] = widget.imageFile;
		setState((){isLoading = true;});
		for (MapEntry fileEntry in files.entries) {
			File file = fileEntry.value;
			//String fileName = basename(file.path);
			String fileName = file.path.split('/').last;
			fileMap[fileEntry.key] = MultipartFile(file.openRead(), await file.length(), filename: fileName);
		}
		data.addAll(fileMap);
		var formData = FormData.fromMap(data);
		Dio dio = new Dio();
		if(widget.user=='Composter'){
			print("In composter");
			print(fileMap);
			var res = await http.get('http://13.68.186.134:8080/compost/'+CurrentUser.id.toString());
			var resp = jsonDecode(res.body);
			print(resp);
			String uri = 'http://13.68.186.134:8080/compost/uploadImage/'+CurrentUser.id.toString()+'?init_id='+resp[0]['id'].toString()+'&timestamp='+DateTime.now().toString();
			print(uri);
			var r1 = await dio.post(
				uri,
				data: formData, options: Options(contentType: 'multipart/form-data')
			);
			print(r1);
			print(r1.statusCode);
			if(r1.statusCode == 201){
				    Toast.show(AppLocalizations.of(context).translate('uploadMsg'), context,
	        	duration: Toast.LENGTH_LONG, gravity: Toast.BOTTOM);
			}
		}	
		else{
			var r1 = await dio.post(
				'http://13.68.186.134:8080/supplier/uploadImage/'+CurrentUser.id.toString()+'?timestamp='+DateTime.now().toString(),
				data: formData, options: Options(contentType: 'multipart/form-data')
			);
			print(r1.statusCode);
			if(r1.statusCode == 201){
				    Toast.show(AppLocalizations.of(context).translate('uploadMsg'), context,
	        	duration: Toast.LENGTH_LONG, gravity: Toast.BOTTOM);
			}	
		}
		setState((){isLoading = false;});
    }

    @override
  	Widget build(BuildContext context) {
		return Scaffold(
		  appBar: AppBar(
		    title: Text(AppLocalizations.of(context).translate('uploadHead')),
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
		  body:	IgnorePointer(
		  ignoring : isLoading,
		  child : Stack(
		  	children : <Widget>[
		  		Column(
		    	children: <Widget>[
					Image.file(
			    			widget.imageFile,
		                    fit: BoxFit.fill,
		                    height: 470.0,
		                    alignment: Alignment.topCenter,
		                    width: MediaQuery.of(context).size.width,	    		
		    		),	    			    		
		    		_buildUploadBtn(),
		    		]
		    	),
		    	isLoading ? Center(child : CircularProgressIndicator()) : Container(),
		  	]
		  )),
		  
		);
	}
}
