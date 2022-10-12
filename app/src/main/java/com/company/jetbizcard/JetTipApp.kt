package com.company.jetbizcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.jetbizcard.components.InputText
import com.company.jetbizcard.ui.theme.JetBizCardTheme

class JetTipApp : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetBizCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    BackgroundColumn()
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun BackgroundColumn() {
    Surface(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CreateCalculationBox()
        }
    }
}


@Composable
fun CreateTotalPerPersonCard(totalPerPerson: Double = 0.0) {
    Card(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(25.dp, 25.dp, 25.dp, 15.dp),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color(0xFFe6cff0),
        elevation = 4.dp) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
            Text(
                text = "Total per Person",
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp)
            Text(
                text = "$ ${"%.2f".format(totalPerPerson)}",
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 34.sp
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun CreateCalculationBox(onValChanged: (String) -> Unit = {}) {
    val billAmount = remember {
        mutableStateOf("")
    }
    val validBillState = remember (billAmount.value){
        billAmount.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val nbrOfPeople = remember {
        mutableStateOf(1)
    }
    val sliderPosition = remember {
        mutableStateOf(0f)
    }
    val totalTip = remember {
        mutableStateOf(0.0)
    }
    val totalPerPerson = remember {
        mutableStateOf(0.0)
    }

    CreateTotalPerPersonCard(totalPerPerson.value)

    Box(
        modifier = Modifier
            .padding(10.dp)
            .border(
                BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(10.dp)
            )
            .height(250.dp)
            .fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            InputText(
                state = billAmount,
                labelId = "Enter Bill",
                onAction = KeyboardActions() {
                    if(!validBillState) return@KeyboardActions
                    onValChanged(billAmount.value.trim())
                    keyboardController?.hide()
                }
            )
            //Part responsible for the split word + buttons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)) {
                Text(text = "Split", modifier = Modifier
                    .width(100.dp)
                    .wrapContentHeight())
                OutlinedButton(
                    onClick = {if(nbrOfPeople.value > 1) nbrOfPeople.value--},
                    shape = CircleShape,
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Add number of people to split")
                }
                Text(
                    text = "${nbrOfPeople.value}",
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(10.dp, 0.dp),
                    textAlign = TextAlign.Center)
                OutlinedButton(
                    onClick = {nbrOfPeople.value++},
                    shape = CircleShape,
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add number of people to split")
                }
            }
            //Part responsible for tip total
            Column(
                modifier = Modifier.padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)) {
                    Text(text = "Tip", modifier = Modifier
                        .width(150.dp)
                        .wrapContentHeight())
                    Text(
                        text = "$ ${"%.2f".format(totalTip.value)}",
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .padding(10.dp, 0.dp),
                        textAlign = TextAlign.Center)
                }
                //Tip percentage
                Text(
                    text = "${((sliderPosition.value) * 100).toInt()} %",
                    modifier = Modifier.padding(5.dp))
                //Slider
                Slider(
                    value = sliderPosition.value,
                    onValueChange = {sliderPosition.value = it},
                    steps = 8,
                    valueRange = 0f..1f,
                    onValueChangeFinished = {
                        if(validBillState){
                            totalTip.value = sliderPosition.value.toDouble() * billAmount.value.toDouble()
                        }
                        else {
                            totalTip.value = 0.0
                        }
                        totalPerPerson.value = CalculateTotalPerPerson(billAmount.value, totalTip.value, nbrOfPeople.value)

                    }
                )
            }
        }
    }
}

fun CalculateTotalPerPerson(billAmount: String, totalTip: Double, nbrOfPeople: Int): Double {
    return (billAmount.toDouble() + totalTip)/nbrOfPeople
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    JetBizCardTheme {
        BackgroundColumn()
    }
}