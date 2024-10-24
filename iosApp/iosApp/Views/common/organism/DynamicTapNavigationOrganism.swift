//
//  DynamicTapNavigationOrganism.swift
//  iosApp
//
//  Created by Debanshu Datta on 29/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct DynamicTapNavigationOrganism: View {
    @Binding var activeTab: Int
    @Environment(\.colorScheme) private var scheme
    var onTapChangeCallback : (Int) -> ()
    var tabs: [String]
    var tabIcons: [String]?
    var animation: Namespace.ID
    
    var body: some View {
        ScrollView(.horizontal) {
            HStack(spacing: 12) {
                ForEach(tabs.indices, id: \.self) { index in
                    TabButton(
                        tab: tabs[index],
                        tabIcon: tabIcons?[index],
                        isActive: activeTab == index,
                        scheme: scheme,
                        animation: animation,
                        action: {
                            self.onTapChangeCallback(index)
                            withAnimation(.easeInOut) {
                                activeTab = index
                            }
                        }
                    )
                }
            }
        }
        .scrollIndicators(.hidden)
        .frame(height: 50)
    }
}

struct TabButton: View {
    var tab: String
    var tabIcon: String?
    var isActive: Bool
    var scheme: ColorScheme
    var animation: Namespace.ID
    var action: () -> Void
    
    var body: some View {
        Button(action: action) {
            HStack{
                if(tabIcon != nil){
                    SVGImageView(url: URL(string: tabIcon!))
                        .frame(width: 15, height: 15)
                }
                Text(tab)
                    .font(.callout)
            }
        }
        .buttonStyle(.plain)
        .foregroundStyle(isActive ? (scheme == .dark ? .black : .white) : Color.primary)
        .padding(.vertical, 5)
        .padding(.horizontal, 10)
        .background {
            if isActive {
                Capsule()
                    .fill(Color.primary)
                    .matchedGeometryEffect(id: "ACTIVETAB", in: animation)
            } else {
                Capsule()
                    .fill(Color(UIColor.systemBackground))
            }
        }
    }
}
