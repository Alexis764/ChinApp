package com.example.chinappsecond.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chinappsecond.R
import com.example.chinappsecond.core.NewModel
import com.example.chinappsecond.core.TopNewModel
import com.example.chinappsecond.core.TopNewTemperatures
import com.example.chinappsecond.navigation.NavigationRoutes
import com.example.chinappsecond.ui.component.BasicNewPhone
import com.example.chinappsecond.ui.component.SubTitle
import com.example.chinappsecond.ui.component.Title
import com.example.chinappsecond.ui.component.TopAppBar

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavHostController,
    windowSizeClass: WindowSizeClass
) {
    val newList: List<NewModel> = homeViewModel.newList
    newList.forEach { homeViewModel.loadImage(it) }

    Column(Modifier.fillMaxSize()) {
        TopAppBar {
            IconButton(onClick = { navController.navigate(NavigationRoutes.Search.route) }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact) { //Tablet
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                TopNewTablet(homeViewModel, Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                News(2, newList.subList(0, 6), Modifier.weight(1f), 250.dp) {
                    navController.navigate(NavigationRoutes.Detail.createRoute(it))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            News(
                3, newList.subList(6, newList.size),
                Modifier
                    .fillMaxWidth()
                    .weight(1f), 250.dp
            ) {
                navController.navigate(NavigationRoutes.Detail.createRoute(it))
            }

        } else { //Phone
            TopNewPhone(homeViewModel)
            Spacer(modifier = Modifier.height(8.dp))

            News(2, newList, imageHeight = 200.dp) {
                navController.navigate(NavigationRoutes.Detail.createRoute(it))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun News(
    cells: Int,
    newList: List<NewModel>,
    modifier: Modifier = Modifier,
    imageHeight: Dp = 200.dp,
    onNavigateDetail: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(cells),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(newList) {
            BasicNewPhone(it, imageHeight) { newModel ->
                onNavigateDetail(newModel.title)
            }
        }
    }
}

@Composable
fun TopNewTablet(homeViewModel: HomeViewModel, modifier: Modifier = Modifier) {
    val topNewModel: TopNewModel by homeViewModel.topNewModel.observeAsState(TopNewModel())

    Column(modifier.padding(start = 8.dp)) {
        Title(topNewModel.title, TextAlign.Start)
        Spacer(modifier = Modifier.height(4.dp))

        TopNewTabletImage(topNewModel.image)
        Spacer(modifier = Modifier.width(4.dp))

        SubTitle(topNewModel.Source, TextAlign.Start)
        Spacer(modifier = Modifier.height(4.dp))
        MyTopNewTemperaturesList(topNewModel.updatalist)
    }
}

@Composable
fun TopNewPhone(homeViewModel: HomeViewModel) {
    val verticalScroll = rememberScrollState()
    val topNewModel: TopNewModel by homeViewModel.topNewModel.observeAsState(TopNewModel())

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Title(topNewModel.title, TextAlign.Start)
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .horizontalScroll(verticalScroll)
        ) {
            TopNewPhoneImage(topNewModel.image)

            Column(Modifier.padding(vertical = 12.dp, horizontal = 8.dp)) {
                SubTitle(topNewModel.Source, TextAlign.Start, Modifier.width(350.dp))
                Spacer(modifier = Modifier.height(4.dp))
                MyTopNewTemperaturesList(topNewModel.updatalist)
            }
        }
    }
}

@Composable
fun MyTopNewTemperaturesList(temperatureList: List<TopNewTemperatures>) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        items(temperatureList) {
            TemperatureItem(it)
        }
    }
}

@Composable
fun TemperatureItem(temperature: TopNewTemperatures) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_circle),
            contentDescription = null,
            modifier = Modifier.size(8.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = temperature.content,
            fontSize = 13.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun TopNewPhoneImage(image: Int) {
    Image(
        painter = painterResource(image),
        contentDescription = null,
        modifier = Modifier
            .width(350.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun TopNewTabletImage(image: Int) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Crop
    )
}
