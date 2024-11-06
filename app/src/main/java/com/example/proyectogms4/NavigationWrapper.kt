package com.example.proyectogms4

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.proyectogms4.presentation.home.DashboardScreen
import com.example.proyectogms4.presentation.home.HomeScreen
import com.example.proyectogms4.presentation.home.ManualScreen
import com.example.proyectogms4.presentation.home.NewsScreen
import com.example.proyectogms4.presentation.home.SettingsScreen
import com.example.proyectogms4.presentation.home.TestingScreen
import com.example.proyectogms4.presentation.home.WeatherScreen
import com.example.proyectogms4.presentation.initial.InitialScreen
import com.example.proyectogms4.presentation.login.LoginScreen
import com.example.proyectogms4.presentation.signup.SignUpScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth,
    db: FirebaseFirestore
) {
    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navHostController.navigate("logIn") },
                navigateToSignUp = { navHostController.navigate("signUp") }
            )
        }
        composable("logIn") {
            LoginScreen(auth){ navHostController.navigate("home")}
            }
        composable("signUp") {
            SignUpScreen(auth)
        }
        composable("home") {
            HomeScreen(db, navHostController)
        }
        composable("dashboard") {
            DashboardScreen(navHostController)
        }
        composable("settings") {
            SettingsScreen(navHostController)
        }
        composable("news") {
            NewsScreen(navHostController)
        }
        composable("weather") {
            WeatherScreen(navHostController)
        }
        composable("testing") {
            TestingScreen(navHostController)
        }
        composable("manual") {
            ManualScreen(navHostController)
        }
    }
}