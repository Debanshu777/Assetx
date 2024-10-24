//
//  StockAssetView.swift
//  iosApp
//
//  Created by Debanshu Datta on 25/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

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
            if let assets = successState.stocks as? [AssetEntity] {
                ForEach(0..<assets.count, id: \.self) { index in
                    Text(assets[index].description())
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
