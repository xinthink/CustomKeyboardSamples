package com.example.customkeyboard

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置FLAG_SECURE防止截屏和录屏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        setContent {
            CustomKeyboardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SecureInputScreen()
                }
            }
        }
    }
}

@Composable
fun CustomKeyboardTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        ),
        content = content
    )
}

@Composable
fun SecureInputScreen() {
    // 状态管理
    var password by remember { mutableStateOf("") }
    var showKeyboard by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 标题
        Text(
            text = stringResource(R.string.secure_input_demo),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // 显示/隐藏键盘按钮
        Button(
            onClick = { showKeyboard = !showKeyboard },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = if (showKeyboard)
                    stringResource(R.string.hide_keyboard)
                else
                    stringResource(R.string.show_keyboard),
                modifier = Modifier.padding(8.dp)
            )
        }

        // 密码输入框
        Box(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = password,
                onValueChange = { /* 不允许直接修改，只通过安全键盘输入 */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(5.dp)
                    ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.enter_password),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                readOnly = true,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )

            // 透明覆盖层处理点击事件
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { showKeyboard = !showKeyboard }
                    .background(Color.Transparent)
            )
        }

        // 安全键盘
        AnimatedVisibility(
            visible = showKeyboard,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            SecureKeyboard(
                onKeyPress = { key ->
                    when (key) {
                        "CLEAR" -> password = ""
                        "DONE" -> showKeyboard = false
                        else -> password += key
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
