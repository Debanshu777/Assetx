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
    var body: some View {
        ScrollView(.vertical) {
            LazyVStack{
                switch(uiState){
                case is DataStateLoading:
                    ProgressView().padding()
                case let successState as DataStateSuccess:
                    Text(successState.data.description)
                case is DataStateError:
                    Text("error")
                default:
                    Text("error")
                }
            }
            .collect(flow: viewModel.universeDataList, into: $uiState)
        }
    }
}
