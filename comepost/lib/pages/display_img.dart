import 'package:ComePost/pages/router.dart';
import 'package:http/http.dart' as http;
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:io';
import 'dart:convert';
import 'dart:core';
import '../User/current_user.dart';
import 'package:toast/toast.dart';
import '../Api_Services/Uri.dart' as UriService;
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
        title: "Come Post",
        debugShowCheckedModeBanner: false,
        home: SelectionScreenLogIn(),
        onGenerateRoute: MyRouter().routeSettings);
  }
}

class DisplayImage extends StatefulWidget {
  final XFile imageFile;
  final String user;
  DisplayImage({Key key, this.imageFile, this.user}) : super(key: key);

  @override
  _DisplayImage createState() => _DisplayImage();
}

class _DisplayImage extends State<DisplayImage> {
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
    Map<String, File> files = Map<String, File>();
    Map<String, dynamic> data = Map<String, dynamic>();
    files['file'] = File(widget.imageFile.path);
    setState(() {
      isLoading = true;
    });
    for (MapEntry fileEntry in files.entries) {
      File file = fileEntry.value;
      //String fileName = basename(file.path);
      String fileName = file.path.split('/').last;
      fileMap[fileEntry.key] = MultipartFile(
          file.openRead(), await file.length(),
          filename: fileName);
    }
    data.addAll(fileMap);
    var formData = FormData.fromMap(data);
    Dio dio = new Dio();
    if (widget.user == 'Composter') {
      print("In composter");
      print(fileMap);
      var res = await http.get(
          Uri.parse('${UriService.Uri.baseUri}/compost/${CurrentUser.id}'));
      var resp = jsonDecode(res.body);
      print(resp);
      String uri = '${UriService.Uri.baseUri}/compost/uploadImage/' +
          CurrentUser.id.toString() +
          '?init_id=' +
          resp[0]['id'].toString() +
          '&timestamp=' +
          DateTime.now().toString();
      print(uri);
      var r1 = await dio.post(uri,
          data: formData, options: Options(contentType: 'multipart/form-data'));
      print(r1);
      print(r1.statusCode);
      if (r1.statusCode == 200) {
        Toast.show(AppLocalizations.of(context).translate('uploadMsg'), context,
            duration: Toast.LENGTH_LONG, gravity: Toast.BOTTOM);
        Navigator.pop(context);
      }
    } else {
      var r1 = await dio.post(
          '${UriService.Uri.baseUri}/supplier/uploadImage/' +
              CurrentUser.id.toString() +
              '?timestamp=' +
              DateTime.now().toString(),
          data: formData,
          options: Options(contentType: 'multipart/form-data'));
      print(r1.statusCode);
      if (r1.statusCode == 200) {
        Toast.show(AppLocalizations.of(context).translate('uploadMsg'), context,
            duration: Toast.LENGTH_LONG, gravity: Toast.BOTTOM);
        Navigator.pop(context);
      }
    }
    setState(() {
      isLoading = false;
    });
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
                  colors: <Color>[Color(0xff43a047), Color(0xff2e7d32)])),
        ),
      ),
      body: IgnorePointer(
          ignoring: isLoading,
          child: Stack(children: <Widget>[
            Column(children: <Widget>[
              Image.file(
                File(widget.imageFile.path),
                fit: BoxFit.fill,
                height: 470.0,
                alignment: Alignment.topCenter,
                width: MediaQuery.of(context).size.width,
              ),
              _buildUploadBtn(),
            ]),
            isLoading
                ? Center(child: CircularProgressIndicator())
                : Container(),
          ])),
    );
  }
}