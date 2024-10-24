//
//  StockHoldingView.swift
//  iosApp
//
//  Created by Debanshu Datta on 19/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct StockHoldingView: View {
    var viewModel = KoinHelper().getAppViewModel()
    @State var stockAssetState: DataState = DataStateUninitialized()
    var body: some View {
        StockAssetView(stockAssetState: $stockAssetState)
            .collect(flow: viewModel.stockAssetData, into: $stockAssetState)
    }
}
