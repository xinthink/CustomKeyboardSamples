package com.example.customkeyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 安全键盘按钮组件
 */
@Composable
fun SecureKeyboardButton(
    text: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onClick(text) },
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.5f),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFF5F5F5),
            contentColor = Color.Black
        )
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * 安全键盘组件
 * 实现随机排列的数字键盘，每次显示时数字位置随机变化
 */
@Composable
fun SecureKeyboard(
    onKeyPress: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // 使用remember和mutableStateOf来存储随机排列的数字
    val numbers = remember {
        mutableStateOf((0..9).map { it.toString() }.shuffled())
    }

    // 每次组件重组时重新随机排列数字
    LaunchedEffect(Unit) {
        numbers.value = (0..9).map { it.toString() }.shuffled()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 创建4行按键
        for (row in 0 until 4) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (row < 3) {
                    // 前三行每行3个数字按钮
                    for (col in 0 until 3) {
                        val index = row * 3 + col
                        SecureKeyboardButton(
                            text = numbers.value[index],
                            onClick = onKeyPress,
                            modifier = Modifier.weight(1f)
                        )
                    }
                } else {
                    // 最后一行：清除、0、完成
                    SecureKeyboardButton(
                        text = stringResource(R.string.clear),
                        onClick = { onKeyPress("CLEAR") },
                        modifier = Modifier.weight(1f)
                    )

                    SecureKeyboardButton(
                        text = numbers.value[9],
                        onClick = onKeyPress,
                        modifier = Modifier.weight(1f)
                    )

                    SecureKeyboardButton(
                        text = stringResource(R.string.done),
                        onClick = { onKeyPress("DONE") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
