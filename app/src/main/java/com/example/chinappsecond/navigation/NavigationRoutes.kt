package com.example.chinappsecond.navigation

sealed class NavigationRoutes(val route: String) {
    data object Home: NavigationRoutes("home")

    data object Search: NavigationRoutes("search")

    data object SearchResult: NavigationRoutes("searchResult/{text}"){
        fun createRoute(text: String) = "searchResult/$text"
    }

    data object Detail: NavigationRoutes("detail/{title}"){
        fun createRoute(title: String) = "detail/$title"
    }
}