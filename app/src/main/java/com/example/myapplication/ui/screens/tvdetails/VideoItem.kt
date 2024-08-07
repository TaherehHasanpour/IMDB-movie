package com.example.myapplication.ui.screens.tvdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.farzin.imdb.R
import com.example.myapplication.ui.theme.darkText
import com.example.myapplication.ui.theme.sectionContainerBackground
import com.farzin.imdb.utils.ImageHelper
import com.farzin.imdb.utils.MySpacerHeight
import com.farzin.imdb.utils.MySpacerWidth

@Composable
fun VideoItem(
    poster: String,
    title: String,
    type: String,
    onClick: () -> Unit,
) {


    MySpacerWidth(width = 10.dp)

    Column {
        Card(
            modifier = Modifier
                .width(170.dp)
                .height(160.dp)
                .clickable { onClick() },
            shape = Shapes().medium,
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.sectionContainerBackground)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.6f),
                    contentAlignment = Alignment.BottomStart
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageHelper.appendImage(poster),
                            imageLoader = ImageLoader.Builder(LocalContext.current)
                                .crossfade(true)
                                .crossfade(500)
                                .build(),
                            contentScale = ContentScale.FillBounds
                        ),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )



                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            painter = painterResource(R.drawable.play),
                            contentDescription = "",
                            modifier = Modifier
                                .size(16.dp)
                                .padding(start = 4.dp),
                            tint = Color.White
                        )

                        Text(
                            text = type,
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(start = 4.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )


                    }

                }

                Text(
                    text = title,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .weight(0.4f)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.darkText,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold
                )


            }

        }

        MySpacerHeight(height = 8.dp)
    }


}