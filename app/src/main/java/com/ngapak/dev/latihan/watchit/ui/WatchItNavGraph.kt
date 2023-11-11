package com.ngapak.dev.latihan.watchit.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ngapak.dev.latihan.watchit.ui.screen.about.AboutScreen
import com.ngapak.dev.latihan.watchit.ui.screen.favorite.FavoriteScreen
import com.ngapak.dev.latihan.watchit.ui.screen.home.HomeScreen
import com.ngapak.dev.latihan.watchit.ui.screen.movie_detail.MovieDetailScreen
import com.ngapak.dev.latihan.watchit.ui.screen.search.SearchScreen

@Composable
fun WatchItNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = WatchItNavigation.HOME_ROUTE,
    ) {
        composable(WatchItNavigation.HOME_ROUTE) {
            HomeScreen(
                navigateToDetail = { id ->
                    navController.navigate("${WatchItNavigation.HOME_ROUTE}/$id")
                },
                navigateToAboutPage = { navController.navigate(WatchItNavigation.ABOUT_ROUTE) },
                navigateToFavorite = { navController.navigate(WatchItNavigation.FAVORITE_ROUTE) },
                navigateSearch = { navController.navigate(WatchItNavigation.SEARCH_ROUTE) },
            )
        }
        composable(
            WatchItNavigation.MOVIE_DETAIL_ROUTE,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            val movieId = it.arguments?.getInt("movieId", 0) ?: -1
            MovieDetailScreen(
                movieId = movieId,
                navigateBack = { navController.navigateUp() },
            )
        }
        composable(WatchItNavigation.ABOUT_ROUTE) {
            AboutScreen({ navController.navigateUp() })
        }
        composable(WatchItNavigation.FAVORITE_ROUTE) {
            FavoriteScreen(
                navigateUp = { navController.navigateUp() },
                navigateToDetail = { id ->
                    navController.navigate("${WatchItNavigation.HOME_ROUTE}/$id")
                },
            )
        }
        composable(WatchItNavigation.SEARCH_ROUTE) {
            SearchScreen(navigateToDetail = { id ->
                navController.navigate("${WatchItNavigation.HOME_ROUTE}/$id")
            }, navigateUp = { navController.navigateUp() })
        }
    }
}