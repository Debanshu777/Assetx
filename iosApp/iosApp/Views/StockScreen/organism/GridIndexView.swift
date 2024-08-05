//
//  GridIndexView.swift
//  iosApp
//
//  Created by Debanshu Datta on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//
import SwiftUI
import shared

struct GridIndexView: View{
    @Binding var indicesList: DataState
    
    var body: some View {
        VStack {
            Text("Market and sectors")
                .font(.system(size: 25, weight: .semibold))
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.horizontal, 10)
            Spacer().frame(height: 15)
            ScrollView(.horizontal){
                LazyHGrid(
                    rows: [
                        GridItem(.fixed(100)),
                        GridItem(.fixed(100))
                    ]
                ){
                    switch indicesList {
                    case is DataStateError:
                        Text("Error")
                    case is DataStateLoading:
                        Text("Error")
                    case let successState as DataStateSuccess<Indices>:
                        @State var indices = successState.stocks.compactMap { $0 as? Indices }
                        ForEach(0..<indices.count, id: \.self) { index in
                            IndicesListItem(indices: indices[index])
                                .frame(width: 350, height: 100)
                                .padding([.leading, .trailing], 5)
                        }
                    case is DataStateUninitialized:
                        Text("Error")
                    default:
                        Text("error")
                    }
                }
            }.scrollIndicators(.hidden)
        }
    }
}
