import 'package:ComePost/pages/login.dart';
import 'package:ComePost/pages/login_composter.dart';
import 'package:ComePost/pages/login_farmer.dart';
import 'package:ComePost/pages/sct.dart';
import 'package:ComePost/pages/selection_screen_login.dart';
import 'package:ComePost/pages/selection_screen_signup.dart';
import 'package:ComePost/pages/signup_composter.dart';
import 'package:ComePost/pages/signup_farmer.dart';
import 'package:ComePost/pages/signup_supplier.dart';
import 'package:flutter/cupertino.dart';
import 'package:page_transition/page_transition.dart';

class MyRouter {
  Route<dynamic> routeSettings(s) {
    switch (s.name) {
      case '/selection_screen_login':
        return PageTransition(
            child: MySelectionScreenLogin(),
            type: PageTransitionType.rightToLeft);

      case '/selection_screen_signup':
        return PageTransition(
            child: SelectionScreenSignUp(),
            type: PageTransitionType.rightToLeft);

      case '/login':
        return PageTransition(
            child: LoginPage(), type: PageTransitionType.rightToLeft);

      case '/login_composter':
        return PageTransition(
            child: LoginComposterPage(), type: PageTransitionType.rightToLeft);

      case '/login_farmer':
        return PageTransition(
            child: LoginFarmerPage(), type: PageTransitionType.rightToLeft);

      case '/signup_supplier':
        return PageTransition(
            child: SignUpSupplier(), type: PageTransitionType.rightToLeft);

      case '/signup_composter':
        return PageTransition(
            child: SignUpComposter(), type: PageTransitionType.rightToLeft);

      case '/signup_farmer':
        return PageTransition(
            child: SignUpFarmer(), type: PageTransitionType.rightToLeft);

      case '/supplier_composter_transaction':
        return PageTransition(
            child: SCT(), type: PageTransitionType.rightToLeft);

      default:
        return PageTransition(
            child: Text('No path found.'),
            type: PageTransitionType.rightToLeft);
    }
  }
}
