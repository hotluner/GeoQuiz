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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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