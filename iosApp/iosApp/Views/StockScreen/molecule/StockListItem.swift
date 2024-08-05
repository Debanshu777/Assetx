//
//  StockListItem.swift
//  iosApp
//
//  Created by Debanshu Datta on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct StockListItem: View {
    let stock: Stock?
    let isLoading: Bool
    
    var body: some View {
        HStack {
            AsyncImage(url: stockImageURL(stock: stock, isLoading: isLoading)) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
            } placeholder: {
                shimmer(isLoading)
            }
            .frame(width: 40, height: 40)
            
            Spacer().frame(width: 10)
            
            VStack(alignment: .leading) {
                Text(stockName(stock: stock, isLoading: isLoading))
                    .font(.callout)
                    .frame(maxWidth: 250, alignment: .leading)
                    .background(shimmer(isLoading))
                
                Spacer().frame(height: 5)
                
                Text(stockTicker(stock: stock, isLoading: isLoading))
                    .font(.caption)
                    .foregroundColor(.gray)
                    .frame(minWidth: 210, alignment: .leading)
                    .background(shimmer(isLoading))
            }
            
            Spacer()
            
            VStack(alignment: .trailing) {
                Text(stockPrice(stock: stock, isLoading: isLoading))
                    .frame(minWidth: 100, alignment: .trailing)
                    .background(shimmer(isLoading))
                
                Spacer().frame(height: 10)
                
                HStack {
                    SVGImageView(url: URL(
                        string: (!isLoading) ? (stock != nil && stock!.change >= 0) ? "https://assets.tickertape.in/images/landing-page/52w_high.svg"
                        :"https://assets.tickertape.in/images/landing-page/52w_low.svg" : ""))
                    .frame(width: 15, height: 15)
                    .background(shimmer(isLoading))
                    
                    Spacer().frame(width: 5)
                    
                    Text(stockChange(stock: stock, isLoading: isLoading))
                        .foregroundColor(stock != nil && stock!.change >= 0 ? Color.green : Color.red)
                        .frame(minWidth: 15, alignment: .trailing)
                        .background(shimmer(isLoading))
                }
            }
        }
        .padding(.vertical, 15)
    }
    
    private func stockImageURL(stock: Stock?, isLoading: Bool) -> URL? {
        guard !isLoading, let stock = stock else { return nil }
        return URL(string: "https://assets.tickertape.in/stock-logos/\(stock.sid).png")
    }
    
    private func stockName(stock: Stock?, isLoading: Bool) -> String {
        return isLoading ? "" : stock?.name ?? ""
    }
    
    private func stockTicker(stock: Stock?, isLoading: Bool) -> String {
        return isLoading ? "" : stock?.ticker ?? ""
    }
    
    private func stockPrice(stock: Stock?, isLoading: Bool) -> String {
        guard !isLoading, let stock = stock else { return "" }
        return "INR \(stock.price.decimals(2))"
    }
    
    private func stockChange(stock: Stock?, isLoading: Bool) -> String {
        guard !isLoading, let stock = stock else { return "" }
        let change = abs(stock.change).decimals(2)
        return "\(change)%"
    }
    
    @ViewBuilder
    private func shimmer(_ isLoading: Bool) -> some View {
        if isLoading {
            ShimmerView()
        } else {
            Color.clear
        }
    }
}

struct ShimmerView: View {
    var body: some View {
        // Implement a shimmer effect view here
        Color.gray.opacity(0.3) // Placeholder for shimmer effect
    }
}
