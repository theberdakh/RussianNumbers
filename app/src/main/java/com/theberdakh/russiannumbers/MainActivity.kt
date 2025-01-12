package com.theberdakh.russiannumbers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theberdakh.russiannumbers.ui.theme.RussianNumbersTheme

class MainActivity : ComponentActivity() {
    private var showAnswer = mutableStateOf(false)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RussianNumbersTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            actions = {
                                // TODO add Icon to change max Number & show answer settings in the top bar
                                IconButton(onClick = {
                                    when (showAnswer.value) {
                                        true -> showAnswer.value = false
                                        false -> showAnswer.value = true
                                    }
                                }) {
                                    Icon(
                                        imageVector =
                                        when (showAnswer.value) {
                                            true -> Icons.Filled.Close
                                            false -> Icons.Filled.Info
                                        },
                                        contentDescription = "Expand"
                                    )
                                }
                            },
                            title = { Text(text = "Russian Numbers") }
                        )
                    }) { innerPadding ->
                    MainContent(Modifier.padding(innerPadding))
                }
            }
        }
    }


    enum class ButtonState {
        GENERATE, CHECK, INCORRECT
    }

    @Composable
    private fun MainContent(modifier: Modifier) {
        var randomNumber by remember { mutableIntStateOf(0) }
        var userInput by remember { mutableStateOf("") }
        var buttonState by remember { mutableStateOf(ButtonState.GENERATE) }
        var randomNumberColor by remember { mutableStateOf(Color.Black) }

        if (showAnswer.value) {
            AlertDialog(
                onDismissRequest = { showAnswer.value = false },
                title = { Text("Answer") },
                text = {
                    Text(
                        text = RussianNumbers.getRussianNumber(randomNumber)
                    )
                },
                confirmButton = {
                    // TODO add effect with Spoiler effect to show answer when Show Answer button clicked
                },
                dismissButton = {
                    TextButton(onClick = { showAnswer.value = false }) {
                        Text("Close")
                    }
                }
            )
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Write correct spelling of the number in Russian:",
                textAlign = TextAlign.Center,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "$randomNumber",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 24.sp, color = randomNumberColor),
            )

            TextField(
                value = userInput,
                onValueChange = {
                    userInput = it
                    buttonState = ButtonState.CHECK
                    randomNumberColor = Color.Black
                },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                onClick = {
                    when (buttonState) {
                        ButtonState.GENERATE -> {
                            userInput = ""
                            buttonState = ButtonState.CHECK
                            randomNumber = (0..1000).random()
                        }

                        ButtonState.CHECK -> {
                            if (userInput == RussianNumbers.getRussianNumber(randomNumber)) {
                                buttonState = ButtonState.GENERATE
                                randomNumber = (0..1000).random()
                                userInput = ""
                                randomNumberColor = Color.Green
                            } else {
                                buttonState = ButtonState.INCORRECT
                                randomNumberColor = Color.Red
                            }
                        }

                        ButtonState.INCORRECT -> {
                            userInput = ""
                            buttonState = ButtonState.CHECK

                        }
                    }
                }
            ) {
                val buttonText = when (buttonState) {
                    ButtonState.GENERATE -> "Generate random number"
                    ButtonState.CHECK -> "Check"
                    ButtonState.INCORRECT -> {
                        "Incorrect! Try again"
                    }
                }
                Text(text = buttonText)
            }
        }
    }
}
