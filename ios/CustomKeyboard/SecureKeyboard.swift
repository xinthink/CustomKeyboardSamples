//
//  SecureKeyboard.swift
//  CustomKeyboard
//

import SwiftUI

struct SecureKeyboardButton: View {
    let number: String
    let action: (String) -> Void

    var body: some View {
        Button(action: { action(number) }) {
            Text(number)
                .font(.title)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .background(Color(.systemGray6))
                .cornerRadius(10)
        }
        .buttonStyle(PlainButtonStyle())
    }
}

struct SecureKeyboard: View {
    let onKeyPress: (String) -> Void
    @State private var numbers: [String]

    init(onKeyPress: @escaping (String) -> Void) {
        self.onKeyPress = onKeyPress
        // 初始化时随机排列数字键盘
        _numbers = State(initialValue: (0...9).map(String.init).shuffled())
    }

    var body: some View {
        VStack(spacing: 8) {
            ForEach(0..<4) { row in
                HStack(spacing: 8) {
                    if row < 3 {
                        ForEach(0..<3) { col in
                            let index = row * 3 + col
                            SecureKeyboardButton(number: numbers[index], action: onKeyPress)
                        }
                    } else {
                        // 最后一行：清除、0、完成
                        SecureKeyboardButton(number: "清除", action: { _ in
                            onKeyPress("CLEAR")
                        })
                        SecureKeyboardButton(number: numbers[9], action: onKeyPress)
                        SecureKeyboardButton(number: "完成", action: { _ in
                            onKeyPress("DONE")
                        })
                    }
                }
            }
        }
        .padding()
        .background(Color(.systemBackground))
        .onAppear {
            // 每次显示键盘时重新随机排列数字
            numbers = (0...9).map(String.init).shuffled()
        }
    }
}

#Preview {
    SecureKeyboard { key in
        print("Pressed key: \(key)")
    }
}
