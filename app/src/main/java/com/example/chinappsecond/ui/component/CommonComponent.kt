package com.example.chinappsecond.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chinappsecond.core.NewModel
import com.example.chinappsecond.core.NewModelDate
import com.example.chinappsecond.ui.theme.DateBackground
import com.example.chinappsecond.ui.theme.LogoBackGround
import com.example.chinappsecond.ui.theme.PagerGray

@Composable
fun LogoApp() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LogoBackGround
        )
    ) {
        Text(
            text = "NEWS",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp)
        )
    }
}


@Composable
fun TopAppBar(icons: @Composable () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LogoApp()
            Row(
                Modifier.weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                icons()
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
    }
}

//@Preview(showSystemUi = true)
@Composable
private fun TopAppBarPreview() {
    TopAppBar {
        Text(text = "Prof")
    }
}


@Composable
fun BasicNewPhone(newModel: NewModel, imageHeight: Dp = 200.dp, onItemClicked: (NewModel) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onItemClicked(newModel) }) {
        Image(
            painter = painterResource(id = newModel.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
                .clip(
                    RoundedCornerShape(16.dp)
                ),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        SubTitle(
            text = newModel.title,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun SearchResultNewItemPhone(newModelDate: NewModelDate, onItemClicked: (NewModelDate) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClicked(newModelDate) },
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = newModelDate.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
            Text(
                text = newModelDate.time.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DateBackground)
                    .align(Alignment.BottomCenter)
                    .padding(6.dp)
            )
        }
        SubTitle(
            text = newModelDate.title,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Text(
            text = newModelDate.text,
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 4,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}


@Composable
fun SearchResultNewItemTablet(newModelDate: NewModelDate, onItemClicked: (NewModelDate) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onItemClicked(newModelDate) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = newModelDate.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(300.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            SubTitle(text = newModelDate.title, textAlign = TextAlign.Start)
            SubTitle(
                text = newModelDate.time.toString(),
                textAlign = TextAlign.Start,
                textColor = PagerGray,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = newModelDate.text,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 4,
                color = Color.Black
            )
        }
    }
}


@Composable
fun SubTitle(
    text: String,
    textAlign: TextAlign,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black
) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
        color = textColor,
        modifier = modifier
    )
}


@Composable
fun Title(text: String, textAlign: TextAlign, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
        modifier = modifier
    )
}