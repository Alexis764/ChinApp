package com.example.chinappsecond.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chinappsecond.core.NewModel
import com.example.chinappsecond.navigation.NavigationRoutes
import com.example.chinappsecond.ui.component.BasicNewPhone
import com.example.chinappsecond.ui.component.SubTitle
import com.example.chinappsecond.ui.component.Title
import com.example.chinappsecond.ui.component.TopAppBar
import com.example.chinappsecond.ui.theme.PagerGray

@Composable
fun DetailScreen(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    title: String,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    var firstTime by rememberSaveable { mutableStateOf(true) }

    val newModel: NewModel by detailViewModel.newModel.observeAsState(NewModel())
    if (firstTime) {
        detailViewModel.getNewModel(title)
        firstTime = false
    }

    val newList: List<NewModel> = detailViewModel.newList
    newList.forEach { detailViewModel.loadImage(it) }

    Column(Modifier.fillMaxSize()) {
        TopAppBar {
            IconButton(onClick = { navController.navigate(NavigationRoutes.Home.route) }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(onClick = { navController.navigate(NavigationRoutes.Search.route) }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Title(text = newModel.title, textAlign = TextAlign.Start, modifier = Modifier.padding(8.dp))

        if (windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact) { //Tablet
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 32.dp)
            ) {
                NewInfo(300.dp, newModel, Modifier.weight(2f)) {}
                Spacer(modifier = Modifier.width(32.dp))
                TabletNewList(newList, Modifier.weight(1f)) {
                    navController.navigate(NavigationRoutes.Detail.createRoute(it))
                }
            }

        } else { //Phone
            NewInfo(200.dp, newModel, Modifier.fillMaxSize()) {
                PhoneNewList(newList) {
                    navController.navigate(NavigationRoutes.Detail.createRoute(it))
                }
            }
        }

    }
}


@Composable
fun NewInfo(
    newImageHeight: Dp,
    newModel: NewModel,
    modifier: Modifier = Modifier,
    phoneList: @Composable () -> Unit
) {
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(8.dp)
            .verticalScroll(verticalScrollState)
    ) {
        SubTitle(text = newModel.Source, textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.height(4.dp))

        SubTitle(text = newModel.time, textAlign = TextAlign.Start, textColor = PagerGray)
        Spacer(modifier = Modifier.height(4.dp))

        Image(
            painter = painterResource(id = newModel.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(newImageHeight)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = newModel.text,
            fontSize = 15.sp,
            color = Color.Black
        )

        phoneList()
    }
}


@Composable
fun PhoneNewList(newList: List<NewModel>, onNavigateToDetailScreen: (String) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(newList) {
            Box(modifier = Modifier.width(300.dp)) {
                BasicNewPhone(newModel = it) { newModel ->
                    onNavigateToDetailScreen(newModel.title)
                }
            }
        }
    }
}


@Composable
fun TabletNewList(
    newList: List<NewModel>,
    modifier: Modifier = Modifier,
    onNavigateToDetailScreen: (String) -> Unit
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(newList) {
            BasicNewPhone(newModel = it) { newModel ->
                onNavigateToDetailScreen(newModel.title)
            }
        }
    }
}
