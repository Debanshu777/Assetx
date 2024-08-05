//
//  SVGImage.swift
//  iosApp
//
//  Created by Debanshu Datta on 31/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import SVGKit

struct SVGImageView: View {
    let url: URL?
    @State private var svgImage: SVGKImage? = nil
    @State private var isLoading: Bool = true
    
    var body: some View {
        if url == nil {
            EmptyView()
        }else{
            VStack {
                if let svgImage = svgImage {
                    SVGView(svgImage: svgImage)
                } else if isLoading {
                    Text("Loading...")
                } else {
                    Text("Failed to load SVG")
                }
            }
            .onAppear {
                loadSVG(url:url!)
            }
        }
    }
    
    private func loadSVG(url: URL) {
        isLoading = true
        DispatchQueue.global().async {
            if let data = try? Data(contentsOf: url),
               let svgImage = SVGKImage(data: data) {
                DispatchQueue.main.async {
                    self.svgImage = svgImage
                    self.isLoading = false
                }
            } else {
                DispatchQueue.main.async {
                    self.svgImage = nil
                    self.isLoading = false
                }
            }
        }
    }
}

struct SVGView: UIViewRepresentable {
    let svgImage: SVGKImage
    
    func makeUIView(context: Context) -> SVGKLayeredImageView {
        return SVGKLayeredImageView(svgkImage: svgImage)
    }
    
    func updateUIView(_ uiView: SVGKLayeredImageView, context: Context) {
        uiView.image = svgImage
    }
}
