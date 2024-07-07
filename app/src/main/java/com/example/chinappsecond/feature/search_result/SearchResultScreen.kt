package com.example.chinappsecond.feature.search_result

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chinappsecond.core.NewModelDate
import com.example.chinappsecond.navigation.NavigationRoutes
import com.example.chinappsecond.ui.component.SearchResultNewItemPhone
import com.example.chinappsecond.ui.component.SearchResultNewItemTablet
import com.example.chinappsecond.ui.component.TopAppBar
import com.example.chinappsecond.ui.theme.PagerGray
import kotlinx.coroutines.launch
import kotlin.math.ceil

@Composable
fun SearchResultScreen(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    text: String,
    searchResultViewModel: SearchResultViewModel = hiltViewModel()
) {
    val searchResultText: String by searchResultViewModel.searchResultText.observeAsState(text)
    val isTimeAscending: Boolean by searchResultViewModel.isTimeAscending.observeAsState(true)

    Column(Modifier.fillMaxSize()) {
        TopAppBar {
            IconButton(onClick = { navController.navigate(NavigationRoutes.Home.route) }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        ResultSearchTextField(
            searchResultText,
            windowSizeClass
        ) { searchResultViewModel.onSearchResultTextChanged(it) }
        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(color = Color.Gray, modifier = Modifier.padding(horizontal = 8.dp))
        Spacer(modifier = Modifier.height(8.dp))

        TimeFilterButton(isTimeAscending) { searchResultViewModel.onIsTimeAscendingChanged(it) }
        NewPager(searchResultText, searchResultViewModel, windowSizeClass, navController)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewPager(
    searchResultText: String,
    searchResultViewModel: SearchResultViewModel,
    windowSizeClass: WindowSizeClass,
    navController: NavHostController
) {
    val newList: List<NewModelDate> = searchResultViewModel.newList
    newList.forEach { searchResultViewModel.loadImage(it) }
    val newListFilterBySearch = getFilterList(newList, searchResultText)

    val pagerState = rememberPagerState(
        pageCount = {
            if (windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact) {  //Tablet
                ceil(newListFilterBySearch.size / 5.0).toInt()
            } else { // Phone
                ceil(newListFilterBySearch.size / 2.0).toInt()
            }
        }
    )

    Column(Modifier.fillMaxSize()) {
        if (pagerState.pageCount > 0) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                userScrollEnabled = false
            ) { page ->
                val newListToPage = getListToPage(page + 1, newListFilterBySearch, windowSizeClass)
                NewList(newListToPage, navController, windowSizeClass)
            }

            PagerMenu(pagerState)

        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No result",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 22.sp
                )
            }
        }
    }
}

@Composable
fun NewList(
    newListToPage: List<NewModelDate>,
    navController: NavHostController,
    windowSizeClass: WindowSizeClass
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(newListToPage) {
            if (windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact) { //Tablet
                SearchResultNewItemTablet(it) { newModelDate ->
                    navController.navigate(NavigationRoutes.Detail.createRoute(newModelDate.title))
                }

            } else { //Phone
                SearchResultNewItemPhone(it) { newModelDate ->
                    navController.navigate(NavigationRoutes.Detail.createRoute(newModelDate.title))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerMenu(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.LightGray
            ),
            enabled = pagerState.currentPage > 0
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
            )
        }

        Text(
            text = (pagerState.currentPage + 1).toString(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.LightGray
            ),
            enabled = (pagerState.currentPage + 1) < pagerState.pageCount
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
            )
        }
    }
}


@Composable
fun TimeFilterButton(isTimeAscending: Boolean, onIsTimeAscendingChanged: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Button(
            onClick = { onIsTimeAscendingChanged(!isTimeAscending) },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PagerGray),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(
                text = "Time",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Icon(
                imageVector = if (isTimeAscending) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}


@Composable
fun ResultSearchTextField(
    searchResultText: String,
    windowSizeClass: WindowSizeClass,
    onSearchResultTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = searchResultText,
        onValueChange = { onSearchResultTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = if (windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact) 36.dp else 26.dp),
        trailingIcon = {
            IconButton(onClick = { onSearchResultTextChanged("") }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            }
        },
        singleLine = true,
        maxLines = 1
    )
}


fun getFilterList(newList: List<NewModelDate>, searchResultText: String): List<NewModelDate> {
    return newList.filter { it.title.lowercase().startsWith(searchResultText.lowercase()) }
}

fun getListToPage(
    page: Int,
    newListFilterBySearch: List<NewModelDate>,
    windowSizeClass: WindowSizeClass
): List<NewModelDate> {
    val itemCount = if (windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact) 5 else 2
    val lastItemPage = (page * itemCount)

    return if (lastItemPage <= newListFilterBySearch.size)
        newListFilterBySearch.subList(lastItemPage - itemCount, lastItemPage)
    else newListFilterBySearch.subList(lastItemPage - itemCount, newListFilterBySearch.size)
}