//
//  StockScreen.swift
//  iosApp
//
//  Created by Debanshu Datta on 19/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct StockScreen: View {
    @State private var activeTab: Int = 0
    @State private var tabViewHeight: CGFloat = 0
    @Environment(\.colorScheme) private var scheme
    let tabs: [(String,AnyView)] = [
        ("Explore",AnyView(StockExploreView())),
        ("Holdings",AnyView(StockHoldingView())),
    ]
    @Namespace private var animation
    
    var body: some View {
        VStack(spacing: 0) {
            DynamicTapNavigationOrganism(
                activeTab: $activeTab,
                onTapChangeCallback: { index in print(index) },
                tabs: tabs.map { $0.0 },
                animation: animation
            ).padding(.horizontal,5)
            
            TabView(selection: $activeTab) {
                ForEach(tabs.indices, id: \.self) { index in
                    tabs[index].1
                        .tag(index)
                }
            }
            .tabViewStyle(.page(indexDisplayMode: .never))
            .frame(maxHeight: .infinity)
        }
        .edgesIgnoringSafeArea(.top)
    }
}
