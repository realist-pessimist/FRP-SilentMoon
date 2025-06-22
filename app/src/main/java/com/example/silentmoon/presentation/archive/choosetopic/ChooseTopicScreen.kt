package com.example.silentmoon.presentation.archive.choosetopic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.silentmoon.R
import com.example.silentmoon.presentation.ui.theme.SilentMoonTheme

private val topicsList: List<Int> = listOf(
  R.drawable.ic_grid_item_1,
  R.drawable.ic_grid_item_2,
  R.drawable.ic_grid_item_4,
  R.drawable.ic_grid_item_3,
  R.drawable.ic_grid_item_5,
  R.drawable.ic_grid_item_6,
  R.drawable.ic_grid_item_8,
  R.drawable.ic_grid_item_1,
)

@Composable
fun ChooseTopicScreen(
  modifier: Modifier = Modifier
) {
  Box(modifier = modifier) {
    Image(
      modifier = Modifier
          .padding(top = 80.dp)
          .fillMaxWidth(),
      painter = painterResource(R.drawable.ic_cloud_background),
      contentScale = ContentScale.FillWidth,
      contentDescription = null
    )
    Column(
      modifier = Modifier.padding(top = 80.dp, start = 20.dp, end = 20.dp),
    ) {
      Text(
        text = buildAnnotatedString {
          withStyle(
            SpanStyle(
              fontWeight = FontWeight.Bold,
              fontSize = 28.sp,
              color = Color(0xFF3F414E)
            )
          ) {
            append("What Brings you\n")
          }
          withStyle(
            SpanStyle(
              fontWeight = FontWeight.Normal,
              fontSize = 28.sp,
              color = Color(0xFF3F414E)
            )
          ) {
            append("to Silent Moon?")
          }
        }
      )
      Text(
        modifier = Modifier.padding(top = 10.dp),
        text = "choose a topic to focuse on:",
        style = TextStyle(
          fontSize = 20.sp,
          fontWeight = FontWeight.Light,
          color = Color(0xFF8F939D)
        )
      )
      LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 21.dp,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(top = 30.dp, bottom = 16.dp),
      ) {
        items(topicsList) {
          Image(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            painter = painterResource(it),
            contentDescription = null
          )
        }
      }
    }
  }
}

@Preview
@Composable
fun ChooseTopic_Preview() {
  SilentMoonTheme {
    ChooseTopicScreen()
  }
}