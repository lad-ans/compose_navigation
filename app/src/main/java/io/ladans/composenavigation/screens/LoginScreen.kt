package io.ladans.composenavigation.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import io.ladans.composenavigation.viewmodels.SharedViewModel
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: SharedViewModel) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    var showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            confirmButton = { /*TODO*/ },
            title = {
                Text(text = "Título do Dialog")
            },
            text = {
                Text(text = "Conteúdo do diálogo")
            }
        )
    }

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by rememberSaveable {
        mutableStateOf(false)
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showBottomSheet = false
            },
            modifier = Modifier
                .fillMaxHeight(.4f),
            content = {
                Text(text = "Título do BottomSheet")
            },
        )
    }

    val loading by viewModel.loadingState.collectAsState()

    if (loading) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            Box(modifier = Modifier.size(100.dp)) {
                // circle progress
                CircularProgressIndicator(
                    color = Color.Green,
                    strokeWidth = 2.dp,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Login") }) }
    ) {
        var launchDialog by rememberSaveable {
            mutableStateOf(false)
        }
        var launchModal by rememberSaveable {
            mutableStateOf(false)
        }

        LazyColumn(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            content = {
                item {Spacer(modifier = Modifier.height(40.dp))}
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Checkbox(
                            checked = launchDialog,
                            onCheckedChange = {
                                launchDialog = it
                                if (launchModal) launchModal = false
                            }
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Abrir Dialog")
                    }
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Switch(
                            checked = launchModal,
                            onCheckedChange = {
                                launchModal = it
                                if (launchModal) launchDialog = false
                            }
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Abrir Modal")
                    }
                }
                item {Spacer(modifier = Modifier.height(40.dp))}
                item {
                    OutlinedTextField(
                        value = email.value,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "E-mail") },
                        onValueChange =  { value -> email.value = value }
                    )
                }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item {
                    OutlinedTextField(
                        value = password.value,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Password") },
                        onValueChange = { value -> password.value = value }
                    )
                }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item {
                    ElevatedButton(
                        onClick = {
//                            val dataLogin = DataLogin(email = email.value, password = password.value)

                            viewModel.updateState(
                                email = email.value,
                                password = password.value
                            )

                            if (!launchModal && !launchDialog) {
                                viewModel.navigateAsync(
                                    secs = 5.seconds,
                                    callback = { route ->
                                        navController.navigate(route) {
                                            popUpTo("login") {
                                                inclusive = true
                                            }
                                        }
                                    },
//                                    callback2 = {
//                                        navController.navigate("home")
//                                    },
                                )
                            }

                            if (launchModal) showBottomSheet = true
                            if (launchDialog) showDialog.value = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.Blue),
                    ) {
                        if (loading) Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        } else Text(
                            text =
                                if (launchModal) "Abrir Modal"
                                else if (launchDialog) "Abrir Dialog"
                                else "Entrar",
                            color = Color.White
                        )
                    }
                    ElevatedButton(
                        onClick = {
                            navController.navigate("custom") {
//                                popUpTo("login") {
//                                    inclusive = true
//                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.elevatedButtonColors(containerColor = Color(0xff436344)),
                    ) {
                        Text(
                            text = "Ir Para Tela Personalizada",
                            color = Color.Black
                        )
                    }
                }
            }
        )
    }
}