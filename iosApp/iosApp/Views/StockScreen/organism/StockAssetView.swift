import SwiftUI
import shared

struct StockAssetView: View {
    @Binding var stockAssetState: DataState
    
    var body: some View {
        switch stockAssetState {
        case is DataStateError:
            Text("DataStateError")
        case is DataStateLoading:
            Text("DataStateLoading")
        case let successState as DataStateSuccess<AnyObject>:
            if let assets = successState.stocks as? [DatabaseAssetEntity] {
                ForEach(assets, id: \.mappingId) { asset in
                    HStack{
                        VStack(alignment: .leading){
                            Text(asset.name)
                                .font(.headline)
                            Text("\(asset.quantity) shares")
                                .font(.subheadline)
                        }
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
                    .padding(.vertical,5)
                    .safeAreaPadding(.horizontal)
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
