package io.ladans.composenavigation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScreen(navController: NavController) {
    val localDensity = LocalDensity.current

    var topAppBarHeight by remember {
        mutableStateOf(0.dp)
    }

    Scaffold(
        topBar = {
//            TopAppBar(
//                title = {
//                        Text(text = "Minha Página Customizada")
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.Cyan
//                ),
//                navigationIcon = {
//                    IconButton(
//                        onClick = {
//                            navController.navigateUp()
//                        }
//                    ) {
//                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
//                    }
//                },
//                modifier = Modifier
//                    .onGloballyPositioned {
//                        topAppBarHeight = with(localDensity) { it.size.height.toDp() }
//                    }
//            )
        }
    ) {
//        Column {
//            Surface(
//                color = Color(0xff345654),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Person,
//                    contentDescription = "",
//                    tint = Color.White,
//                )
//            }
//            Box(
//                modifier = Modifier
//                    .background(Color(0xff985654))
//                    .fillMaxWidth(.5f)
//                    .height(50.dp)
//            )
//            Box(
//                modifier = Modifier
//                    .background(Color(0xff985654))
//                    .fillMaxWidth()
//                    .height(100.dp)
//            )
//        }
        MyComposable(appBArHeight = topAppBarHeight)
    }
}

@Composable
fun MyComposable(modifier: Modifier = Modifier, appBArHeight: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(
                        Color.Magenta,
                        Color.Blue,
                    )
                )
            )
            .padding(30.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(.8f)
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(listOf(Color.LightGray, Color.Cyan)),
                    RoundedCornerShape(10.dp)
                )
                .align(Alignment.BottomCenter),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-50).dp),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Surface(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(.6f)
                        .background(Color.DarkGray, shape = RoundedCornerShape(10.dp))
                        .padding(
                            top = 100.dp,
                        )
                        .align(Alignment.TopCenter),
                ) {}
            }
        }
    }
}