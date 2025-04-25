# 安全键盘 (Secure Keyboard)

这是一个跨平台（iOS和Android）的安全键盘实现，专为金融应用等需要高安全性的场景设计。该项目提供了防截屏、随机排列数字的自定义键盘，以增强密码输入的安全性。

## 功能特点

### 安全特性
- 数字键盘随机排列，每次显示时重新随机排列
- 防截屏和录屏保护
- 密码输入内容隐藏
- 禁用系统键盘，仅使用安全键盘输入

### 用户体验
- 简洁直观的界面
- 流畅的键盘显示/隐藏动画
- 清除和完成功能按钮
- 响应式布局，适配不同屏幕尺寸

## 技术栈

### iOS 实现
- Swift
- SwiftUI
- 使用 `privacySensitive` 和自定义安全控件

### Android 实现
- Kotlin
- Jetpack Compose
- 使用 `FLAG_SECURE` 防止截屏

## 项目结构

```
CustomKeyboard/
├── android/                # Android 项目
│   ├── app/                # 应用模块
│   │   ├── src/            # 源代码
│   │   │   ├── main/
│   │   │   │   ├── java/com/example/customkeyboard/
│   │   │   │   │   ├── MainActivity.kt       # 主界面
│   │   │   │   │   └── SecureKeyboard.kt     # 安全键盘组件
│   │   │   │   ├── res/                      # 资源文件
│   │   │   │   └── AndroidManifest.xml       # 应用清单
│   │   └── build.gradle    # 应用级构建配置
│   ├── build.gradle        # 项目级构建配置
│   └── settings.gradle     # Gradle设置
├── ios/                    # iOS 项目
│   ├── CustomKeyboard/
│   │   ├── ContentView.swift       # 主界面
│   │   ├── SecureKeyboard.swift    # 安全键盘组件
│   │   └── CustomKeyboardApp.swift # 应用入口
└── docs/                   # 文档
```

## 安装与运行

### Android

1. 使用 Android Studio 打开 `android` 目录
2. 等待 Gradle 同步完成
3. 点击运行按钮或使用 `Shift+F10` 运行应用

### iOS

1. 使用 Xcode 打开 `ios/CustomKeyboard.xcodeproj`
2. 选择目标设备或模拟器
3. 点击运行按钮或使用 `Cmd+R` 运行应用

## 安全注意事项

- 本项目仅提供基本的输入安全保护，生产环境中应结合其他安全措施
- 建议在实际应用中添加以下额外安全措施：
  - 输入内容的加密存储
  - 键盘显示时禁用系统返回键
  - 应用切换到后台时自动清除输入内容
  - 定期更新随机算法

## 许可证

[MIT License](LICENSE)
