import 'package:flutter_polyline_points/flutter_polyline_points.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'dart:async';
import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'home_composter.dart';

const double CAMERA_ZOOM = 13;
const double CAMERA_TILT = 0;
const double CAMERA_BEARING = 30;


class DisplayMap extends StatefulWidget {
    @override
    _DisplayMapState createState() => _DisplayMapState();
  }

class _DisplayMapState extends State<DisplayMap>
{
	Completer<GoogleMapController> _controller = Completer();  
    // this set will hold my markers
    Set<Marker> _markers = {};      
    // this will hold the generated polylines
    Set<Polyline> _polylines = {};      
    // this will hold each polyline coordinate as Lat and Lng pairs
    List<LatLng> polylineCoordinates = [];      
    Timer timer;
    CameraPosition initialLocation;
    LatLng SOURCE_LOCATION,END_LOCATION;
    bool once=true;
    String user_id,date;


    @override
    void initState() {
      super.initState();
      timer = Timer.periodic(Duration(seconds: 5), (Timer t) => getCurrentLocation());
      
    }
     void getCurrentLocation() async{
      var currentLocation = await Geolocator().
        getCurrentPosition(desiredAccuracy: LocationAccuracy.best);
      //print(currentLocation);
    }
    

    @override
    Widget build(BuildContext context) {  
    	
      if(once)
      {
      	ComposterClass args = ModalRoute.of(context).settings.arguments;
      	dynamic lat = double.parse(args.latitude);
      	dynamic lon = double.parse(args.longitude);
      	SOURCE_LOCATION = LatLng(lat,lon);
      	print(SOURCE_LOCATION);
      	//once = false;
      }
      CameraPosition initialLocation = CameraPosition(
          zoom: CAMERA_ZOOM,
          bearing: CAMERA_BEARING,
          tilt: CAMERA_TILT,
          target: SOURCE_LOCATION
      ); 

      return GoogleMap(
          myLocationEnabled: true,  
          tiltGesturesEnabled: false,
          markers: _markers,
          mapType: MapType.normal,
          initialCameraPosition: initialLocation,
          onMapCreated: onMapCreated
      );

      
    }

    void onMapCreated(GoogleMapController controller) {
      _controller.complete(controller);
      print("hi");
      setMapPins();
    }

    void setMapPins()
    {  
      setState(() {
        _markers.add(Marker(
           markerId: MarkerId('sourcePin'),
           position: SOURCE_LOCATION,
        ));      
        /*_markers.add(Marker(
           markerId: MarkerId('destPin'),
           position: END_LOCATION,
        ));  */
      });
    }
}