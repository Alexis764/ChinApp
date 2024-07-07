package com.example.chinappsecond.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chinappsecond.feature.detail.DetailScreen
import com.example.chinappsecond.feature.home.HomeScreen
import com.example.chinappsecond.feature.home.HomeViewModel
import com.example.chinappsecond.feature.search.SearchScreen
import com.example.chinappsecond.feature.search.SearchViewModel
import com.example.chinappsecond.feature.search_result.SearchResultScreen

@Composable
fun MyNavigation(
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    windowSizeClass: WindowSizeClass
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoutes.Home.route) {
        composable(NavigationRoutes.Home.route) {
            HomeScreen(homeViewModel, navController, windowSizeClass)
        }
        composable(NavigationRoutes.Search.route) {
            SearchScreen(searchViewModel, navController, windowSizeClass)
        }
        composable(
            NavigationRoutes.SearchResult.route,
            arguments = listOf(
                navArgument("text") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            SearchResultScreen(
                navController,
                windowSizeClass,
                navBackStackEntry.arguments?.getString("text").orEmpty()
            )
        }
        composable(
            NavigationRoutes.Detail.route,
            arguments = listOf(
                navArgument("title") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            DetailScreen(
                navController,
                windowSizeClass,
                navBackStackEntry.arguments?.getString("title").orEmpty()
            )
        }
    }
}