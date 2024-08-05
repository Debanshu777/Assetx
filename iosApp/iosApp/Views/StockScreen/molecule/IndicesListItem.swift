//
//  IndicesListItem.swift
//  iosApp
//
//  Created by Debanshu Datta on 05/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct IndicesListItem: View {
    let indices: Indices
    
    var body: some View {
        VStack {
            HStack {
                Text(indices.name ?? "Null")
                Spacer()
                PerformanceChart(points: indices.points!.compactMap {
                    Float(($0 as? Point)!.price)
                })
                .frame(width: 80, height: 80)
                Spacer()
                VStack(alignment: .trailing) {
                    let lastPrice = (indices.points?.last as! Point as Point).price
                    let lastClose = Double(truncating:indices.lastClosePrice! as KotlinDouble)
                    
                    
                    Text("INR \(lastPrice.decimals(2))")
                    HStack {
                        SVGImageView(url: URL(
                            string: (lastPrice - lastClose >= 0) ? "https://assets.tickertape.in/images/landing-page/52w_high.svg"
                            :"https://assets.tickertape.in/images/landing-page/52w_low.svg"))
                        .frame(width: 15, height: 15)
                        Text("\(abs((lastPrice - lastClose)).decimals(2))%")
                            .foregroundColor(lastPrice - lastClose >= 0 ? Color.green : Color.red)
                    }
                    
                }
            }
            .padding(10)
            .background(Color.primary.opacity(0.1))
            .cornerRadius(8)
        }
    }
}
