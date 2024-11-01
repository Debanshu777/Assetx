//
//  StockAssetAggregatorView.swift
//  iosApp
//
//  Created by Debanshu Datta on 01/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct StockAssetAggregatorView: View {
    @Binding var stockAssetState: DataState
    
    private func calculateAggregator(assets: [DatabaseAssetEntity]) -> AssetAggregator {
        let totalInvested = assets.reduce(0) { $0 + $1.averagePurchasedValue * Double($1.quantity) }
        let currentTotalValue = assets.reduce(0) { $0 + $1.marketValue * Double($1.quantity) }
        let totalReturns = currentTotalValue - totalInvested
        let totalReturnsPercentage = totalInvested > 0 ? (totalReturns / totalInvested) * 100 : 0
        
        return AssetAggregator(
            currentTotalValue: currentTotalValue,
            totalInvested: totalInvested,
            totalReturnsPercentage: totalReturnsPercentage
        )
    }
    
    var body: some View {
        switch stockAssetState {
        case is DataStateError:
            Text("DataStateError")
        case is DataStateLoading:
            Text("DataStateLoading")
        case let successState as DataStateSuccess<AnyObject>:
            if let assets = successState.stocks as? [DatabaseAssetEntity] {
                let aggregator = calculateAggregator(assets: assets)
                VStack(spacing: 0){
                    VStack(alignment: .leading, spacing: 10) {
                        HStack {
                            VStack(alignment: .leading) {
                                Text("Current")
                                    .font(.caption)
                                Text("₹\(aggregator.currentTotalValue, specifier: "%.2f")")
                                    .bold()
                            }
                            Spacer()
                            VStack(alignment: .trailing) {
                                Text("Total returns")
                                    .font(.caption)
                                Text("+₹\(aggregator.currentTotalValue - aggregator.totalInvested, specifier: "%.2f") (\(aggregator.totalReturnsPercentage, specifier: "%.2f")%)")
                            }
                        }
                        HStack {
                            VStack(alignment: .leading) {
                                Text("Invested")
                                    .font(.caption)
                                Text("₹\(aggregator.totalInvested, specifier: "%.2f")")
                                
                            }
                            Spacer()
                            VStack(alignment: .trailing) {
                                Text("1D returns")
                                    .font(.caption)
                                Text("+₹2,331 (1.85%)")
                            }
                        }
                    }
                    .padding()
                }
            } else {
                Text("Unexpected data format")
            }
        case is DataStateUninitialized:
            Text("DataStateUninitialized")
        default:
            Text("error")
        }
    }
}

struct AssetAggregator {
    let currentTotalValue: Double
    let totalInvested: Double
    let totalReturnsPercentage: Double
}
