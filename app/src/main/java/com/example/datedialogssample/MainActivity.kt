package com.example.datedialogssample

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.datedialogssample.ui.theme.DateDialogsSampleTheme
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DateDialogsSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ManufacturedDate()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManufacturedDate() {
        val dateState = rememberDatePickerState()
        val millisToLocalDate = dateState.selectedDateMillis?.let {
            DateUtils().convertMillisToLocalDate(it)
        }
        val dateToString = millisToLocalDate?.let {
            DateUtils().dateToString(millisToLocalDate)
        } ?: ""
    Column {
        DatePicker(
            dateFormatter = DatePickerFormatter(
                selectedDateSkeleton = "EE, dd MMM, yyyy",
                ),
            title = {
                    Text(text = "Manufactured Date")
            },
            state = dateState,
            showModeToggle = true
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = dateToString
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerWithDialog(
    modifier: Modifier = Modifier
) {
    val dateState = rememberDatePickerState()
    val millisToLocalDate = dateState.selectedDateMillis?.let {
        DateUtils().convertMillisToLocalDate(it)
    }
    val dateToString = millisToLocalDate?.let {
        DateUtils().dateToString(millisToLocalDate)
    } ?: "Choose Date"

    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    showDialog = true
                }),
            text = dateToString,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        if (showDialog) {
            DatePickerDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(
                       onClick = { showDialog = false }
                    ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = dateState,
                    showModeToggle = true
                )
            }
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true,
//    showSystemUi = true)
//@Composable
//fun ManufacturedDatePreview() {
//    DateDialogsSampleTheme {
//        ManufacturedDate()
//    }
//}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun ManufacturedDateWithDialogPreview() {
    DateDialogsSampleTheme {
        DatePickerWithDialog()
    }
}