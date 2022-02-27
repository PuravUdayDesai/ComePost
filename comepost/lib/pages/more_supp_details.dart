import 'package:ComePost/Api_Services/ApiCall.dart';
import 'package:ComePost/Api_Services/Uri.dart';
import 'package:ComePost/User/current_user.dart';
import 'package:ComePost/pages/router.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:syncfusion_flutter_charts/charts.dart';
import 'package:syncfusion_flutter_charts/sparkcharts.dart';

import '../app_localization.dart';

class MoreSupInfo extends StatelessWidget {
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
        home: MoreSupInfoScreen(),
        onGenerateRoute: MyRouter().routeSettings);
  }
}

class MoreSupInfoScreen extends StatefulWidget {
  @override
  _MoreSupInfoScreenState createState() => _MoreSupInfoScreenState();
}

class _MoreSupInfoScreenState extends State<MoreSupInfoScreen> {
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
              title: Text('Stock details'),
              centerTitle: true,
              iconTheme: IconThemeData(color: Colors.white),
            ),
            body: FutureBuilder(
                future: ApiCall.getDataFromApi(
                    Uri.baseUri + '/compost/${CurrentUser.id}'),
                builder: (context, snap) {
                  if (snap.hasData) {
                    var res = snap.data;
                    print("Res: $res");
                    List<dynamic> data = res == "nothing" ? [] : res;
                    double total = 0, rem = 0;
                    if(data.isNotEmpty){
                      rem = data[data.length - 1]['compostWeight'];
                      data.forEach((e) {
                        if (e['addOrSub']) total += e['compostWeight'];
                      });
                    }                    
                    return Container(
                      margin: EdgeInsets.symmetric(vertical: 10),
                      child: Column(
                        children: [
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceAround,
                            children: [
                              Container(
                                height:
                                    MediaQuery.of(context).size.height * 0.2,
                                width: MediaQuery.of(context).size.width / 2,
                                child: Card(
                                    shape: RoundedRectangleBorder(
                                        borderRadius:
                                            BorderRadius.circular(15)),
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
                                          '${total} Kgs',
                                          textAlign: TextAlign.center,
                                          style: TextStyle(
                                              fontSize: 16,
                                              color: Colors.green),
                                        ),
                                      ),
                                    )),
                              ),
                              Container(
                                height:
                                    MediaQuery.of(context).size.height * 0.2,
                                width: MediaQuery.of(context).size.width / 2,
                                child: Card(
                                    shape: RoundedRectangleBorder(
                                        borderRadius:
                                            BorderRadius.circular(15)),
                                    elevation: 5,
                                    child: ListTile(
                                      contentPadding: EdgeInsets.symmetric(
                                          vertical: 10, horizontal: 1),
                                      title: Text(
                                        'Total comepost sold',
                                        style: TextStyle(fontSize: 17),
                                        textAlign: TextAlign.center,
                                      ),
                                      subtitle: Padding(
                                        padding: const EdgeInsets.symmetric(
                                            vertical: 10.0),
                                        child: Text(
                                          '${total - rem} Kgs',
                                          textAlign: TextAlign.center,
                                          style: TextStyle(
                                              fontSize: 16,
                                              color: Colors.green),
                                        ),
                                      ),
                                    )),
                              ),
                            ],
                          ),
                          SizedBox(height: 20),
                          Card(
                            elevation: 3,
                            shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
                            child: SfCartesianChart(
                              title: ChartTitle(
                                  text: 'Stock information based on time'),
                              primaryXAxis: CategoryAxis(
                                title: AxisTitle(
                                  text: 'Time',
                                  textStyle: TextStyle(
                                    color: Colors.black,
                                  ),
                                ),
                              ),
                              primaryYAxis: NumericAxis(
                                title: AxisTitle(text: 'Quantity', textStyle: TextStyle(color: Colors.black)),
                                  minimum: 0, maximum: 30, interval: 2),
                              series: <ChartSeries<dynamic, String>>[
                                BarSeries(
                                    dataLabelMapper: (datum, index) {
                                      return index.toString();
                                    },
                                    dataSource: data,
                                    xValueMapper: (data, _) =>
                                        data['date'].toString().substring(11, 16),
                                    yValueMapper: (data, _) =>
                                        data['compostWeight'])
                              ],
                            ),
                          )
                        ],
                      ),
                    );
                  }
                  return Center(child: CircularProgressIndicator());
                }),
          );
  }
}
