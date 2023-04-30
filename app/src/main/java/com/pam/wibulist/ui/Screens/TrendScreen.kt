package com.pam.wibulist.ui.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.pam.wibulist.models.AnimeBannerModel
import com.pam.wibulist.models.AnimeFullModel
import com.pam.wibulist.models.AnimeTrendingViewModel


@Composable
fun MainScreenView(
    avm: AnimeTrendingViewModel,
    navController: NavController
) {
    LaunchedEffect(
        Unit,
        block = {
            avm.getAnimeTrendingList()
        }
    )
    Column {
        Text(
            text = "Top Anime",
            fontSize = 24.sp,
            fontWeight= FontWeight.SemiBold,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.padding(start = 20.dp, top = 10.dp)
        )

        when {
            avm.errorMessage.isEmpty() -> {
                AvmList(avl = avm.animeTrendingList) { animeId, animeTitle, animeImgUrl, animeGenre, animeDeskripsi, animeRating, animeRelease ->
                    Log.d("ClickItem", "this is anime id: $animeId")
                    navController.navigate("Detail?id=$animeId?title=$animeTitle?imgUrl=$animeImgUrl?genre=$animeGenre?Deskripsi=$animeDeskripsi?rating=$animeRating?release=$animeRelease")
                }
            }
            else -> Log.e("AVM", "Something happened")
        }
    }
}
@Composable
fun AvmList(avl: List<AnimeBannerModel>, itemClick: (index: Int, title: String, imgUrl: String, genre: String, Deskripsi:String, rating:String, release:String)-> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxSize()
        .padding(20.dp)
    ) {
        Text(
            text = "Trending Now",
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        val scrollState = rememberScrollState()

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            itemsIndexed(avl) { index, item ->
                Box(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp)
                        .clickable {
                            itemClick(
                                item.id,
                                item.title,
                                item.imgUrl,
                                item.genre,
                                item.Deskripsi,
                                item.rating,
                                item.release,

                                )
                        }
                ) {
                    Column {
                        Image(
                            painter = rememberImagePainter(
                                data = item.imgBanner,
                                builder = {
                                    scale(Scale.FILL)
                                    placeholder(coil.compose.base.R.drawable.notification_action_background)
                                }
                            ),
                            contentDescription = item.Deskripsi,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f/9f)
                        )
                        Column(modifier = Modifier.padding(4.dp)) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.caption,
                            )

                            Text(
                                text = item.genre,
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier
                                    .background(
                                        Color.LightGray
                                    )
                                    .padding(4.dp)
                            )
                            Text(
                                text = item.Deskripsi,
                                style = MaterialTheme.typography.body1,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

    }
}

