package com.company.jetbizcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.jetbizcard.ui.theme.JetBizCardTheme

class IntroToCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetBizCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    CreateCornedSurface()
                }
            }
        }
    }
}

@Composable
fun CreateCornedSurface() {
    val moneyCounter = remember {
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(5.dp),
        color = Color(0xFF546E7A),
        shape = RoundedCornerShape(15.dp)) {
        Column(verticalArrangement = Arrangement.Center
            , horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$${moneyCounter.value}",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(30.dp))
            CreateCircle(moneyCounter = moneyCounter.value) {
                moneyCounter.value += 10
            }
        }

    }
}

@Composable
fun CreateCircle(moneyCounter: Int = 0, updateMoneyCounter: (Int) -> Unit) {
    Card (modifier = Modifier
        .padding(5.dp)
        .size(105.dp)
        .clickable {
            updateMoneyCounter(moneyCounter)
        },
        shape = CircleShape,
        elevation = 4.dp) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = "Tap!",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.ExtraBold))
        }
        }
    }


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    JetBizCardTheme {
        CreateCornedSurface()
    }
}