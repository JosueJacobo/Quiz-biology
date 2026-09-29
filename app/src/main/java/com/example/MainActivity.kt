package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BottomNavBar
import com.example.ui.components.ProfileAuthDialog
import com.example.ui.components.TopHeaderBar
import com.example.ui.screens.AddQuestionScreen
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.screens.LevelsMapScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QuizGameScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.QuizViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                BioQuizApp()
            }
        }
    }
}

@Composable
fun BioQuizApp(viewModel: QuizViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val allProgress by viewModel.allProgress.collectAsStateWithLifecycle()
    val leaderboard by viewModel.leaderboard.collectAsStateWithLifecycle()
    val customQuestions by viewModel.customQuestions.collectAsStateWithLifecycle()
    val quizState by viewModel.quizState.collectAsStateWithLifecycle()
    val showAuthDialog by viewModel.showAuthDialog.collectAsStateWithLifecycle()
    val snackMessage by viewModel.snackMessage.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackMessage) {
        snackMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnack()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (currentScreen != AppScreen.QUIZ_GAME) {
                TopHeaderBar(
                    user = currentUser,
                    onOpenAuth = { viewModel.openAuthDialog() },
                    onNavigateToAddQuestion = { viewModel.navigateTo(AppScreen.ADD_QUESTION) },
                    onNavigateToProfile = { viewModel.navigateTo(AppScreen.PROFILE) }
                )
            }
        },
        bottomBar = {
            if (currentScreen != AppScreen.QUIZ_GAME) {
                BottomNavBar(
                    currentScreen = currentScreen,
                    isCreator = currentUser?.isCreator == true,
                    onNavigate = { viewModel.navigateTo(it) }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                AppScreen.LEVELS_MAP -> {
                    LevelsMapScreen(
                        user = currentUser,
                        progressList = allProgress,
                        onStartLevel = { levelNum -> viewModel.startLevel(levelNum) }
                    )
                }

                AppScreen.QUIZ_GAME -> {
                    QuizGameScreen(
                        state = quizState,
                        onSelectOption = { idx -> viewModel.selectOption(idx) },
                        onProceedNext = { viewModel.proceedToNextQuestion() },
                        onRepeatLevel = { viewModel.repeatLevel() },
                        onPlayNextLevel = { viewModel.playNextLevel() },
                        onExitQuiz = { viewModel.navigateTo(AppScreen.LEVELS_MAP) }
                    )
                }

                AppScreen.LEADERBOARD -> {
                    LeaderboardScreen(
                        currentUser = currentUser,
                        leaderboard = leaderboard
                    )
                }

                AppScreen.ADD_QUESTION -> {
                    AddQuestionScreen(
                        currentUser = currentUser,
                        customQuestions = customQuestions,
                        onLoginAsCreator = { viewModel.loginAsCreatorShortcut() },
                        onAddQuestion = { level, category, text, a, b, c, d, correct, expl ->
                            viewModel.addCustomQuestion(level, category, text, a, b, c, d, correct, expl)
                        },
                        onDeleteQuestion = { q -> viewModel.deleteCustomQuestion(q) }
                    )
                }

                AppScreen.PROFILE -> {
                    ProfileScreen(
                        user = currentUser,
                        progressList = allProgress,
                        onOpenAuthDialog = { viewModel.openAuthDialog() },
                        onResetProgress = { viewModel.resetUserProgress() }
                    )
                }
            }
        }

        if (showAuthDialog) {
            ProfileAuthDialog(
                currentUser = currentUser,
                onDismiss = { viewModel.closeAuthDialog() },
                onLoginUser = { email, username, isCreator ->
                    viewModel.switchUser(email, username, isCreator)
                }
            )
        }
    }
}
