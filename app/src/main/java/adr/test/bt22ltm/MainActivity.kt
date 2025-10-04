package adr.test.bt22ltm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import adr.test.bt22ltm.ui.theme.BT22LTMTheme
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { BT22LTMTheme { EmailScreen() } }
    }
}

@Composable
fun EmailScreen() {
    var email by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf<String?>(null) }
    var isError by rememberSaveable { mutableStateOf(false) }
    val focus = LocalFocusManager.current

    fun validate() {
        val e = email.trim()
        when {
            e.isEmpty() -> {
                message = "Email không hợp lệ"
                isError = true
            }
            !e.contains("@") -> {
                message = "Email không đúng định dạng"
                isError = true
            }
            else -> {
                message = "Bạn đã nhập email hợp lệ"
                isError = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        Text(
            text = "Thực hành 02",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                message = null // xoá thông báo khi người dùng nhập lại
            },
            modifier = Modifier.fillMaxWidth(0.9f),
            placeholder = { Text("Email") },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    validate()
                    focus.clearFocus()
                }
            )
        )

        if (message != null) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = message!!,
                color = if (isError) Color(0xFFD32F2F) else Color(0xFF2E7D32),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        } else {
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = {
                validate()
                focus.clearFocus()
            },
            shape = RoundedCornerShape(26.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3),
                contentColor = Color.White
            )
        ) {
            Text("Kiểm tra", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmailPreview() {
    BT22LTMTheme { EmailScreen() }
}
