package com.example.geoquiz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geoquiz.ui.theme.GeoQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuizTheme {
                GeoQuizScreen()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoQuizScreen() {
    // Состояния приложения
    var currentIndex by remember { mutableStateOf(0) } // Индекс текущего вопроса
    var score by remember { mutableStateOf(0) } // Счет правильных ответов
    var isAnswered by remember { mutableStateOf(false) } // Ответил ли пользователь на текущий вопрос

    val currentQuestion = questionBank[currentIndex]
    val isLastQuestion = currentIndex == questionBank.size - 1
    val context = LocalContext.current

    fun checkAnswer(userAnswer: Boolean) {
        if (!isAnswered) {
            isAnswered = true // Блокируем кнопки, если не отвечено
            val isCorrect = userAnswer == currentQuestion.answerTrue
            if (isCorrect) {
                score++
                Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Incorrect!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("GeoQuiz") }) // Верхняя панель
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Текст вопроса
            Text(
                text = currentQuestion.textResId,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TRUE Button
                if (!isAnswered) { // <--- Делаем кнопку невидимой после ответа
                    Button(
                        onClick = { checkAnswer(true) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)) // Фиолетовый цвет
                    ) {
                        Text("TRUE")
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f)) // Занимаем место
                }

                // FALSE Button
                if (!isAnswered) { // <--- Делаем кнопку невидимой после ответа
                    Button(
                        onClick = { checkAnswer(false) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)) // Фиолетовый цвет
                    ) {
                        Text("FALSE")
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f)) // Занимаем место
                }
            }

            // Кнопка NEXT
            Button(
                onClick = {
                    if (!isLastQuestion) {
                        currentIndex++ // Переходим к следующему вопросу
                        isAnswered = false // Сбрасываем состояние ответа
                    } else {
                        Toast.makeText(context, "Your final score: $score/${questionBank.size}", Toast.LENGTH_LONG).show()
                    }
                },
                enabled = isAnswered && !isLastQuestion, // <--- Кнопка активна только после ответа
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)),
                modifier = Modifier
                    .align(Alignment.End) // Выравнивание по правому краю
            ) {
                Text("NEXT")
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next")
            }

            if (isLastQuestion && isAnswered) {
                Text(
                    text = "Quiz Finished! Final Score: $score/${questionBank.size}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GeoQuizScreenPreview() {
    GeoQuizTheme {
        GeoQuizScreen()
    }
}