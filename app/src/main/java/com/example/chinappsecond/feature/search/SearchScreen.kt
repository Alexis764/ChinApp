package com.example.chinappsecond.feature.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chinappsecond.R
import com.example.chinappsecond.core.NewModel
import com.example.chinappsecond.navigation.NavigationRoutes
import com.example.chinappsecond.ui.component.BasicNewPhone
import com.example.chinappsecond.ui.component.TopAppBar
import com.example.chinappsecond.ui.theme.LogoBackGround
import com.example.chinappsecond.ui.theme.PagerGray
import kotlin.math.ceil

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    navController: NavHostController,
    windowSizeClass: WindowSizeClass
) {
    Column(Modifier.fillMaxSize()) {
        TopAppBar {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        SearchTextField(
            searchViewModel,
            windowSizeClass
        ) { navController.navigate(NavigationRoutes.SearchResult.createRoute(it)) }
        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(color = Color.Gray, modifier = Modifier.padding(horizontal = 8.dp))
        Spacer(modifier = Modifier.height(8.dp))

        HotView(searchViewModel, navController, windowSizeClass)
    }
}

@Composable
fun HotView(
    searchViewModel: SearchViewModel,
    navController: NavHostController,
    windowSizeClass: WindowSizeClass
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 12.dp, end = 8.dp, start = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HotTitle(windowSizeClass)
        HotPager(Modifier.weight(1f), searchViewModel, navController, windowSizeClass)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HotPager(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel,
    navController: NavHostController,
    windowSizeClass: WindowSizeClass
) {
    val newList: List<NewModel> = searchViewModel.newList
    newList.forEach { searchViewModel.loadImage(it) }
    val pagerState = rememberPagerState(pageCount = { ceil(newList.size / 4.0).toInt() })

    HorizontalPager(state = pagerState, modifier = modifier) { page ->
        val indexLastElement = ((page + 1) * 4) - 1
        val pageList = if ((indexLastElement + 1) <= newList.size)
            newList.subList(indexLastElement - 3, indexLastElement + 1)
        else newList.subList(indexLastElement - 3, newList.size)

        HotPageList(
            pageList,
            if (windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact) 300.dp else 200.dp
        ) { navController.navigate(NavigationRoutes.Detail.createRoute(it)) }
    }

    Row {
        repeat(pagerState.pageCount) {
            val color = if (pagerState.currentPage == it) LogoBackGround else PagerGray
            Icon(
                painter = painterResource(id = R.drawable.ic_circle),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(if (windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact) 16.dp else 12.dp)
            )
        }
    }
}

@Composable
fun HotPageList(
    hotNewList: List<NewModel>,
    imageHeight: Dp,
    onNavigateToDetailScreen: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(hotNewList) {
            BasicNewPhone(newModel = it, imageHeight = imageHeight) { newModel ->
                onNavigateToDetailScreen(newModel.title)
            }
        }
    }
}

@Composable
fun HotTitle(windowSizeClass: WindowSizeClass) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.ic_hot),
            contentDescription = null,
            modifier = Modifier.size(if (windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact) 60.dp else 40.dp)
        )
        Text(
            text = "HOT",
            fontWeight = FontWeight.Bold,
            fontSize = if (windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact) 30.sp else 20.sp,
        )
    }
}

@Composable
fun SearchTextField(
    searchViewModel: SearchViewModel,
    windowSizeClass: WindowSizeClass,
    onNavigateToSearchResultScreen: (String) -> Unit
) {
    val searchText: String by searchViewModel.searchText.observeAsState("")

    OutlinedTextField(
        value = searchText,
        onValueChange = { searchViewModel.onSearchTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = if (windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact) 36.dp else 26.dp),
        trailingIcon = {
            IconButton(onClick = {
                if (searchText.isNotEmpty()) onNavigateToSearchResultScreen(searchText)
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            }
        },
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions(onDone = {
            if (searchText.isNotEmpty()) onNavigateToSearchResultScreen(searchText)
        })
    )
}
