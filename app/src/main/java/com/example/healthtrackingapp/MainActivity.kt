package com.example.healthtrackingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthtrackingapp.ui.screens.BloodPressureScreen
import com.example.healthtrackingapp.ui.screens.GetInformationScreen
import com.example.healthtrackingapp.ui.screens.HealthBookScreen
import com.example.healthtrackingapp.ui.screens.MainScreen
import com.example.healthtrackingapp.ui.screens.StartScreen
import com.example.healthtrackingapp.ui.screens.WeighingScreen
import com.example.healthtrackingapp.ui.theme.HealthTrackingAppTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                }
            }
        }
    }
}
