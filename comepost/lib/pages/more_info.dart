import 'package:ComePost/Api_Services/ApiCall.dart';
import 'package:ComePost/Api_Services/Uri.dart';
import 'package:ComePost/User/current_user.dart';
import 'package:ComePost/pages/router.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';

import '../app_localization.dart';

class MoreInfo extends StatelessWidget {
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
        home: MoreInfoScreen(),
        onGenerateRoute: MyRouter().routeSettings);
  }
}

class MoreInfoScreen extends StatefulWidget {
  @override
  _MoreInfoScreenState createState() => _MoreInfoScreenState();
}

class _MoreInfoScreenState extends State<MoreInfoScreen> {
  bool _isLoading = false;

  @override
  Widget build(BuildContext context) {
    return _isLoading
        ? Scaffold(body: CircularProgressIndicator())
        : Scaffold(
            appBar: AppBar(
              flexibleSpace: Container(
                decoration: BoxDecoration(
                    gradient: LinearGradient(
                        begin: Alignment.topLeft,
                        end: Alignment.bottomRight,
                        colors: <Color>[Color(0xff43a047), Color(0xff2e7d32)])),
              ),
              title: Text(
                  AppLocalizations.of(context).translate('transactionsTitle')),
              centerTitle: true,
              iconTheme: IconThemeData(color: Colors.white),
            ),
            body: FutureBuilder(
                future: ApiCall.getDataFromApi(
                    Uri.baseUri + '/farmer/purchase/${CurrentUser.id}'),
                builder: (context, snap) {
                  if (snap.hasData) {
                    var res = snap.data;
                    List<dynamic> data = res;
                    double qty = 0, amt = 0;
                    data.forEach((e) {
                      qty += e['compostWeigth'];
                      amt += e['totalPrice'];
                    });
                    return Column(
                      children: [
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceAround,
                          children: [
                            Container(
                              height: MediaQuery.of(context).size.height * 0.2,
                              width: MediaQuery.of(context).size.width / 2,
                              child: Card(
                                  shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(15)),
                                  elevation: 5,
                                  child: ListTile(
                                    contentPadding: EdgeInsets.symmetric(
                                        vertical: 10, horizontal: 1),
                                    title: Text(
                                      'Available comepost quantity',
                                      style: TextStyle(fontSize: 17),
                                      textAlign: TextAlign.center,
                                    ),
                                    subtitle: Padding(
                                      padding: const EdgeInsets.symmetric(
                                          vertical: 10.0),
                                      child: Text(
                                        '${qty} Kgs',
                                        textAlign: TextAlign.center,
                                        style: TextStyle(
                                            fontSize: 16, color: Colors.green),
                                      ),
                                    ),
                                  )),
                            ),
                            Container(
                              height: MediaQuery.of(context).size.height * 0.2,
                              width: MediaQuery.of(context).size.width / 2,
                              child: Card(
                                  shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(15)),
                                  elevation: 5,
                                  child: ListTile(
                                    contentPadding: EdgeInsets.symmetric(
                                        vertical: 10, horizontal: 1),
                                    title: Text(
                                      'Total cost of comepost',
                                      style: TextStyle(fontSize: 17),
                                      textAlign: TextAlign.center,
                                    ),
                                    subtitle: Padding(
                                      padding: const EdgeInsets.symmetric(
                                          vertical: 10.0),
                                      child: Text(
                                        '${amt} Rs',
                                        textAlign: TextAlign.center,
                                        style: TextStyle(
                                            fontSize: 16, color: Colors.green),
                                      ),
                                    ),
                                  )),
                            ),
                          ],
                        ),
                        Expanded(
                          child: Container(
                            margin: EdgeInsets.symmetric(
                                horizontal: 5, vertical: 10),
                            child: ListView.builder(
                                itemCount: res.length,
                                itemBuilder: (context, index) {
                                  return Card(
                                    shape: RoundedRectangleBorder(
                                        borderRadius:
                                            BorderRadius.circular(15)),
                                    elevation: 3,
                                    child: Padding(
                                      padding: const EdgeInsets.all(5.0),
                                      child: ListTile(
                                        title: Padding(
                                          padding: const EdgeInsets.symmetric(
                                              vertical: 5.0),
                                          child: Text(
                                            '${res[index]['category']} of ${res[index]['grade']} grade',
                                            style: TextStyle(fontSize: 18),
                                          ),
                                        ),
                                        subtitle: Text(
                                            AppLocalizations.of(context)
                                                    .translate('priceLab') +
                                                ' = ${res[index]['price']}/Kg\n${AppLocalizations.of(context).translate('quanLab')} = ${res[index]['compostWeigth']} Kg\n${AppLocalizations.of(context).translate('tileP1')} = ${res[index]['totalPrice']} Rs',
                                            style: TextStyle(
                                              color: Colors.brown,
                                            )),
                                      ),
                                    ),
                                  );
                                }),
                          ),
                        ),
                      ],
                    );
                  }
                  return Center(child: CircularProgressIndicator());
                }),
          );
  }
}
