//
//  StockExploreView.swift
//  iosApp
//
//  Created by Debanshu Datta on 19/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct StockExploreView: View {
    var viewModel = KoinHelper().getAppViewModel()
    @State var uiState: DataState = DataStateUninitialized()
    @State var indicesList: DataState = DataStateUninitialized()
    var body: some View {
        ScrollView(.vertical) {
            LazyVStack{
                Spacer().frame(height: 20)
                GridIndexView(indicesList: $indicesList)
                    .collect(flow: viewModel.indicesList, into: $indicesList)
                Spacer().frame(height: 40)
                TodayStockView(universeList: $uiState) { type in
                    viewModel.setType(type: type)
                }.collect(flow: viewModel.universeDataList, into: $uiState)
            }
        }
    }
}
