//
//  TodayStockView.swift
//  iosApp
//
//  Created by Debanshu Datta on 06/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//
import SwiftUI
import shared

struct TodayStockView: View {
    @Binding var universeList: DataState
    @State private var activeTab: Int = 0
    @Environment(\.colorScheme) private var scheme
    @Namespace private var animation
    var setStockType: (Type_) -> ()
    
    let tabsList: [(ChipItem, Type_)] = [
        (ChipItem(name: "Gainers", iconUrl: "https://assets.tickertape.in/images/landing-page/gainers.svg"), .gainers),
        (ChipItem(name: "Losers", iconUrl: "https://assets.tickertape.in/images/landing-page/losers.svg"), .losers),
        (ChipItem(name: "Most Active", iconUrl: "https://assets.tickertape.in/images/landing-page/most_active.svg"), .active),
        (ChipItem(name: "52W High", iconUrl: "https://assets.tickertape.in/images/landing-page/52w_high.svg"), .approachingHigh),
        (ChipItem(name: "52W Low", iconUrl: "https://assets.tickertape.in/images/landing-page/52w_low.svg"), .approachingLow)
    ]
    
    var body: some View {
        VStack {
            Text("Today's stocks")
                .font(.system(size: 25, weight: .semibold))
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.horizontal, 10)
            
            Spacer().frame(height: 15)
            DynamicTapNavigationOrganism(
                activeTab: $activeTab,
                onTapChangeCallback: { index in
                    setStockType(tabsList[index].1)
                },
                tabs: tabsList.map{ $0.0.name },
                tabIcons: tabsList.map{ $0.0.iconUrl },
                animation: animation
            )
            
            switch universeList {
            case is DataStateError:
                Text("Error")
            case is DataStateLoading:
                ScrollView {
                    VStack(spacing: 0) {
                        ForEach(0..<6, id: \.self){ _ in
                            StockListItem(stock: nil, isLoading: true)
                        }
                    }
                }
            case let successState as DataStateSuccess<Stock>:
                let stocks = successState.stocks.compactMap { $0 as? Stock }
                ScrollView {
                    VStack(spacing: 0) {
                        ForEach(Array(stocks.enumerated()), id: \.element) { index, element in
                            StockListItem(stock: element, isLoading: false)
                            if (index < stocks.count - 1) {
                                Divider()
                                    .padding(.horizontal, 15)
                            }
                        }
                    }
                    .frame(height: 410)
                    .padding(.horizontal, 10)
                }
            case is DataStateUninitialized:
                ScrollView {
                    VStack(spacing: 0) {
                        ForEach(0..<6, id: \.self){ _ in
                            StockListItem(stock: nil, isLoading: true)
                        }
                    }
                }
            default:
                Text("error")
            }
        }.frame(alignment: .leading)
    }
}

