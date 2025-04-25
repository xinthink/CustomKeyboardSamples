//
//  ContentView.swift
//  CustomKeyboard
//
//  Created by Yingxin Wu on 2025/3/4.
//

import SwiftUI

struct ContentView: View {
    @State private var password = ""
    @State private var showKeyboard = false

    var body: some View {
        VStack(spacing: 20) {
            Text("安全输入演示")
                .font(.title)
                .padding()

            Button(action: {
                showKeyboard.toggle()
            }) {
                Text(showKeyboard ? "隐藏键盘" : "显示键盘")
                    .padding()
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(8)
            }

            ZStack {
                SecureField("请输入密码", text: $password)
                    .textContentType(.password)
                    .keyboardType(.numberPad)
//                    .disabled(true)
                    .disableAutocorrection(true)
                    .privacySensitive(true)
                    .onAppear {
//                      UIApplication.shared.secureView()
                    }
                    .multilineTextAlignment(.center)
                    .padding()
                    .background(RoundedRectangle(cornerRadius: 5).stroke())
                    .onChange(of: password) { oldValue, newValue in
                        print("密码从 \(oldValue) 变更为 \(newValue)")
                    }

                // 添加透明覆盖层来处理点击事件
                Color.clear
                    .contentShape(Rectangle())
                    .onTapGesture {
                        showKeyboard.toggle()
                    }
            }

          if showKeyboard {
                SecureKeyboard { key in
                    switch key {
                    case "CLEAR":
                        password = ""
                    case "DONE":
                        showKeyboard = false
                    default:
                        password += key
                    }
                }
                .transition(.move(edge: .bottom))
            }

            Spacer()
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
