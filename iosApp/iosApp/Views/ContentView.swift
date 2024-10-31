import SwiftUI
import shared

struct ContentView: View {
    @State private var searchText: String = ""
    @State private var activeScreen: Int = 0
    @State private var isToolbarVisible = true
    let screens: [Screen] = [
        Screen(name: "Stock", iconSystemName: "chart.bar",screen: AnyView(StockScreen())),
        Screen(name: "Home", iconSystemName: "house",screen: AnyView(HomeView()))
    ]
    
    var body: some View {
        NavigationStack{
            TabView(selection: $activeScreen) {
                ForEach(screens.indices, id: \.self) { index in
                    ScrollView{
                        VStack(spacing: 0){
                            ExpandableNavigationBar(screens[activeScreen].name)
                            screens[index].screen
                        }
                        
                    }
                    .tabItem{
                        Image(systemName:screens[index].iconSystemName)
                        Text(screens[index].name)
                    }
                    .tag(index)
                }
            }
        }
    }
    
    @ViewBuilder
    func ExpandableNavigationBar(_ title:String) -> some View{
        VStack{
            HStack{
                Text(title).font(.largeTitle.bold())
                Spacer()
                Image(systemName: "person.fill").font(.body)
            }
            HStack(spacing: 10){
                Image(systemName: "magnifyingglass").font(.body)
                TextField("Search stock, mutual funds etc", text:$searchText)
            }
            .padding(10)
            .background{
                RoundedRectangle(cornerRadius: 20.0)
                    .fill(.bar)
            }
        }
        .padding(.top,25)
        .safeAreaPadding(.horizontal,10)
        .padding(.bottom,10)
    }
}
