package com.example.healthtrackingapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.healthtrackingapp.ui.screens.AddFoodScreen
import com.example.healthtrackingapp.ui.screens.AddSleepScreen
import com.example.healthtrackingapp.ui.screens.AddTemperatureScreen
import com.example.healthtrackingapp.ui.screens.AddWaterScreen
import com.example.healthtrackingapp.ui.screens.AlarmScreen
import com.example.healthtrackingapp.ui.screens.BloodPressureScreen
import com.example.healthtrackingapp.ui.screens.GetInformationScreen
import com.example.healthtrackingapp.ui.screens.GoalScreen
import com.example.healthtrackingapp.ui.screens.HealthBookScreen
import com.example.healthtrackingapp.ui.screens.HistoryUNao
import com.example.healthtrackingapp.ui.screens.LoginScreen
import com.example.healthtrackingapp.ui.screens.MainScreen
import com.example.healthtrackingapp.ui.screens.OverviewScreen
import com.example.healthtrackingapp.ui.screens.StartScreen
import com.example.healthtrackingapp.ui.screens.UNaoScreen
import com.example.healthtrackingapp.ui.screens.WeighingScreen
import com.example.healthtrackingapp.ui.theme.HealthTrackingAppTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestExactAlarmPermission(this)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            HealthTrackingAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "start"
                ) {
                    composable(
                        "start",
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                tween(1000)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                tween(1000)
                            )
                        }
                    ) { StartScreen(navController) }
                    composable(
                        "login",
                        enterTransition = {
                            when (targetState.destination.route) {
                                "start" -> slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right,
                                    tween(1000)
                                ) { it }

                                else -> slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    tween(1000)
                                )
                            }
                        },
                        exitTransition = {
                            when (targetState.destination.route) {
                                "start" -> slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right,
                                    tween(1000)
                                ) { it }

                                else -> slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    tween(1000)
                                )
                            }
                        }
                    ) { LoginScreen(navController) }
                    composable(
                        "getinfor",
                        enterTransition = {
                            when (targetState.destination.route) {
                                "start" -> slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right,
                                    tween(1000)
                                ) { it }

                                else -> slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    tween(1000)
                                )
                            }
                        },
                        exitTransition = {
                            when (targetState.destination.route) {
                                "start" -> slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right,
                                    tween(1000)
                                ) { it }

                                else -> slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    tween(1000)
                                )
                            }
                        }
                    ) { GetInformationScreen(navController) }
                    composable(
                        "main",
                        deepLinks = listOf(navDeepLink { uriPattern = "myapp://main/user" }),
                        enterTransition = {
                            when (targetState.destination.route) {
                                "getinfor" -> slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right,
                                    tween(500)
                                ) { it }

                                else -> slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    tween(500)
                                )
                            }
                        },
                        exitTransition = {
                            when (targetState.destination.route) {
                                "getinfor" -> slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right,
                                    tween(500)
                                ) { it }

                                else -> slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    tween(500)
                                )
                            }
                        }

                    ) { MainScreen(navController) }
                    composable("healthbook") { HealthBookScreen(navController) }
                    composable(
                        "weighingscreen",
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Up,
                                tween(500)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Down,
                                tween(500)
                            )
                        }
                    ) { WeighingScreen(navController) }
                    composable(
                        "addsleep",
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Up,
                                tween(500)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Down,
                                tween(500)
                            )
                        }
                    ) { AddSleepScreen(navController) }
                    composable(
                        "addwater",
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Up,
                                tween(500)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Down,
                                tween(500)
                            )
                        }
                    ) { AddWaterScreen(navController) }
                    composable(
                        "addfood",
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Up,
                                tween(500)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Down,
                                tween(500)
                            )
                        }
                    ) { AddFoodScreen(navController) }
                    composable(
                        "bloodpressurescreen",
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Up,
                                tween(500)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Down,
                                tween(500)
                            )
                        }
                    ) { BloodPressureScreen(navController) }
                    composable(
                        "addtemperaturescreen",
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Up,
                                tween(500)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Down,
                                tween(500)
                            )
                        }
                    ) { AddTemperatureScreen(navController) }
                    composable("goalscreen") { GoalScreen(navController = navController) }
                    composable(
                        "overviewscreen",
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Up,
                                tween(500)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Down,
                                tween(500)
                            )
                        }
                    ) { OverviewScreen(navController) }
                    composable("unao") { UNaoScreen(navController) }
                    composable("unaohistory") { HistoryUNao(navController) }
                    composable("alarmscreen") { AlarmScreen(navController) }
                }
            }
        }
    }
}

@SuppressLint("ServiceCast")
fun requestExactAlarmPermission(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            context.startActivity(intent)
        }
    }
}