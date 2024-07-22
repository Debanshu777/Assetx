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
    @State private var activeTab: Tab = .explore
    @Environment(\.colorScheme) private var scheme
    @Namespace private var animation
    
    var body: some View {
        VStack{
            ScrollView(.horizontal){
                HStack(spacing: 12){
                    ForEach(Tab.allCases,id: \.rawValue){ tab in
                        Button(action:{
                            withAnimation(.easeInOut){
                                activeTab = tab
                            }
                        }){
                            Text(tab.rawValue)
                                .font(.callout)
                                .foregroundStyle(activeTab == tab ? (scheme == .dark ? .black: .white)
                                                 : Color.primary)
                                .padding(.vertical,5)
                                .padding(.horizontal, 10)
                                .background{
                                    if activeTab == tab{
                                        Capsule()
                                            .fill(Color.primary)
                                            .matchedGeometryEffect(id: "ACTIVETAB", in: animation)
                                    }else{
                                        Capsule()
                                            .fill(.background)
                                    }
                                }
                        }
                        .buttonStyle(.plain)
                    }
                }
            }.frame(height: 50)
            
            
            TabView(selection: $activeTab){
                StockExploreView().tabItem{
                    Text("Explore")
                }.tag(Tab.explore)
                
                StockHoldingView().tabItem{
                    Text("Holdings")
                }.tag(Tab.holdings)
                
            }.tabViewStyle(.page(indexDisplayMode: .never))
        }
    }
}
