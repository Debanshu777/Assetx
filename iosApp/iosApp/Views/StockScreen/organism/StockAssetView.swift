import SwiftUI
import shared

import SwiftUI
import shared

struct StockAssetView: View {
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
                
                VStack(alignment: .leading, spacing: 20) {
                    // Aggregator View
                    VStack(alignment: .leading, spacing: 10) {
                        HStack {
                            VStack(alignment: .leading) {
                                Text("Current")
                                    .font(.caption)
                                Text("₹\(aggregator.currentTotalValue, specifier: "%.2f")")
                                    .font(.title2)
                                    .bold()
                            }
                            Spacer()
                            VStack(alignment: .leading) {
                                Text("Total returns")
                                    .font(.caption)
                                Text("+₹\(aggregator.currentTotalValue - aggregator.totalInvested, specifier: "%.2f") (\(aggregator.totalReturnsPercentage, specifier: "%.2f")%)")
                                    .font(.title3)
                            }
                        }
                        HStack {
                            VStack(alignment: .leading) {
                                Text("Invested")
                                    .font(.caption)
                                Text("₹\(aggregator.totalInvested, specifier: "%.2f")")
                                    .font(.title3)
                            }
                            Spacer()
                            VStack(alignment: .leading) {
                                Text("1D returns")
                                    .font(.caption)
                                // Placeholder for 1D returns; assuming a fixed value for example
                                Text("+₹2,331 (1.85%)")
                                    .font(.title3)
                            }
                        }
                    }
                    .padding()
                    .cornerRadius(12)
                    .padding(.horizontal)
                    
                    // Sorting and Returns Label
                    HStack {
                        Text("Sort")
                            .font(.caption)
                        Spacer()
                        Text("Returns (%)")
                            .font(.caption)
                    }
                    .padding(.horizontal)
                    
                    // Listing View
                    List(assets, id: \.mappingId) { asset in
                        VStack(alignment: .leading, spacing: 5) {
                            Text(asset.name)
                                .font(.headline)
                            HStack {
                                Text("\(asset.quantity) shares")
                                    .font(.subheadline)
                                Spacer()
                                let profitValue = (asset.marketValue - asset.averagePurchasedValue) * Double(asset.quantity)
                                let profitPercentage = (asset.marketValue - asset.averagePurchasedValue) / asset.averagePurchasedValue * 100
                                VStack(alignment: .trailing){
                                    Text("+₹\(profitValue, specifier: "%.2f")")
                                        .font(.subheadline)
                                    Text("(\(profitPercentage, specifier: "%.2f")%)")
                                        .font(.caption)
                                }
                            }
                        }
                        .padding(.vertical, 5)
                    }
                    .listStyle(PlainListStyle())
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

// Define the structure for the aggregator data
struct AssetAggregator {
    let currentTotalValue: Double
    let totalInvested: Double
    let totalReturnsPercentage: Double
}
