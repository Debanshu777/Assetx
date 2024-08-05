//
//  PerformanceChart.swift
//  iosApp
//
//  Created by Debanshu Datta on 05/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct PerformanceChart: View {
    let points: [Float]
    
    var body: some View {
        GeometryReader { geometry in
            HStack(spacing: 0) {
                ForEach(0..<points.count-1, id: \.self) { index in
                    ChartSegment(
                        fromValue: points[index],
                        toValue: points[index + 1],
                        max: points.max() ?? 0,
                        min: points.min() ?? 0,
                        lineColor: points.last! > points.first! ? Color(hex: "19af55") : Color(hex: "d82f44")
                    )
                }
            }
        }
    }
}

struct ChartSegment: View {
    let fromValue: Float
    let toValue: Float
    let max: Float
    let min: Float
    let lineColor: Color
    
    var body: some View {
        GeometryReader { geometry in
            Path { path in
                let fromPoint = CGPoint(
                    x: 0,
                    y: geometry.size.height * CGFloat(1 - getValuePercentageForRange(value: fromValue, max: max, min: min))
                )
                let toPoint = CGPoint(
                    x: geometry.size.width,
                    y: geometry.size.height * CGFloat(1 - getValuePercentageForRange(value: toValue, max: max, min: min))
                )
                
                path.move(to: fromPoint)
                path.addLine(to: toPoint)
            }
            .stroke(lineColor, lineWidth: 3)
        }
    }
    
    private func getValuePercentageForRange(value: Float, max: Float, min: Float) -> Float {
        return (value - min) / (max - min)
    }
}

extension Color {
    init(hex: String) {
        let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)
        let a, r, g, b: UInt64
        switch hex.count {
        case 3: // RGB (12-bit)
            (a, r, g, b) = (255, (int >> 8) * 17, (int >> 4 & 0xF) * 17, (int & 0xF) * 17)
        case 6: // RGB (24-bit)
            (a, r, g, b) = (255, int >> 16, int >> 8 & 0xFF, int & 0xFF)
        case 8: // ARGB (32-bit)
            (a, r, g, b) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
        default:
            (a, r, g, b) = (1, 1, 1, 0)
        }

        self.init(
            .sRGB,
            red: Double(r) / 255,
            green: Double(g) / 255,
            blue:  Double(b) / 255,
            opacity: Double(a) / 255
        )
    }
}