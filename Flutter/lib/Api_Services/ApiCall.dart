import "dart:convert";
import "dart:async";
import "package:http/http.dart" as http;
import "Uri.dart";

class ApiCall
{
	static getDataFromApi(baseURI) async
	{
		try
		{
			print(baseURI);	
			var response = await http.get(
		        baseURI,
		        headers : {
		          "Accept" : "application/json"
		        }
		        );
			print(response.statusCode);
			if(response.statusCode!=200){
				return 'nothing';
			}
			var jsonData = jsonDecode(response.body);
			//print(jsonData);
			print('here');
			return jsonData;
		}
		catch(e)
		{
			print("Here");
			print(e);
		}
	}
	static createRecord(baseURI,dataa) async
	{
		print(jsonEncode(dataa));
		try
		{
			http.Response response = await http.post(baseURI,body : jsonEncode(dataa),headers : {
              "Accept" : "application/json",
              "Content-Type" : "application/json"
			            });
			      final int statusCode = response.statusCode;
			      print(statusCode);
			      if (statusCode < 200 || statusCode > 400 || json == null) 
		      	{
		      		print("Error while fetching data");
		    	}
		      return (json.decode(response.body));
		}
		catch(e)
		{
			print(e);
		}
	}
	static updateRecord(baseURI,dataa) async
	{
		print(jsonEncode(dataa));
		print(baseURI);
		try
		{
			http.Response response = await http
		    .put(baseURI, body: jsonEncode(dataa),headers : {
              "Accept" : "application/json",
              "Content-Type" : "application/json"
			            });
		    final int statusCode = response.statusCode;
		    print(statusCode);
		    if (statusCode < 200 || statusCode > 400 || json == null) {
		      print("Error while fetching data");
		    }
		    return (json.decode(response.body));
		}
		catch(e)
		{
			print(e);
		}
	}
	static deleteRecord(baseURI) async
	{
		print(baseURI);
		try
		{
			http.Response response =
		        await http.delete(baseURI);
		    final int statusCode = response.statusCode;
		    print(statusCode);
		    if (statusCode < 200 || statusCode > 400 || json == null) {
		      print("Error while fetching data");
		    }
		    print(response.body);
		    return (json.decode(response.body));
		}
		catch(e)
		{
			print(e);
		}
	}
}