package com.example.silentmoon.frp.exercises.categorytheory.di

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.feature.reservation.ReservationRequest
import com.example.feature.reservation.makeReservation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Композ-экран бронирования
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen() {
  // Состояние формы
  var tableId by remember { mutableStateOf("") }
  var customerName by remember { mutableStateOf("") }
  var guests by remember { mutableStateOf("") }

  // Состояние загрузки
  var isLoading by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf<String?>(null) }
  var successMessage by remember { mutableStateOf<String?>(null) }

  // Создаем контекст с зависимостями
  val reservationContext = remember {
    buildReservationContext()
  }

  var showDatePicker by remember { mutableStateOf(false) }
  val datePickerState = rememberDatePickerState()
  val selectedDate = datePickerState.selectedDateMillis?.let {
    convertMillisToDate(it)
  } ?: ""

  val focusManager = LocalFocusManager.current
  val keyboardController = LocalSoftwareKeyboardController.current

  val fieldsIsBlank =
    tableId.isBlank() || customerName.isBlank() || selectedDate.isBlank() || guests.isBlank()

  Box(
    modifier = Modifier
        .fillMaxSize()
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
  ) {
    Column(
      modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      // Поля формы
      OutlinedTextField(
        value = tableId,
        onValueChange = { tableId = it },
        label = { Text("Номер столика") },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
      )

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = customerName,
        onValueChange = { customerName = it },
        label = { Text("Ваше имя") }
      )

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = selectedDate,
        onValueChange = { },
        label = { Text("Дата брони") },
        readOnly = true,
        trailingIcon = {
          IconButton(onClick = { showDatePicker = !showDatePicker }) {
            Icon(
              imageVector = Icons.Default.DateRange,
              contentDescription = "Select date"
            )
          }
        },
      )

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = guests,
        onValueChange = { guests = it },
        label = { Text("Количество гостей") },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
      )

      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = {
          if (fieldsIsBlank) {
            errorMessage = "Заполните все поля"
            return@Button
          }

          val request = ReservationRequest(
            tableId = tableId.toInt(),
            customerName = customerName,
            date = selectedDate,
            guests = guests.toInt()
          )

          // Запускаем корутину
          CoroutineScope(Dispatchers.IO).launch {
            isLoading = true
            delay(1000) //Имитация загрузки
            reservationContext.makeReservation(request)
            isLoading = false
          }
        },
        modifier = Modifier
            .width(320.dp)
            .height(64.dp),
        enabled = !isLoading
      ) {
        if (isLoading) {
          CircularProgressIndicator(
            modifier = Modifier.align(Alignment.CenterVertically),
            color = Color.White
          )
        } else {
          Text("Забронировать столик")
        }
      }

      // Сообщения об ошибках/успехе
      errorMessage?.let { message ->
        Text(
          text = message,
          color = Color.Red,
          modifier = Modifier.padding(8.dp)
        )
      }

      successMessage?.let { message ->
        Text(
          text = message,
          color = Color.Green,
          modifier = Modifier.padding(8.dp)
        )
      }

      if (showDatePicker) {
        Popup(
          onDismissRequest = { showDatePicker = false },
          alignment = Alignment.TopStart
        ) {
          Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 64.dp)
                .shadow(elevation = 4.dp)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
          ) {
            DatePicker(
              state = datePickerState,
              showModeToggle = false
            )
          }
        }
      }
    }
  }
}

fun convertMillisToDate(millis: Long): String {
  val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
  return formatter.format(Date(millis))
}